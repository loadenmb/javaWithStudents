import java.awt.*;
import java.awt.event.*;

import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;

import java.io.PrintWriter;

import java.net.URI;
 
public class carXMLclient extends Frame implements ActionListener {
   
   // user values
   private TextField fieldId_user;
   private Choice fieldSort_what;
   private Choice fieldSort_order;   
   private TextField fieldcss_fontHeader;
   private TextField fieldcss_fontSizeHeader;
   private TextField fieldcss_fontContent;
   private TextField fieldcss_fontSizeContent;
   private TextField fieldcss_backgroundColor1;
   private TextField fieldcss_backgroundColor2;
   
   // car values
   private TextField fieldId_car;
   private TextField fieldName;
   private TextField fieldPs;
   private TextField fieldMileage;
   private TextField fieldAdmission;
   private TextField fieldLocation;
   private TextField fieldRental_price;
   private TextArea fieldFixture;
   private TextArea fieldText;
   
   // buttons
   private Button btnSend;
   private Button btnAdd;
	private Button btnOpen; 
	
	// last user id of xml after xml send
	private Integer lastAddedId = 0;  
   
   private carXML carxml = new carXML();
     
   public static void main(String[] args) {
      carXMLclient app = new carXMLclient();
   }
 
   // formular
   public carXMLclient () {

		// make X button on top right work
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      }); 
		this.setLayout(new GridBagLayout());
  		GridBagConstraints gbc = new GridBagConstraints(); 				
 		gbc.fill = GridBagConstraints.BOTH;
 		
 		Panel userDataLyout = new Panel(new FlowLayout(FlowLayout.LEFT));
 		gbc.gridx = 0;
     	gbc.gridy = 0; 
      this.add(userDataLyout, gbc);   	
      Panel userDataLyout2 = new Panel(new FlowLayout(FlowLayout.LEFT));
 		gbc.gridx = 0;
     	gbc.gridy = 1; 
      this.add(userDataLyout2, gbc);  	
      Panel userDataLyout3 = new Panel(new FlowLayout(FlowLayout.LEFT));
 		gbc.gridx = 0;
     	gbc.gridy = 2; 
      this.add(userDataLyout3, gbc); 
 		
      Panel carDataLyout = new Panel(new FlowLayout(FlowLayout.LEFT));      
     	gbc.gridx = 0;
     	gbc.gridy = 3;  
      this.add(carDataLyout, gbc);      
      Panel carDataLyout2 = new Panel(new FlowLayout(FlowLayout.LEFT));      
     	gbc.gridx = 0;
     	gbc.gridy = 4;  
      this.add(carDataLyout2, gbc);
      Panel carDataLyout3 = new Panel(new FlowLayout(FlowLayout.LEFT));      
     	gbc.gridx = 0;
     	gbc.gridy = 5;  
      this.add(carDataLyout3, gbc);
 
 		// maybe validate fields, but it's not required if program dead is documented	
 		userDataLyout.add(new Label("Benutzer ID:")); 
      this.fieldId_user = new TextField("0", 3);
      userDataLyout.add(this.fieldId_user);
  
      userDataLyout.add(new Label("Sortieren nach:")); 
		this.fieldSort_what = new Choice();
      this.fieldSort_what.add("ID");
      this.fieldSort_what.add("Mietpreis"); 
      this.fieldSort_what.add("PS"); 
      this.fieldSort_what.add("Zulassung");    
      this.fieldSort_what.add("Kilometerstand"); 
      userDataLyout.add(this.fieldSort_what);  
      
      userDataLyout.add(new Label("Sortier Reihenfolge:"));
		this.fieldSort_order = new Choice();
      this.fieldSort_order.add("ASC");
      this.fieldSort_order.add("DESC");     
      userDataLyout.add(this.fieldSort_order);      
      	
      userDataLyout2.add(new Label("Hintergrund Farbe 1:")); 
		this.fieldcss_backgroundColor1= new TextField("#ccc", 7);
      userDataLyout2.add(this.fieldcss_backgroundColor1); 
      
      userDataLyout2.add(new Label("Größe Überschrift:")); 
		this.fieldcss_fontSizeHeader= new TextField("20px", 4);
      userDataLyout2.add(this.fieldcss_fontSizeHeader);
            	
      userDataLyout2.add(new Label("Schrift Überschrift:")); 
		this.fieldcss_fontHeader= new TextField("Arial", 20);
      userDataLyout2.add(this.fieldcss_fontHeader);       
           
      userDataLyout3.add(new Label("Hintergrund Farbe 2:")); 
		this.fieldcss_backgroundColor2 = new TextField("#fff", 7);
      userDataLyout3.add(this.fieldcss_backgroundColor2);        
      
      userDataLyout3.add(new Label("Größe Inhalt:")); 
		this.fieldcss_fontSizeContent= new TextField("14px", 4);
      userDataLyout3.add(this.fieldcss_fontSizeContent); 
      
      userDataLyout3.add(new Label("Schrift Inhalt:")); 
		this.fieldcss_fontContent= new TextField("sans-serif", 20);
      userDataLyout3.add(this.fieldcss_fontContent); 

      this.btnSend = new Button("XML senden");
      gbc.gridx = 1;
     	gbc.gridy = 0;
      this.add(this.btnSend, gbc);                 
      this.btnSend.addActionListener(this);      
      
 		carDataLyout.add(new Label("Auto ID:")); 
      this.fieldId_car = new TextField("0", 3);
      carDataLyout.add(this.fieldId_car);
      
      carDataLyout.add(new Label("Name:")); 
      this.fieldName = new TextField("", 20);
      carDataLyout.add(this.fieldName);      
      
      carDataLyout.add(new Label("PS:")); 
      this.fieldPs = new TextField("0", 4);
      carDataLyout.add(this.fieldPs);      
      
      carDataLyout.add(new Label("Kilometerstand:")); 
      this.fieldMileage = new TextField("0", 8);
      carDataLyout.add(this.fieldMileage);

 		carDataLyout2.add(new Label("Zulassung:")); 
      this.fieldAdmission = new TextField("0", 4);
      carDataLyout2.add(this.fieldAdmission); 
      
      carDataLyout2.add(new Label("Mietpreis:")); 
      this.fieldRental_price = new TextField("0", 4);
      carDataLyout2.add(this.fieldRental_price); 
      
      carDataLyout2.add(new Label("Standort:")); 
      this.fieldLocation = new TextField("", 16);
      carDataLyout2.add(this.fieldLocation);    
     
      carDataLyout3.add(new Label("Austattung:")); 
		this.fieldFixture = new TextArea("", 5, 30);
      carDataLyout3.add(this.fieldFixture);
      
      carDataLyout3.add(new Label("Text:")); 
		this.fieldText = new TextArea("", 5, 30);
      carDataLyout3.add(this.fieldText);      
      
      this.btnAdd = new Button("Auto hizufügen");
      gbc.gridx = 1;
     	gbc.gridy = 3;
      this.add(this.btnAdd, gbc);                 
      this.btnAdd.addActionListener(this);   

      this.btnOpen = new Button("Webseite öffnen");
      gbc.gridx = 1;
     	gbc.gridy = 6;
      this.add(this.btnOpen, gbc);                 
      this.btnOpen.addActionListener(this);      
      
      this.setTitle("carXMLclient"); 
      this.setSize(1024, 400);
      this.setVisible(true);
   }
 
   // called on button-click.
   @Override
   public void actionPerformed(ActionEvent evt) {
   	
   	// sort & send xml via socket
   	switch (evt.getActionCommand()) { // not great for multi lang, fnd better way to identify action or use constants for text   	
		  
		   case "XML senden":
		      this.carxml.id = Integer.parseInt(this.fieldId_user.getText());
				this.carxml.sort_order = this.fieldSort_order.getItem(this.fieldSort_order.getSelectedIndex());	
				this.carxml.css_fontheader = this.fieldcss_fontHeader.getText();
	   		this.carxml.css_fontsizeheader = this.fieldcss_fontSizeHeader.getText();
	  			this.carxml.css_fontcontent = this.fieldcss_fontContent.getText();
	   		this.carxml.css_fontsizecontent = this.fieldcss_fontSizeContent.getText();
	   		this.carxml.css_backgroundcolor1 = this.fieldcss_backgroundColor1.getText();
	   		this.carxml.css_backgroundcolor2 = this.fieldcss_backgroundColor2.getText();
				switch(this.fieldSort_what.getItem(this.fieldSort_what.getSelectedIndex())) {				
					case "Zulassung":
						this.carxml.sort_what = "admission";
						break;
					case "PS":
						this.carxml.sort_what = "ps";
						break;						
					case "Kilometerstand":
						this.carxml.sort_what = "mileage";
						break;	
					case "Mietpreis":
						this.carxml.sort_what = "rental_price";
						break;			
					default:
						this.carxml.sort_what = "id";		
				}   	
	   		this.carxml.sort();
	
				String xmlData = this.carxml.object2xml();
			   System.out.println(xmlData); // debug output		
				
			  	Socket socket = null;
			   try {
			       socket = new Socket(InetAddress.getLocalHost(), 1337);
	    			 PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
	    			 socketWriter.println(xmlData);
	    			
	    			// reset form fields	    			 
		    		this.fieldId_user.setText(new Integer(this.carxml.id + 1).toString());
					this.fieldSort_order.select(0);	
					this.fieldSort_order.select(0);
					this.fieldcss_fontHeader.setText("Arial");
		   		this.fieldcss_fontSizeHeader.setText("20px");
		  			this.fieldcss_fontContent.setText("sans-serif");
		   		this.fieldcss_fontSizeContent.setText("14px");
		   		this.fieldcss_backgroundColor1.setText("#ccc");
		   		this.fieldcss_backgroundColor2.setText("#fff");
					this.lastAddedId = this.carxml.id;
					this.fieldId_car.setText("0");
					
					// reset carxml
					this.carxml = new carXML();
					
			   } catch (ConnectException e) {
			   	System.err.print(e);			 
			   } catch (IOException e) { 
			       System.err.print(e);
			   } finally {
			   	  try {
	    				socket.close();
					 } catch(IOException e) {
					 	System.err.print(e);
					 }      
			   }
				break;
			
			case "Webseite öffnen":	
				try {
  					Desktop.getDesktop().browse(new URI("http://localhost:7331/?u=" + this.lastAddedId.toString()));
  				} catch(URISyntaxException e) { // never triggered because hard url
  					System.err.print(e);
  				} catch (IOException e) { 
			      System.err.print(e);
			   }
				break;
			
			// add new car     		
   		default:			
				car car = new car(); 
		 	  	car.id = Integer.parseInt(this.fieldId_car.getText());
				car.name = fieldName.getText();
				car.ps = Integer.parseInt(this.fieldPs.getText());
				car.mileage = Integer.parseInt(this.fieldMileage.getText());
				car.admission = Integer.parseInt(this.fieldAdmission.getText());
				car.rental_price = Integer.parseInt(this.fieldRental_price.getText());
				car.location = this.fieldLocation.getText();
				car.fixture = this.fieldFixture.getText();
				car.text = this.fieldText.getText();  
		      this.carxml.add(car);
				
				this.fieldId_car.setText(new Integer(car.id + 1).toString());
				this.fieldName.setText("");
				this.fieldPs.setText("0");
				this.fieldMileage.setText("0");
				this.fieldAdmission.setText("0");
				this.fieldFixture.setText("");
				this.fieldText.setText("");
				this.fieldRental_price.setText("0");
				this.fieldLocation.setText("");
   	}
   }
}