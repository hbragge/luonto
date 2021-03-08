/*
 * Opettaja.java
 *
 * Created on 9. helmikuuta 2005, 18:07
 */

package fi.tty.pori.vo;

/**
 *
 * @author pertti
 */
public class Opettaja {
    /** */
    protected int Openro;
    /** */
    protected String Sukunimi;
    /** */
    protected String Etunimi;
    /** */
    protected String Email;
    /** */
    protected String Huone;

    /**
     * Constructor 1
     *
     */
    public Opettaja() {

    }

    /**
     * Constructor 2
     *
     * @param Openro_    
     * @param Sukunimi_    
     * @param Etunimi_    
     * @param Email_    
     * @param Huone_    
     */
    public Opettaja(int Openro_, String Sukunimi_ , String Etunimi_ , String Email_ , String Huone_ ) {

        Openro = Openro_;
        Sukunimi = Sukunimi_;
        Etunimi = Etunimi_;
        Email = Email_;
        Huone = Huone_;
    }

    /**
     * This is setter for Openro
     *
     * @param Openro_    
     */
    public void setOpenro(int Openro_) {

        Openro = Openro_;
    }

    /**
     * This is getter for Openro
     *
     * @return int
     */
    public int getOpenro() {

        return Openro;
    }

    /**
     * This is setter for Sukunimi
     *
     * @param Sukunimi_    
     */
    public void setSukunimi(String Sukunimi_) {

        Sukunimi = Sukunimi_;
    }

    /**
     * This is getter for Sukunimi
     *
     * @return String
     */
    public String getSukunimi() {

        return Sukunimi;
    }

    /**
     * This is setter for Etunimi
     *
     * @param Etunimi_    
     */
    public void setEtunimi(String Etunimi_) {

        Etunimi = Etunimi_;
    }

    /**
     * This is getter for Etunimi
     *
     * @return String
     */
    public String getEtunimi() {

        return Etunimi;
    }

    /**
     * This is setter for Email
     *
     * @param Email_    
     */
    public void setEmail(String Email_) {

        Email = Email_;
    }

    /**
     * This is getter for Email
     *
     * @return String
     */
    public String getEmail() {

        return Email;
    }

    /**
     * This is setter for Huone
     *
     * @param Huone_    
     */
    public void setHuone(String Huone_) {

        Huone = Huone_;
    }

    /**
     * This is getter for Huone
     *
     * @return String
     */
    public String getHuone() {

        return Huone;
    }

    
}
