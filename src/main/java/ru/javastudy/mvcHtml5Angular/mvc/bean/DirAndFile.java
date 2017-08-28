package ru.javastudy.mvcHtml5Angular.mvc.bean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.method.P;

import javax.xml.bind.annotation.XmlElement;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

/**
 * Created for JavaStudy.ru on 26.02.2016.
 */
public class DirAndFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private int IDLOG;
    private Date CREATED;
    private String PATH;
    private int DIRCNT;
    private int FILECNT;
    private String SUMMURYSIZE;

    public DirAndFile() {
    }


    public int getIDLOG() {
        return IDLOG;
    }

    @XmlElement
    public void setIDLOG(int iDLOG) {
        IDLOG = iDLOG;
    }

    public DirAndFile(int IDLOG, Date CREATED, String PATH, int DIRCNT, int FILECNT, String SUMMURYSIZE) {
        this.IDLOG = IDLOG;
        this.CREATED = CREATED;
        this.PATH = PATH;
        this.DIRCNT = DIRCNT;
        this.FILECNT = FILECNT;
        this.SUMMURYSIZE = SUMMURYSIZE;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getCREATED() {
        return CREATED;
    }

    @XmlElement
    public void setCREATED(Date CREATED) {
        this.CREATED = CREATED;
    }

    public String getPATH() {
        return PATH;
    }

    @XmlElement
    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public int getDIRCNT() {
        return DIRCNT;
    }

    @XmlElement
    public void setDIRCNT(int DIRCNT) {
        this.DIRCNT = DIRCNT;
    }

    public int getFILECNT() {
        return FILECNT;
    }

    @XmlElement
    public void setFILECNT(int FILECNT) {
        this.FILECNT = FILECNT;
    }

    public String getSUMMURYSIZE() {
        return SUMMURYSIZE;
    }

    @XmlElement
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

    public static DirAndFile getObj(String path) {
        DirAndFile res = new DirAndFile();
        res.PATH = path;
        Path p = Paths.get(path);
        if (!Files.exists(p)) return null;
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
            System.out.println(e);
            return null;
        }
        return res;
    }

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String getBfFileSize(final long value) {

        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        if (value < 1)
            return "0 b";
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

    private static String format(final long value,
                                 final long divider,
                                 final String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return String.format("%.1f %s", Double.valueOf(result), unit);
    }

    public PreparedStatementCreator getPreparedStatementCreator() {
        final String INSERT_SQL = "INSERT INTO DIRANDFILE (CREATED,PATH,DIRCNT,FILECNT,SUMMURYSIZE) VALUES (?,?,?,?,?)";
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return getPS(connection, INSERT_SQL);
            }
        };
    }

    private PreparedStatement getPS(Connection connection, String QUERY_SQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
        preparedStatement.setDate(1, new java.sql.Date(CREATED.getTime()));
        preparedStatement.setString(2, PATH);
        preparedStatement.setInt(3, DIRCNT);
        preparedStatement.setInt(4, FILECNT);
        preparedStatement.setString(5, SUMMURYSIZE);
        return preparedStatement;
    }



    public void getCreationFiles(JdbcTemplate jdbcTemplate, String path) {

        final String QUERY_SQL = "SELECT (IDDIRANDFILE) FROM  DIRANDFILE WHERE " +
                "CREATED=? AND PATH=? AND DIRCNT=? AND FILECNT=? AND SUMMURYSIZE=?";

        int id = jdbcTemplate.query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return getPS(connection, QUERY_SQL);
            }
        }, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet resulSet, int rowNum) throws SQLException {
                return resulSet.getInt("IDDIRANDFILE");
            }
        }).get(0);

        List<HierarhiFile> hf = HierarhiFile.getByFile(path, id);
        System.out.println(hf);
        for (HierarhiFile h : hf) {
            jdbcTemplate.update(h.getPreparedStatementCreator());
        }
    }
}
