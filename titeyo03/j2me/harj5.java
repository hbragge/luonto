import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.io.*;
import java.util.*;
import java.io.*;



public class harj5 extends MIDlet implements CommandListener{
	private Command backCommand, saveCommand, poistaCommand;
	private List menulist, list;
	private Form form2, form3, form4;
	private String[] items;
	private Alert alert;
	private Vector viestit = new Vector();
	private TextField otsikko;
	private TextField teksti;
	private RecordStore rs = null;
	private RecordEnumeration rEnum = null;
	private Viesti lukuviesti;
	private StringItem lukuotsikko = new StringItem("otsikko",null);
	private StringItem lukuteksti = new StringItem("teksti",null);
	
	public harj5() {
			try {RecordStore.deleteRecordStore("stringit");}
				catch (Exception e){
				}
			otsikko = new TextField("Anna otsikko","", 10, TextField.ANY);
			teksti = new TextField("Anna teksti","", 20, TextField.ANY);
			
			backCommand = new Command("Takaisin", Command.BACK, 1);
			saveCommand = new Command("Tallenna", Command.OK, 1);
			poistaCommand = new Command("Kyllä", Command.OK, 1);
			
			
			
				
			list = new List("Lista",List.IMPLICIT);
			try { paivitaLista();}
				catch(Exception e){
					e.printStackTrace();
					System.out.println("Virhe!");
				}
			
			form2 = new Form("Uusi");
			form2.append(otsikko);
			form2.append(teksti);
			form2.addCommand(backCommand);
			form2.addCommand(saveCommand);
			form2.setCommandListener(this);
			
			form3 = new Form("Poista");
			form3.append("Haluatko poistaa viestit?");
			form3.addCommand(backCommand);
			form3.addCommand(poistaCommand);
			form3.setCommandListener(this);
			
			form4 = new Form("Viesti");
			form4.append(lukuotsikko);
			form4.append(lukuteksti);
			form4.addCommand(backCommand);
			form4.setCommandListener(this);
	}
	
	private Display display;
	public void commandAction(Command c, Displayable d){
		
		if(c == backCommand) {
			display.setCurrent(menulist);
		}
		
		if (c == List.SELECT_COMMAND && d == menulist){
			
			int index = menulist.getSelectedIndex();
			switch(index){
				case 0: display.setCurrent(form2);
				break;
				case 1: {
						display.setCurrent(list);
						list.setCommandListener(this);}
				break;
				case 2: display.setCurrent(form3);
				break;
			}
		}
		
		if (c == List.SELECT_COMMAND && d == list){
			
			
			lukuviesti = (Viesti)viestit.elementAt(list.getSelectedIndex());
			lukuotsikko.setText(lukuviesti.getOtsikko());
			lukuteksti.setText(lukuviesti.getTeksti());
			display.setCurrent(form4);
		}
		
		if(c == saveCommand) {
			
			try	{
				
				kirjoitaViesti();
				paivitaLista();
				paivitaMenu();
			}		
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Virhe!");
			}
			finally{
					
					display.setCurrent(menulist);
			}
		
		}
		
		if(c == poistaCommand) {
			
			try	{
				
				poistaViesti();
				paivitaLista();
				paivitaMenu();
			}		
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Virhe!");
			}
			finally{	
					display.setCurrent(menulist);
			}
		
		}
	}
	
		
	protected void startApp() {
		if (display==null) {
			
			
			
			display = Display.getDisplay(this); // jos null, niin käynnistetään 1. kertaa... myös muut ekan-kerran -toiminnot
			String[] itemit = {"Uusi","Lista","Poista"};
			if(items.length==0)
				itemit = new String[]{"Uusi"};
			menulist = new List("Lista",List.IMPLICIT,itemit,null);
			display.setCurrent(menulist);
			menulist.setCommandListener(this);
			try {
				lueViesti();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		}
		
	}
	
	public String[] lueOtsikot() throws Exception {
		
			String[] otsikot = null;
		try {
			viestit = new Vector();
			rs = RecordStore.openRecordStore("stringit",true);
			rEnum = rs.enumerateRecords(null,null,false);
			otsikot = new String[rEnum.numRecords()];
			for (int i=0;rEnum.hasNextElement();i++) {
				int id = rEnum.nextRecordId();
				byte[] data = rs.getRecord(id);
				Viesti v = new Viesti(data, id);
				viestit.addElement(v);
				otsikot[i] = v.getOtsikko();
			}
			} finally {
				rEnum.destroy();
				rs.closeRecordStore();
				return otsikot;
		}
	}
	
	public void lueViesti() throws Exception {;
			
		try {
			viestit = new Vector();
			rs = RecordStore.openRecordStore("stringit",true);
			rEnum = rs.enumerateRecords(null,null,false);
			
			while (rEnum.hasNextElement()) {
				int id = rEnum.nextRecordId();
				byte[] data = rs.getRecord(id);
				Viesti v = new Viesti(data, id);
				viestit.addElement(v);
			}
			} finally {
				rEnum.destroy();
				rs.closeRecordStore();
		}
	}
	
	public void kirjoitaViesti() throws Exception {
		
		try {
			rs = RecordStore.openRecordStore("stringit",true);
			
			
				Viesti v = new Viesti(otsikko.getString(),teksti.getString());
				
				byte[] data = v.byteiksi();
				int id = rs.addRecord(data,0,data.length);
				v.setRmsId(id);
				lueViesti();
			
			} finally {
				rs.closeRecordStore();
		}
	}
	
	public void poistaViesti() throws Exception {
			
		try {
			
			rs = RecordStore.openRecordStore("stringit",true);
			rEnum = rs.enumerateRecords(null,null,false);
			
			while (rEnum.hasNextElement()) {
				int id = rEnum.nextRecordId();
				rs.deleteRecord(id);
			}
			} finally {
				rEnum.destroy();
				rs.closeRecordStore();
		}
	}
	
	public void paivitaLista() throws Exception {
			list.deleteAll();
			items = lueOtsikot();
			for(int i=0;i<items.length;i++){
				list.append(items[i],null);	
				}
	}
	
	public void paivitaMenu() throws Exception {
			menulist.deleteAll();
			menulist.append("Uusi",null);
			if(items.length>0){
				menulist.append("Lista",null);
				menulist.append("Poista",null);
				}
	}
	
	protected void pauseApp() {
		}
		
	protected void destroyApp(boolean uncond) {
		display.setCurrent(menulist);
		}

}


		
		
		
		
		
