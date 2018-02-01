package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import de.mq.analysis.integration.Script;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

@Controller
class ScriptFX implements Initializable {

	@FXML
	private Button cancelButton;

	@FXML
	private Button selectButton;

	@FXML
	private TableView<Script> scriptTable;
	
	private final ScriptController scriptController;
	
	ScriptFX(final ScriptController scriptController){
		this.scriptController=scriptController;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		cancelButton.setOnAction(actionEvent -> cancel(actionEvent));
		selectButton.setOnAction(actionEvent -> {

			
			cancel(actionEvent);
		});

		final TableColumn<Script, Script> col = new TableColumn<>();

		col.setText("f(x)");
		col.setPrefWidth(500);

		col.setCellValueFactory(data -> new ObservableValueBase<Script>() {

			@Override
			public Script getValue() {
				return data.getValue();
			}
		});

		System.out.println();
		col.setCellFactory(column -> {
			return new TableCell<Script, Script>() {

				protected void updateItem(final Script item, final boolean empty) {
					setText(item != null ? item.code() : null);
				}

			};
		});

		scriptTable.getColumns().add(col);
		scriptTable.setItems(FXCollections.observableArrayList(scriptController.scripts()));
		

	}

	protected void cancel(final ActionEvent actionEvent) {
		((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
	}

}
