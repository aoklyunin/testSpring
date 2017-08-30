package ru.testWork.aoklyunin.mvc.bean;

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
        this.mSIZE = SIZE;
        this.mNAME = NAME;
        this.mOWNERID = OWNERID;
    }


    @Override
    public String toString() {
        return "HierarhiFile{" +
                "SIZE='" + mSIZE + '\'' +
                ", NAME='" + mNAME + '\'' +
                ", OWNERID=" + mOWNERID +
                '}';
    }

    public String getSIZE() {
        return mSIZE;
    }
    public String getNAME() {
        return mNAME;
    }
    public int getOWNERID() {
        return mOWNERID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HierarhiFile that = (HierarhiFile) o;

        if (mOWNERID != that.mOWNERID) return false;
        if (mSIZE != null ? !mSIZE.equals(that.mSIZE) : that.mSIZE != null) return false;
        return mNAME != null ? mNAME.equals(that.mNAME) : that.mNAME == null;
    }

    @Override
    public int hashCode() {
        int result = mSIZE != null ? mSIZE.hashCode() : 0;
        result = 31 * result + (mNAME != null ? mNAME.hashCode() : 0);
        result = 31 * result + mOWNERID;
        return result;
    }
    private String mSIZE;
    private String mNAME;
    private int mOWNERID;
}
