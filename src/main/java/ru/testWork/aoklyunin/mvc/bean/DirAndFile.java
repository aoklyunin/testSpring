package ru.testWork.aoklyunin.mvc.bean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author Клюнин А.О.
 * @version 1.0
 * Класс используется для работы с директориями
 */

public class DirAndFile implements Serializable {

    /**
     * Возвращает объект DirAndFile по адресу в файловой системе
     */
    public static DirAndFile getObj(String path) {
        // создаём объект
        DirAndFile res = new DirAndFile(path);

        Path p = Paths.get(path);
        if (!Files.exists(p)) return null;
        // получаем оставшиеся данные
        BasicFileAttributes attr;
        try {
            attr = Files.readAttributes(p, BasicFileAttributes.class);
            res.CREATED = new Date(attr.creationTime().toMillis());
            res.DIRCNT = (int) Files.list(p).filter(path1 -> path1.toFile().isDirectory()).count();
            res.FILECNT = (int) Files.list(p).filter(path1 -> path1.toFile().isFile()).count();
            long size = Files.list(p).filter(path1 -> path1.toFile().isFile()).
                    map(path1 -> path1.toFile().length()).mapToLong(value -> value.longValue()).sum();
            res.SUMMURYSIZE = getBfFileSize(size);

        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
        return res;
    }

    /**
     * Возвращает "красивую" строку с размером файла, переданным в аргументе
     */
    static String getBfFileSize(final long value) {

        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        if (value < 1)
            return "0 B";
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


    /**
     * Возвращает объект для вставки в таблицу
     */
    public PreparedStatementCreator getPreparedStatementCreator() {
        final String INSERT_SQL = "INSERT INTO DIRANDFILE" +
                " (CREATED,PATH,DIRCNT,FILECNT,SUMMURYSIZE) VALUES (?,?,?,?,?)";
        return connection -> getPS(connection, INSERT_SQL);
    }

    /**
     * Вставляет в таблицу записи, соответствующую директории, адрес которой передан в аргументе
     */
    public void insertFilesIntoTable(JdbcTemplate jdbcTemplate) {

        final String QUERY_SQL = "SELECT (IDDIRANDFILE) FROM  DIRANDFILE WHERE " +
                "CREATED=? AND PATH=? AND DIRCNT=? AND FILECNT=? AND SUMMURYSIZE=?";
        // получаем id
        int id = jdbcTemplate.query(connection -> getPS(connection, QUERY_SQL),
                (resulSet, rowNum) -> resulSet.getInt("IDDIRANDFILE")).get(0);

        // получаем список файлов для объекта директории
        List<HierarhiFile> hf = HierarhiFile.getByFile(PATH, id);

        for (HierarhiFile h : hf) {
            jdbcTemplate.update(h.getPreparedStatementCreator());
        }
    }

    public DirAndFile(String PATH) {
        this.PATH = PATH;
    }

    public int getIDLOG() {
        return IDDIRANDFILE;
    }
    public void setIDLOG(int iDLOG) {
        IDDIRANDFILE = iDLOG;
    }
    public Date getCREATED() {
        return CREATED;
    }
    public void setCREATED(Date CREATED) {
        this.CREATED = CREATED;
    }
    public String getPATH() {
        return PATH;
    }
    public int getDIRCNT() {
        return DIRCNT;
    }
    public void setDIRCNT(int DIRCNT) {
        this.DIRCNT = DIRCNT;
    }
    public int getFILECNT() {
        return FILECNT;
    }
    public void setFILECNT(int FILECNT) {
        this.FILECNT = FILECNT;
    }
    public String getSUMMURYSIZE() {
        return SUMMURYSIZE;
    }
    public void setSUMMURYSIZE(String SUMMURYSIZE) {
        this.SUMMURYSIZE = SUMMURYSIZE;
    }

    @Override
    public String toString() {
        return "DirAndFile{" +
                "CREATED=" + CREATED +
                ", PATH='" + PATH + '\'' +
                ", DIRCNT=" + DIRCNT +
                ", FILECNT=" + FILECNT +
                ", SUMMURYSIZE='" + SUMMURYSIZE + '\'' +
                '}';
    }

    /**
     * Возвращает объект запроса с подстановкой
     */
    private PreparedStatement getPS(Connection connection, String QUERY_SQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
        preparedStatement.setDate(1, new java.sql.Date(CREATED.getTime()));
        preparedStatement.setString(2, PATH);
        preparedStatement.setInt(3, DIRCNT);
        preparedStatement.setInt(4, FILECNT);
        preparedStatement.setString(5, SUMMURYSIZE);
        return preparedStatement;
    }

    /**
     * Возвращает "красивую" строку с размером файла, делителем и единицей измерения, переданным в аргументе
     */
    private static String format(final long value, final long divider, final String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return String.format("%.1f %s", Double.valueOf(result), unit);
    }

    /**
     * версия ID
     **/
    private static final long serialVersionUID = 1L;
    /**
     * ID
     **/
    private int IDDIRANDFILE;
    /**
     * Дата и время создания
     **/
    private Date CREATED;
    /**
     * Путь
     **/
    private String PATH;
    /**
     * Кол-во директорий
     **/
    private int DIRCNT;
    /**
     * Кол-во фалов
     **/
    private int FILECNT;
    /**
     * Суммарный размер файлов
     **/
    private String SUMMURYSIZE;

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;
}
