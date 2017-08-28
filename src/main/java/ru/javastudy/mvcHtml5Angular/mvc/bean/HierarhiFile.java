package ru.javastudy.mvcHtml5Angular.mvc.bean;

import org.springframework.jdbc.core.PreparedStatementCreator;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by aokly on 26.08.2017.
 */
public class HierarhiFile {
    private String SIZE;
    private String NAME;
    private int IDLOG;
    private int OWNERID;

    public String getSIZE() {
        return SIZE;
    }

    @XmlElement
    public void setSIZE(String SIZE) {
        this.SIZE = SIZE;
    }

    public String getNAME() {
        return NAME;
    }

    @XmlElement
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getIDLOG() {
        return IDLOG;
    }

    @XmlElement
    public void setIDLOG(int IDLOG) {
        this.IDLOG = IDLOG;
    }

    public int getOWNERID() {
        return OWNERID;
    }

    @XmlElement
    public void setOWNERID(int OWNERID) {
        this.OWNERID = OWNERID;
    }

    public HierarhiFile(String SIZE, String NAME, int IDLOG, int OWNERID) {

        this.SIZE = SIZE;
        this.NAME = NAME;
        this.IDLOG = IDLOG;
        this.OWNERID = OWNERID;
    }

    public HierarhiFile(String SIZE, String NAME, int OWNERID) {
        this.SIZE = SIZE;
        this.NAME = NAME;
        this.OWNERID = OWNERID;
    }

    public static List<HierarhiFile> getByFile(String path,int ownerId) {
        List<HierarhiFile> lst = new ArrayList<>();
        Path p = Paths.get(path);
        if (!Files.exists(p)) return lst;
        try {
            return Files.list(p).map(path1 -> new HierarhiFile(DirAndFile.getBfFileSize(path1.toFile().length()), path1.toFile().getName(), ownerId)).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "HierarhiFile{" +
                "SIZE='" + SIZE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", IDLOG=" + IDLOG +
                ", OWNERID=" + OWNERID +
                '}';
    }

    private PreparedStatement getPS(Connection connection, String QUERY_SQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
        preparedStatement.setString(1, SIZE);
        preparedStatement.setString(2, NAME);
        preparedStatement.setInt(3, OWNERID);
        return preparedStatement;
    }

    public PreparedStatementCreator getPreparedStatementCreator() {
        final String INSERT_SQL = "INSERT INTO HIERARHIFILES (SIZE,NAME,OWNERID) VALUES (?,?,?)";
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return getPS(connection, INSERT_SQL);
            }
        };
    }
}
