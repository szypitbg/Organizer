package Organizer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;


public class TopMenuButtonsController {

    private static final String LIST_EVENTS_FXML = "/fxml/ListEvents.fxml";
    private static final String ADD_EVENTS_FXML = "/fxml/AddEvent.fxml";
    private static final String ADD_CATEGORY_FXML = "/fxml/AddCategory.fxml";


    private MainController mainController;

    @FXML
    private ToggleGroup toggleButtons;

    @FXML
    public void openListEvents() {
        mainController.setCenter(LIST_EVENTS_FXML);
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void addEvent() {
        resetToggleButtons();
        mainController.setCenter(ADD_EVENTS_FXML);
    }

    @FXML
    public void addCategory() {
        resetToggleButtons();
        mainController.setCenter(ADD_CATEGORY_FXML);
    }


    private void resetToggleButtons() {
        if(toggleButtons.getSelectedToggle()!=null){
            toggleButtons.getSelectedToggle().setSelected(false);
        }
    }


}
