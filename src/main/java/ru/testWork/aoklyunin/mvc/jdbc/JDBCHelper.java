package ru.testWork.aoklyunin.mvc.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import ru.testWork.aoklyunin.mvc.bean.DirAndFile;
import ru.testWork.aoklyunin.mvc.bean.HierarhiFile;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Клюнин А.О.
 * @version 1.0
 *          Вспомогательный класс для работы с БД
 */
@Repository
public class JDBCHelper {

    /**
     * Возвращает список директорий из БД
     */
    List<DirAndFile> queryAllDirs() {
        final String QUERY_SQL = "SELECT * FROM DIRANDFILE ORDER BY IDDIRANDFILE";
        List<DirAndFile> dafList = this.jdbcTemplate.query(QUERY_SQL, (resulSet, rowNum) -> new DirAndFile(
                resulSet.getInt("IDDIRANDFILE"),
                resulSet.getTimestamp("CREATED"),
                resulSet.getString("PATH"),
                resulSet.getInt("DIRCNT"),
                resulSet.getInt("FILECNT"),
                resulSet.getString("SUMMURYSIZE")));
        return dafList;
    }

    /**
     * Добавляет директорию в БД по расположению
     */
    boolean addDir(String path) {
        try {
            DirAndFile daf = new DirAndFile(path);
            // добавляем в базу директорию
            jdbcTemplate.update(getPreparedStatementCreatorDAF(daf));
            // добавляем в базу вложенные файлы
            insertFilesIntoTable(daf, jdbcTemplate);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Возвращает список вложенных файлов из БД по id содержащей их директории
     */
    List<HierarhiFile> getDirsById(int id) {
        final String QUERY_SQL = "SELECT * FROM HIERARHIFILES WHERE OWNERID=" + id;
        return this.jdbcTemplate.query(QUERY_SQL, (resulSet, rowNum) ->
                new HierarhiFile(
                        resulSet.getString("SIZE"),
                        resulSet.getString("NAME"),
                        resulSet.getInt("OWNERID")));
    }

    /**
     * Возвращает адрес директории по id
     */
    String getPathById(int id) {
        final String QUERY_SQL = "SELECT PATH,CREATED FROM  DIRANDFILE WHERE +IDDIRANDFILE=?";
        return jdbcTemplate.query(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
            preparedStatement.setInt(1, id);
            return preparedStatement;
        }, (resulSet, rowNum) ->
                resulSet.getString("PATH") + " " +
                        new SimpleDateFormat("dd.MM.yyyy HH:mm")
                                .format(resulSet.getTimestamp("CREATED"))).get(0);
    }

    /**
     * Совмещаем два списка в один по очереди
     */
    private static List<String> combineTwoList(List<String> lst1, List<String> lst2) {
        List<String> res = new ArrayList<>();
        int minLn = Math.min(lst1.size(), lst2.size());
        int maxLn = Math.max(lst1.size(), lst2.size());
        if (lst1.size() < lst2.size()) {
            List<String> tmp = lst1;
            lst1 = lst2;
            lst2 = tmp;
        }
        for (int i = 0; i < minLn; i++) {
            res.add(lst1.get(i));
            res.add(lst2.get(i));
        }
        for (int i = minLn; i < maxLn; i++) {
            res.add(lst1.get(i));
        }
        return res;
    }

    /**
     * убираем лишние нули в начале строки
     */
    private static String getShortNumber(String s) {
        while (s.startsWith("0")) s = s.substring(1);
        return s;
    }

    /**
     * Создаём список для сортировки
     */
    private static List<String> generateList(String s) {
        final String regexStr = "[-]?[0-9]+";

        Pattern pat = Pattern.compile(regexStr);
        List<String> numbers = new ArrayList<>();
        List<String> notNumbers = new ArrayList<>(Arrays.asList(s.split(regexStr)));
        Matcher matcher = pat.matcher(s);
        while (matcher.find()) {
            numbers.add(getShortNumber(matcher.group()));
        }
        if (Character.isDigit(s.charAt(0)))
            return combineTwoList(numbers, notNumbers);
        else
            return combineTwoList(notNumbers, numbers);
    }

    /**
     * Проверка строки, является ли она числом
     */
    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Метод удаляет из строки расширение
     *
     * @param s - исходная строка
     * @return строка без расширения
     */
    private static String deleteExtension(String s) {
        int lastIndexOfDot = s.lastIndexOf('.');
        if (lastIndexOfDot == -1) return s;

        else return s.substring(0, lastIndexOfDot);
    }

    /**
     * Сортировка
     *
     * @param lst - список для сортировки
     */
    public static void sortFolderList(List<HierarhiFile> lst) {
        lst.sort((o1, o2) -> {
            boolean o1d = o1.getSIZE().equals("<DIR>");
            boolean o2d = o2.getSIZE().equals("<DIR>");
            if ((o1d && o2d) || (!o1d && !o2d)) {
                List<String> gen1 = generateList(deleteExtension(o1.getNAME()));
                List<String> gen2 = generateList(deleteExtension(o2.getNAME()));
                for (int i = 0; i < Math.min(gen1.size(), gen2.size()); i++) {
                    String s1 = gen1.get(i);
                    String s2 = gen2.get(i);
                    int c;
                    if (isNumeric(s1)) {
                        c = Double.compare(Double.parseDouble(s1), Double.parseDouble(s2));
                    } else {
                        int minLn = Math.min(s1.length(), s2.length());
                        c = s1.substring(0, minLn).toLowerCase().compareTo(
                                s2.substring(0, minLn).toLowerCase());
                    }
                    if (c != 0) {
                        return c;
                    } else {
                        int diff = s1.length() - s2.length();
                        if (diff != 0) return diff;
                    }
                }
                return (gen1.size() - gen2.size());
            } else if (o1d) return -1;
            else return 1;
        });
    }

    @Autowired
    DataSource dataSource; //look to application-context.xml bean id='dataSource' definition

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    /**
     * Возвращает объект запроса с подстановкой
     */
    private static PreparedStatement getPSDAF(DirAndFile daf, Connection connection, String QUERY_SQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
        preparedStatement.setDate(1, new java.sql.Date(daf.getCREATED().getTime()));
        preparedStatement.setString(2, daf.getPATH());
        preparedStatement.setInt(3, daf.getDIRCNT());
        preparedStatement.setInt(4, daf.getFILECNT());
        preparedStatement.setString(5, daf.getSUMMURYSIZE());
        return preparedStatement;
    }

    /**
     * Получить список файлов по директории по её id и пути к директории
     */
    private static List<HierarhiFile> getByFileHF(String path, int ownerId) {
        Path p = Paths.get(path);
        if (!Files.exists(p)) return new ArrayList<>();
        try {
            return Files.list(p).map(path1 -> {
                File file = path1.toFile();
                String size;
                if (file.isDirectory()) size = "<DIR>";
                else size = getBfFileSize(file.length());
                return new HierarhiFile(size, file.getName(), ownerId);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new ArrayList<>();
    }


    /**
     * Возвращает "красивую" строку с размером файла, делителем и единицей измерения, переданным в аргументе
     */
    private static String format(long value, long divider, String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return String.format("%.1f%s", Double.valueOf(result), unit);
    }


    /**
     * Вставляет в таблицу записи, соответствующую директории, адрес которой передан в аргументе
     */
    private void insertFilesIntoTable(DirAndFile daf, JdbcTemplate jdbcTemplate) {

        final String QUERY_SQL = "SELECT (IDDIRANDFILE) FROM  DIRANDFILE WHERE " +
                "CREATED=? AND PATH=? AND DIRCNT=? AND FILECNT=? AND SUMMURYSIZE=?";
        // получаем id
        int id = jdbcTemplate.query(connection -> getPSDAF(daf, connection, QUERY_SQL),
                (resulSet, rowNum) -> resulSet.getInt("IDDIRANDFILE")).get(0);

        // получаем список файлов для объекта директории
        List<HierarhiFile> hf = getByFileHF(daf.getPATH(), id);

        for (HierarhiFile h : hf) {
            jdbcTemplate.update(getPreparedStatementCreatorHF(h));
        }
    }

    /**
     * Возвращает объект для вставки в таблицу
     */
    private static PreparedStatementCreator getPreparedStatementCreatorDAF(DirAndFile daf) {
        final String INSERT_SQL = "INSERT INTO DIRANDFILE" +
                " (CREATED,PATH,DIRCNT,FILECNT,SUMMURYSIZE) VALUES (?,?,?,?,?)";
        return connection -> getPSDAF(daf, connection, INSERT_SQL);
    }

    /**
     * Получить объект для запроса к бд для файлов
     */
    private static PreparedStatement getPSHF(HierarhiFile hf, Connection connection, String QUERY_SQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
        preparedStatement.setString(1, hf.getSIZE());
        preparedStatement.setString(2, hf.getNAME());
        preparedStatement.setInt(3, hf.getOWNERID());
        return preparedStatement;
    }

    /**
     * Получить объект для вставки в бд для файлов
     */
    private static PreparedStatementCreator getPreparedStatementCreatorHF(HierarhiFile hf) {
        final String INSERT_SQL = "INSERT INTO HIERARHIFILES (SIZE,NAME,OWNERID) VALUES (?,?,?)";
        return connection -> getPSHF(hf, connection, INSERT_SQL);
    }

    /**
     * Возвращает кол-во папок по адресу
     * @param p
     * @return
     * @throws IOException
     */
    public static int getDirCnt(Path p) throws IOException {
        return (int) Files.list(p).filter(path1 -> path1.toFile().isDirectory()).count();
    }

    /**
     * Возвращает кол-во файлов по адресу
     * @param p
     * @return
     * @throws IOException
     */
    public static int getFileCnt(Path p) throws IOException {
        return (int) Files.list(p).filter(path1 -> path1.toFile().isFile()).count();
    }

    /**
     * Возвращает объект DirAndFile по адресу в файловой системе
     */
    public static long getSumSize(Path p) throws IOException {
        return Files.list(p).filter(path1 -> path1.toFile().isFile()).
                map(path1 -> path1.toFile().length()).mapToLong(value -> value.longValue()).sum();

    }



    /**
     * Возвращает "красивую" строку с размером файла, переданным в аргументе
     */
    public static String getBfFileSize(long value) {
        long[] dividers = new long[]{T, G, M, K, 1};
        String[] units = new String[]{"Tb", "Gb", "Mb", "Kb", "b"};
        if (value < 1)
            return "0b";
        String result = null;
        for (int i = 0; i < dividers.length; i++) {
            final long divider = dividers[i];
            if (value >= divider) {
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;
}
