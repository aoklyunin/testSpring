package ru.javastudy.mvcHtml5Angular.mvc.bean;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by aokly on 26.08.2017.
 */
public class HierarhiFile {
    private long SIZE;
    private long NAME;
    private int IDLOG;
    private int OWNERID;

    public long getSIZE() {
        return SIZE;
    }
    @XmlElement
    public void setSIZE(long SIZE) {
        this.SIZE = SIZE;
    }

    public long getNAME() {
        return NAME;
    }
    @XmlElement
    public void setNAME(long NAME) {
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

    public HierarhiFile(long SIZE, long NAME, int IDLOG, int OWNERID) {

        this.SIZE = SIZE;
        this.NAME = NAME;
        this.IDLOG = IDLOG;
        this.OWNERID = OWNERID;
    }
}
