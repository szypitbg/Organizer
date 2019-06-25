package Organizer.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Organizer.modelFx.EventFx;
import Organizer.modelFx.CategoryFx;
import Organizer.modelFx.ListEventsModel;
import Organizer.utils.DialogsUtils;
import Organizer.utils.FxmlUtils;
import Organizer.utils.exceptions.ApplicationException;

import java.io.IOException;


public class ListEventsController {

    @FXML
    private ComboBox categoryComboBox;

    @FXML
    private TableView<EventFx> eventsTableView;
    @FXML
    private TableColumn<EventFx, String> titleColumn;
    @FXML
    private TableColumn<EventFx, String> descColumn;
    @FXML
    private TableColumn<EventFx, CategoryFx> categoryColumn;
    @FXML
    private TableColumn<EventFx, EventFx> deleteColumn;
    @FXML
    private TableColumn<EventFx, EventFx> editColumn;

    private ListEventsModel listEventsModel;

    @FXML
    void initialize() {
        this.listEventsModel = new ListEventsModel();
        try {
            this.listEventsModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }

        this.categoryComboBox.setItems(this.listEventsModel.getCategoryFxObservableList());
        this.listEventsModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        this.eventsTableView.setItems(this.listEventsModel.getEventFxObservableList());
        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        this.descColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxProperty());
        this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        this.editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.deleteColumn.setCellFactory(param -> new TableCell<EventFx, EventFx>() {
            Button button = createButton("/icons/delete.png");

            @Override
            protected void updateItem(EventFx item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        try {
                            listEventsModel.deleteEvent(item);
                        } catch (ApplicationException e) {
                            DialogsUtils.errorDialog(e.getMessage());
                        }
                    });
                }
            }
        });

        this.editColumn.setCellFactory(param -> new TableCell<EventFx, EventFx>() {
            Button button = createButton("/icons/edit.png");

            @Override
            protected void updateItem(EventFx item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddEvent.fxml");
                        Scene scene = null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            DialogsUtils.errorDialog(e.getMessage());
                        }
                        EventController controller = loader.getController();
                        controller.getEventModel().setEventFxObjectProperty(item);
                        controller.bindings();
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    });
                }
            }
        });

    }

    private Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }

    public void filterOnActionComboBox() {
        this.listEventsModel.filterEventsList();
    }

    public void clearCategoryComboBox() {
        this.categoryComboBox.getSelectionModel().clearSelection();
    }
}
