package ru.testWork.aoklyunin.mvc.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.testWork.aoklyunin.mvc.bean.DirAndFile;
import ru.testWork.aoklyunin.mvc.bean.HierarhiFile;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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
        List<DirAndFile> dafList = this.jdbcTemplate.query(QUERY_SQL, (resulSet, rowNum) -> {
            DirAndFile daf = new DirAndFile(resulSet.getString("PATH"));
            daf.setIDLOG(resulSet.getInt("IDDIRANDFILE"));
            daf.setCREATED(resulSet.getDate("CREATED"));
            daf.setDIRCNT(resulSet.getInt("DIRCNT"));
            daf.setFILECNT(resulSet.getInt("FILECNT"));
            daf.setSUMMURYSIZE(resulSet.getString("SUMMURYSIZE"));
            return daf;
        });
        return dafList;
    }

    /**
     * Добавляет директорию в БД по расположению
     */
    boolean addDir(String path) {
        // получаем объект по местоположению
        DirAndFile daf = DirAndFile.getObj(path);
        if (daf == null) return false;
        // добавляем в базу директорию
        jdbcTemplate.update(daf.getPreparedStatementCreator());
        // добавляем в базу вложенные файлы
        daf.insertFilesIntoTable(jdbcTemplate);
        return true;
    }

    /**
     * Возвращает список вложенных файлов из БД по id содержащей их директории
     */
    List<HierarhiFile> getDirsById(int id) {
        final String QUERY_SQL = "SELECT * FROM HIERARHIFILES WHERE OWNERID=" + id;
        List<HierarhiFile> hfList = this.jdbcTemplate.query(QUERY_SQL, (resulSet, rowNum) -> {
            HierarhiFile hf = new HierarhiFile(
                    resulSet.getString("SIZE"),
                    resulSet.getString("NAME"),
                    resulSet.getInt("OWNERID")
            );
            return hf;
        });
        return hfList;
    }

    /**
     * Возвращает адрес директории по id
     */
    String getPathById(int id) {
        final String QUERY_SQL = "SELECT (PATH) FROM  DIRANDFILE WHERE +IDDIRANDFILE=?";
        return jdbcTemplate.query(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
            preparedStatement.setInt(1, id);
            return preparedStatement;
        }, (resulSet, rowNum) -> resulSet.getString("PATH")).get(0);

    }


    @Autowired
    DataSource dataSource; //look to application-context.xml bean id='dataSource' definition

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
