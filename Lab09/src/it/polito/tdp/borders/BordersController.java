/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader
	
    @FXML // fx:id="boxCountries"
    private ComboBox<String> boxCountries; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		try {
			int anno = Integer.parseInt(txtAnno.getText());
			
			if(anno < 1816 || anno > 2006)
				throw new IllegalArgumentException();
			
			model.createGraph(anno);
			Map<Country, Integer> countries = model.getCountries();
			
			for(Country c:countries.keySet()) {
				if(countries.get(c)!=0)
					txtResult.appendText("Stato: " +c.getStateName() + ", numero di stati confinanti: " + countries.get(c) + "\n");
			}
			
			int cc = model.getCC();
			
			txtResult.appendText("Il numero di componenti connesse al grafo è: " + cc + "\n");
			
		}catch(NumberFormatException nfe) {
			txtResult.appendText("Inserire un anno valido sotto forma di numero intero\n");
			
		}catch(IllegalArgumentException iae) {
			txtResult.appendText("L'anno deve essere compreso nell'intervallo [1816, 2006]\n");
			
		}
	}
	
    @FXML
    void handleCerca(ActionEvent event) {
    	try {
    		int anno = Integer.parseInt(txtAnno.getText());
			
			if(anno < 1816 || anno > 2006)
				throw new IllegalArgumentException();
			
			model.createGraph(anno);
			
			String country = this.boxCountries.getValue();
			
			if(country == null)
				throw new Exception();
				
			List<Country> vicini = model.cercaVicini(country) ;
			
				txtResult.appendText("Ecco la lista degli stati raggiungibili:\n");
				
				for(Country c:vicini) {
					txtResult.appendText(c.getStateName() + "\n");
				}
				
			
			
			
    	} catch(NumberFormatException nfe) {
			txtResult.appendText("Inserire un anno valido sotto forma di numero intero\n");
			
		}catch(IllegalArgumentException iae) {
			txtResult.appendText("L'anno deve essere compreso nell'intervallo [1816, 2006]\n");
			
		} catch(Exception e) {
			txtResult.appendText("Seleziona uno stato\n");
		}
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert boxCountries != null : "fx:id=\"boxCountries\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model m) {
		// TODO Auto-generated method stub
		this.model = m;
		
		for(Country c:model.getAllCountries()) {
			boxCountries.getItems().add(c.getStateName());
		}
		
	}
}
