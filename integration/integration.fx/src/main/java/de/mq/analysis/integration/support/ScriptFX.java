package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

@Controller
class ScriptFX implements Initializable{
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Button selectButton;
	
	@FXML
	private TableView<String> scriptTable;
	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		cancelButton.setOnAction(actionEvent -> cancel(actionEvent));
		selectButton.setOnAction(actionEvent -> {
			cancel(actionEvent);
		});
		
		
	
		
		final TableColumn<String, String> col =  new TableColumn<>();
	
		col.setText("f(x)");
		col.setPrefWidth(500);
	
        col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));	
        scriptTable.getColumns().add(col);
	
		scriptTable.setItems(FXCollections.observableArrayList("Kylie", "Christina"));
	}

	protected void cancel(final ActionEvent actionEvent) {
		((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
	}
	
}
