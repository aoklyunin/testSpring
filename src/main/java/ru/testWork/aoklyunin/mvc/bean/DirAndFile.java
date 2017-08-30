package ru.testWork.aoklyunin.mvc.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static ru.testWork.aoklyunin.mvc.jdbc.JDBCHelper.*;

/**
 * @author Клюнин А.О.
 * @version 1.0
 *          Класс используется для работы с директориями
 */

public class DirAndFile implements Serializable {

    /**
     * @param IDDIRANDFILE id
     * @param CREATED      дата добавления
     * @param PATH         путь к директории
     * @param DIRCNT       кол-во директорий
     * @param FILECNT      кол-во файлов
     * @param SUMMURYSIZE  суммарный размер файлов
     */
    public DirAndFile(int IDDIRANDFILE, Date CREATED, String PATH, int DIRCNT, int FILECNT, String SUMMURYSIZE) {
        this.IDDIRANDFILE = IDDIRANDFILE;
        this.CREATED = CREATED;
        this.PATH = PATH;
        this.DIRCNT = DIRCNT;
        this.FILECNT = FILECNT;
        this.SUMMURYSIZE = SUMMURYSIZE;
    }


    /**
     * Конструктор от адреса папки
     *
     * @param path
     */
    public DirAndFile(String path) throws IOException {
        Path p = Paths.get(path);
        if (Files.exists(p))
        // получаем оставшиеся данные
        this.CREATED = new Date();
        this.PATH = path;
        this.DIRCNT = getDirCnt(p);
        this.FILECNT = getFileCnt(p);
        this.SUMMURYSIZE = getBfFileSize(getSumSize(p));
    }

    public int getIDLOG() {
        return IDDIRANDFILE;
    }

    public Date getCREATED() {
        return CREATED;
    }

    public String getPATH() {
        return PATH;
    }

    public int getDIRCNT() {
        return DIRCNT;
    }

    public int getFILECNT() {
        return FILECNT;
    }

    public String getSUMMURYSIZE() {
        return SUMMURYSIZE;
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
