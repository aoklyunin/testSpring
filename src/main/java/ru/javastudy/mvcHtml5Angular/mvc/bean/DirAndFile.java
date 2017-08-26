package ru.javastudy.mvcHtml5Angular.mvc.bean;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

}
