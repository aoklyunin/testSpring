package ru.testWork.aoklyunin.mvc.bean;

import org.springframework.jdbc.core.PreparedStatementCreator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  @author Клюнин А.О.
 *  @version 1.0
 *  Класс используется для работы с файлами в директории
 */
public class HierarhiFile {
    /**
     * Конструктор с аргументами: путь к файлу, назваание файла, id директории
     */
    public HierarhiFile(String SIZE, String NAME, int OWNERID) {
        this.SIZE = SIZE;
        this.NAME = NAME;
        this.OWNERID = OWNERID;
    }

    /**
     *  Получить список файлов по директории по её id и пути к директории
     */
    static List<HierarhiFile> getByFile(String path, int ownerId) {
        Path p = Paths.get(path);
        if (!Files.exists(p)) return  new ArrayList<>();
        try {
            return Files.list(p).map(path1 -> {
                File file = path1.toFile();
                String size;
                if (file.isDirectory()) size = "<DIR>";
                else size = DirAndFile.getBfFileSize(file.length());
                return new HierarhiFile(size,file.getName(), ownerId);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new ArrayList<>();
    }

    /**
     *  Получить объект для вставки в бд
     */
    PreparedStatementCreator getPreparedStatementCreator() {
        final String INSERT_SQL = "INSERT INTO HIERARHIFILES (SIZE,NAME,OWNERID) VALUES (?,?,?)";
        return connection -> getPS(connection, INSERT_SQL);
    }

    @Override
    public String toString() {
        return "HierarhiFile{" +
                "SIZE='" + SIZE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", OWNERID=" + OWNERID +
                '}';
    }

    public String getSIZE() {
        return SIZE;
    }
    public String getNAME() {
        return NAME;
    }

    /**
     *  Получить объект запроса к бд по строке
     */
    private PreparedStatement getPS(Connection connection, String QUERY_SQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
        preparedStatement.setString(1, SIZE);
        preparedStatement.setString(2, NAME);
        preparedStatement.setInt(3, OWNERID);
        return preparedStatement;
    }

    private String SIZE;
    private String NAME;
    private int OWNERID;
}
