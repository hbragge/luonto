import java.io.*;

public class Viesti {
  private String otsikko;
  private String teksti;
  private int rmsId;

  public Viesti() { rmsId = -1; }
  
  public Viesti(String otsikko, String teksti) {
    this.otsikko = otsikko;
    this.teksti = teksti;
    rmsId = -1;
  }

  public Viesti(byte[] data) throws IOException {
    this(data,-1);
  }

  public Viesti(byte[] data, int rmsId) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(data);
    DataInputStream dis = new DataInputStream(bais);
    otsikko = dis.readUTF();
    teksti = dis.readUTF();
    this.rmsId = rmsId;
    dis.close();
  }
  
  public String getOtsikko() { return otsikko; }
  public String getTeksti() { return teksti; }
  public int getRmsId() { return rmsId; }
  
  public void setOtsikko(String uusiOtsikko) {otsikko = uusiOtsikko;}
  public void setTeksti(String uusiTeksti) {teksti = uusiTeksti;}
  public void setRmsId(int id) {rmsId = id;}
  
  public byte[] byteiksi() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeUTF(otsikko);
    dos.writeUTF(teksti);
    dos.close();
    byte[] data = baos.toByteArray();
    return data;
  }

}