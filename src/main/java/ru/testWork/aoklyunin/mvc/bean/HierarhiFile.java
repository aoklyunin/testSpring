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
        this.SIZE = SIZE;
        this.NAME = NAME;
        this.OWNERID = OWNERID;
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
    public int getOWNERID() {
        return OWNERID;
    }

    private String SIZE;
    private String NAME;
    private int OWNERID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HierarhiFile that = (HierarhiFile) o;

        if (OWNERID != that.OWNERID) return false;
        if (SIZE != null ? !SIZE.equals(that.SIZE) : that.SIZE != null) return false;
        return NAME != null ? NAME.equals(that.NAME) : that.NAME == null;
    }

    @Override
    public int hashCode() {
        int result = SIZE != null ? SIZE.hashCode() : 0;
        result = 31 * result + (NAME != null ? NAME.hashCode() : 0);
        result = 31 * result + OWNERID;
        return result;
    }
}
