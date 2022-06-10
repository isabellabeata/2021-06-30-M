/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.model.creaGrafo();
    	
    	this.txtResult.appendText(this.model.nVertici());
    	this.txtResult.appendText(this.model.nArchi());
    	
    	this.txtResult.appendText(this.model.sPesoMin()+"\n");
    	this.txtResult.appendText(this.model.sPesoMax()+"\n");
    	
    	Double soglia=Double.parseDouble(this.txtSoglia.getText());
    	
    	if(soglia> this.model.pesoMax() || soglia<this.model.pesoMin()) {
    		this.txtResult.setText("inserire un valore fra peso minimo e peso massimo!" );
    	}

    		this.txtResult.appendText(this.model.nArchiMinS(soglia)+"\n");
    		this.txtResult.appendText(this.model.nArchiMaggS(soglia));
    	
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Double soglia=Double.parseDouble(this.txtSoglia.getText());
    	
    	if(soglia> this.model.pesoMax() || soglia<this.model.pesoMin()) {
    		this.txtResult.setText("inserire un valore fra peso minimo e peso massimo!" );
    	}
    	
    	
    	this.txtResult.appendText(this.model.getCammino(soglia));
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		
	}
}
