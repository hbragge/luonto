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
public class Viesti {
    /** */
    protected int Viestinro;
    /** */
    protected int Vastaanottaja;
    /** */
    protected String Viesti;
    /** */

    /**
     * Constructor 1
     *
     */
    public Viesti() {

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
    public Viesti(int Viestinro_, int Vastaanottaja_ , String Viesti_ ) {

        Viestinro = Viestinro_;
        Vastaanottaja = Vastaanottaja_;
        Viesti = Viesti_;
    }

    /**
     * This is setter for Openro
     *
     * @param Openro_    
     */
    public void setViestinro(int Viestinro_) {

        Viestinro = Viestinro_;
    }

    /**
     * This is getter for Openro
     *
     * @return int
     */
    public int getViestinro() {

        return Viestinro;
    }

    /**
     * This is setter for Sukunimi
     *
     * @param Sukunimi_    
     */
    public void setVastaanottaja(int Vastaanottaja_) {

        Vastaanottaja = Vastaanottaja_;
    }

    /**
     * This is getter for Sukunimi
     *
     * @return String
     */
    public int getVastaanottaja() {

        return Vastaanottaja;
    }

    /**
     * This is setter for Etunimi
     *
     * @param Etunimi_    
     */
    public void setViesti(String Viesti_) {

        Viesti = Viesti_;
    }

    /**
     * This is getter for Etunimi
     *
     * @return String
     */
    public String getViesti() {

        return Viesti;
    }

 

    
}
