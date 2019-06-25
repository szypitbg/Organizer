package Organizer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import Organizer.modelFx.EventModel;
import Organizer.modelFx.CategoryFx;
import Organizer.utils.DialogsUtils;
import Organizer.utils.exceptions.ApplicationException;

public class EventController {

    @FXML
    private Button addButton;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private TextArea descTextArea;
    @FXML
    private TextField titleTextField;

    private EventModel eventModel;

    @FXML
    public void initialize() {
        this.eventModel = new EventModel();
        try {
            this.eventModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();

    }


    public void bindings() {
        this.categoryComboBox.setItems(this.eventModel.getCategoryFxObservableList());
        this.categoryComboBox.valueProperty().bindBidirectional(this.eventModel.getEventFxObjectProperty().categoryFxProperty());
        this.titleTextField.textProperty().bindBidirectional(this.eventModel.getEventFxObjectProperty().titleProperty());
        this.descTextArea.textProperty().bindBidirectional(this.eventModel.getEventFxObjectProperty().descriptionProperty());

    }

    public void addEventOnAction() {
        try {
            this.eventModel.saveEventInDataBase();
            clearFields();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private void clearFields() {
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.titleTextField.clear();
        this.descTextArea.clear();

    }

    public EventModel getEventModel() {
        return eventModel;
    }
}
