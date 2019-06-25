package Organizer.modelFx;

import Organizer.database.dao.EventDao;
import Organizer.database.dao.CategoryDao;
import Organizer.database.models.Event;
import Organizer.database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Organizer.utils.converters.ConverterEvent;
import Organizer.utils.converters.ConverterCategory;
import Organizer.utils.exceptions.ApplicationException;

import java.util.List;


public class EventModel {


    private ObjectProperty<EventFx> eventFxObjectProperty = new SimpleObjectProperty<>(new EventFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();



    public void init() throws ApplicationException {

        initCategoryList();
    }

    private void initCategoryList() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c->{
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            categoryFxObservableList.add(categoryFx);
        });
    }


    public void saveEventInDataBase() throws ApplicationException {
        Event event = ConverterEvent.convertToEvent(this.getEventFxObjectProperty());

        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class, this.getEventFxObjectProperty().getCategoryFx().getId());

        event.setCategory(category);

        EventDao eventDao = new EventDao();
        eventDao.creatOrUpdate(event);

    }


    public EventFx getEventFxObjectProperty() {
        return eventFxObjectPropertyProperty().get();

    }


    public ObjectProperty<EventFx> eventFxObjectPropertyProperty() {
        return eventFxObjectProperty;

    }

    public void setEventFxObjectProperty(EventFx eventFxObjectProperty) {
        this.eventFxObjectPropertyProperty().set(eventFxObjectProperty);

    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }


    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

}
