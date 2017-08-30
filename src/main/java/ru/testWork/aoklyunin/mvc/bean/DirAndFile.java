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
        this.mIDDIRANDFILE = IDDIRANDFILE;
        this.mCREATED = CREATED;
        this.mPATH = PATH;
        this.mDIRCNT = DIRCNT;
        this.mFILECNT = FILECNT;
        this.mSUMMURYSIZE = SUMMURYSIZE;
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
        this.mCREATED = new Date();
        this.mPATH = path;
        this.mDIRCNT = getDirCnt(p);
        this.mFILECNT = getFileCnt(p);
        this.mSUMMURYSIZE = getBfFileSize(getSumSize(p));
    }

    public int getIDLOG() {
        return mIDDIRANDFILE;
    }

    public Date getCREATED() {
        return mCREATED;
    }

    public String getPATH() {
        return mPATH;
    }

    public int getDIRCNT() {
        return mDIRCNT;
    }

    public int getFILECNT() {
        return mFILECNT;
    }

    public String getSUMMURYSIZE() {
        return mSUMMURYSIZE;
    }

    @Override
    public String toString() {
        return "DirAndFile{" +
                "CREATED=" + mCREATED +
                ", PATH='" + mPATH + '\'' +
                ", DIRCNT=" + mDIRCNT +
                ", FILECNT=" + mFILECNT +
                ", SUMMURYSIZE='" + mSUMMURYSIZE + '\'' +
                '}';
    }


    /**
     * версия ID
     **/
    private static final long serialVersionUID = 1L;
    /**
     * ID
     **/
    private int mIDDIRANDFILE;
    /**
     * Дата и время создания
     **/
    private Date mCREATED;
    /**
     * Путь
     **/
    private String mPATH;

    /**
     * Кол-во директорий
     **/
    private int mDIRCNT;
    /**
     * Кол-во фалов
     **/
    private int mFILECNT;

    /**
     * Суммарный размер файлов
     **/
    private String mSUMMURYSIZE;

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;
}
