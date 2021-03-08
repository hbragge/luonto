// Teht‰v‰ 37 - Henri Bragge

import java.io.*;//Tarvitaan virheenk‰sittely‰ varten(ei ole)
import java.awt.*;
import java.awt.event.*;//Toiminnallisuutta nappuloiden taakse
import javax.swing.*;//Tarvitaan graafisen k‰yttˆliittym‰n tekemiseen
 
 public class tehtava37{
 //Luokan sis‰iset metodit
 private static int luku=0;
 //Tekstikent‰‰ t‰ytyy m‰‰ritell‰ jo t‰‰ll‰ koska useampi metodi k‰ytt‰‰ t‰t‰
 private static JTextField lukukentta= new JTextField(5);
 
 //Main-metodi ainoastaan k‰ynnist‰‰ ikkuna metodin
 public static void main(String[] args) throws IOException{
 //ikkunalle syˆttein‰ otsikko ja korkeus ja leveys 
 ikkuna("Swingi esimerkki", 100,150);
 }
 
 //Ikkuna-metodi piirt‰‰ graafisen k‰yttˆliittym‰n
 public static void ikkuna(String otsikko, int leveys, int korkeus)throws IOException{
 //Luodaan uusi JFrame olio (siis uusi ikkuna)
        JFrame M = new JFrame(otsikko);
        
        //Luodaan uusi JPanel olio(t‰h‰n lis‰t‰‰n myˆhemmin kaikki ikkunaan tuleva tavara)
        JPanel contentPane=new JPanel();
        
        //asetetaan contentPane-s‰iliˆ JFramen sis‰lle
        M.setContentPane(contentPane);
        
        //M‰‰ritell‰‰ layout(vaihtoehtoina BorderLayout, FlowLayout, GridLayout...jne.)
        contentPane.setLayout(new FlowLayout());
        
        //Luodaan nappulat
        JButton lisaaButton=new JButton("Lis‰‰ 1");
        JButton lopetaButton=new JButton("Lopeta");
        JButton vahenButton=new JButton("V‰henn‰ 1");
        
        //Lis‰t‰‰n nappulat ja tekstikent‰t contentPane:n sis‰lle
        contentPane.add(lukukentta);
        contentPane.add(lisaaButton);
        contentPane.add(vahenButton);
        contentPane.add(lopetaButton);
        
       //Lis‰t‰‰n Tapahtuman kuuntelijat nappuloihin
       //Lopetusnappula
        lopetaButton.addActionListener(
        new ActionListener(){
        public void actionPerformed(ActionEvent e){
        lopeta_actionPerformed(e);//kutsutaan lopeta_actionPerformed metodia
        }
        }
        );
        //Valintanappula
        lisaaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lisaaButton_actionPerformed(e);//kutsutaan lisaaButton_actionPerformed metodia
            }
        });
        
        vahenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vahenButton_actionPerformed(e);
            }
        });
        
//sovelluksen lopetus
        M.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ikkunan koonasetus
        M.setSize(leveys, korkeus);
        //ikkunan luonti       
        M.setVisible(true);
    }
    
    
    //Metodi lis‰‰ tekstikent‰n arvoa yhdell‰
    static void lisaaButton_actionPerformed(ActionEvent e) {
    luku++;
lukukentta.setText(String.valueOf(luku));
}

	static void vahenButton_actionPerformed(ActionEvent e) {
    luku--;
lukukentta.setText(String.valueOf(luku));
}
    
    //Ohjelman lopetus metodi. Suoritetaan kun lopeta nappulaa on painettu
    static void lopeta_actionPerformed(ActionEvent e) {
    System.exit(0);
}
    
}