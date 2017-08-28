package ru.javastudy.mvcHtml5Angular.mvc.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.javastudy.mvcHtml5Angular.mvc.bean.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created for JavaStudy.ru on 24.02.2016.
 */
@Repository
public class JDBCHelper {

    @Autowired
    DataSource dataSource; //look to application-context.xml bean id='dataSource' definition

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        System.out.println("JDBCExample postConstruct is called. datasource = " + dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DirAndFile> queryAllDirs() {
        System.out.println("JDBCExample: queryAllLogs() is called");
        final String QUERY_SQL = "SELECT * FROM DIRANDFILE ORDER BY IDDIRANDFILE";
        List<DirAndFile> dbLogList = this.jdbcTemplate.query(QUERY_SQL, new RowMapper<DirAndFile>() {
            public DirAndFile mapRow(ResultSet resulSet, int rowNum) throws SQLException {
                System.out.println("Getting log: " + rowNum + " content: " + resulSet.getString("PATH"));
                DirAndFile dbLog = new DirAndFile();
                dbLog.setIDLOG(resulSet.getInt("IDDIRANDFILE"));
                dbLog.setCREATED(resulSet.getDate("CREATED"));
                dbLog.setDIRCNT(resulSet.getInt("DIRCNT"));
                dbLog.setPATH(resulSet.getString("PATH"));
                dbLog.setFILECNT(resulSet.getInt("FILECNT"));
                dbLog.setSUMMURYSIZE(resulSet.getString("SUMMURYSIZE"));
                return dbLog;
            }
        });
        return dbLogList;
    }

    public boolean addDir(String path) {
        System.out.println("addDir called");
        // получаем объект по местоположению
        DirAndFile daf = DirAndFile.getObj(path);
        if (daf==null) return false;
        else System.out.println(daf);
        // добавляем в базу
        jdbcTemplate.update(daf.getPreparedStatementCreator());
        daf.getCreationFiles(jdbcTemplate,path);
        return true;
    }

    public List<HierarhiFile> getDirsById(int id) {

        final String QUERY_SQL = "SELECT * FROM HIERARHIFILES WHERE OWNERID="+id;
        List<HierarhiFile>  dbLogList = this.jdbcTemplate.query(QUERY_SQL, new RowMapper<HierarhiFile>() {
            public HierarhiFile mapRow(ResultSet resulSet, int rowNum) throws SQLException {
                HierarhiFile dbLog = new HierarhiFile(
                        resulSet.getString("SIZE"),
                        resulSet.getString("NAME"),
                        resulSet.getInt("OWNERID")

                );
                return dbLog;
            }
        });
        return dbLogList;
    }

    public String getPathById(int id) {
        final String QUERY_SQL = "SELECT (PATH) FROM  DIRANDFILE WHERE +IDDIRANDFILE=?";

        return jdbcTemplate.query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
                preparedStatement.setInt(1, id);
                return preparedStatement;
            }
        }, new RowMapper<String>() {
            public String mapRow(ResultSet resulSet, int rowNum) throws SQLException {
                return resulSet.getString("PATH");
            }
        }).get(0);

    }
}
