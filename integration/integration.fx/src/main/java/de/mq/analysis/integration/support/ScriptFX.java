package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import de.mq.analysis.integration.Script;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

@Controller
class ScriptFX implements Initializable, Observer {

	@FXML
	private Button cancelButton;

	@FXML
	private Button selectButton;

	@FXML
	private TableView<Script> scriptTable;

	@FXML
	private TextArea script;

	@FXML
	private Button deleteScript;

	@FXML
	private Button addScript;

	@FXML
	private Button saveScript;

	private final ScriptController scriptController;

	private final ScriptAO scriptAO = new ScriptAO();

	ScriptFX(final ScriptController scriptController, final DefiniteIntegralFX definiteIntegralFX) {
		scriptAO.addObserver(definiteIntegralFX.getDefiniteIntegralAO());

		this.scriptController = scriptController;
		scriptAO.addObserver(this);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		cancelButton.setOnAction(actionEvent -> cancel(actionEvent));
		selectButton.setOnAction(actionEvent -> {
			final Script script = scriptTable.getSelectionModel().getSelectedItem();
			scriptAO.setSelectedScript(script);
			cancel(actionEvent);
		});

		addScript.setOnAction(actionEvent -> {
			scriptTable.getSelectionModel().clearSelection();
			scriptAO.setCurrentScript(new ScriptImpl(null));

		});

		script.textProperty().addListener((observable, oldValue, newValue) -> scriptAO.setCurrentScript(newValue));

		final TableColumn<Script, Script> col = new TableColumn<>();

		col.setText("f(x)");
		col.setPrefWidth(500);

		col.setCellValueFactory(data -> new ObservableValueBase<Script>() {

			@Override
			public Script getValue() {

				return data.getValue();
			}
		});

		col.setCellFactory(column -> {
			return new TableCell<Script, Script>() {

				@Override
				protected void updateItem(final Script item, final boolean empty) {
					setText(item != null ? item.code() : null);
				}

			};
		});

		scriptTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
			selectButton.setDisable(newSelection == null);
			scriptAO.setCurrentScript(newSelection);

		});

		saveScript.setOnAction(actionEvent -> {
			final Script changedScript = scriptController.save(scriptAO);
			setScripts();
			scriptTable.getSelectionModel().select(changedScript);

		});

		deleteScript.setOnAction(actionEvent -> {
			scriptController.delete(scriptAO);
			scriptTable.setEditable(true);
			setScripts();
		});

		selectButton.setDisable(true);
		scriptTable.getColumns().add(col);

		setScripts();

		scriptTable.setPlaceholder(new Label());

		scriptTable.getSelectionModel().select(scriptAO.getSelectedScript());

	}

	protected void setScripts() {
		scriptTable.getItems().clear();
		scriptTable.getItems().addAll(scriptController.scripts());
	}

	private void cancel(final ActionEvent actionEvent) {
		((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
	}

	@Override
	public void update(final Observable o, final Object arg) {

		script.setText(scriptAO.getCurrentScript() != null ? scriptAO.getCurrentScript().code() : null);
		deleteScript.setDisable(scriptAO.getCurrentScript() == null || scriptAO.getCurrentScript().id() == null);
		saveScript.setDisable(scriptAO.getCurrentScript() == null || !StringUtils.hasText(scriptAO.getCurrentScript().code()));

	}

}
