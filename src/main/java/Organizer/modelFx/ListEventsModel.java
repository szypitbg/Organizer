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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ListEventsModel {

    private ObservableList<EventFx> eventFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();
    private List<EventFx> eventFxList = new ArrayList<>();

    public void init() throws ApplicationException {
        EventDao eventDao = new EventDao();
        List<Event> event = eventDao.queryForAll(Event.class);
        eventFxList.clear();
        event.forEach(events -> {
            this.eventFxList.add(ConverterEvent.convertToEventFx(events));
        });
        this.eventFxObservableList.setAll(eventFxList);


        initCategory();
    }

    public void filterEventsList() {
        if (getCategoryFxObjectProperty() != null) {
            filterPredicate(predicateCatgory());
        } else {
            this.eventFxObservableList.setAll(this.eventFxList);
        }
    }

    public void deleteEvent(EventFx eventFx) throws ApplicationException {
        EventDao eventDao = new EventDao();
        eventDao.deleteById(Event.class, eventFx.getId());
        init();
    }

    private void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.queryForAll(Category.class);
        this.categoryFxObservableList.clear();
        categories.forEach(c -> {
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            this.categoryFxObservableList.add(categoryFx);
        });
    }

    private Predicate<EventFx> predicateCatgory() {
        return eventFx -> eventFx.getCategoryFx().getId() == getCategoryFxObjectProperty().getId();
    }


    private void filterPredicate(Predicate<EventFx> predicate) {
        List<EventFx> newList = eventFxList.stream().filter(predicate).collect(Collectors.toList());
        this.eventFxObservableList.setAll(newList);
    }

    public ObservableList<EventFx> getEventFxObservableList() {
        return eventFxObservableList;
    }

    public void setEventFxObservableList(ObservableList<EventFx> eventFxObservableList) {
        this.eventFxObservableList = eventFxObservableList;
    }


    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }


    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }
}
