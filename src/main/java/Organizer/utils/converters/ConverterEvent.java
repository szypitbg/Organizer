package Organizer.utils.converters;

import Organizer.database.models.Event;
import Organizer.modelFx.EventFx;


public class ConverterEvent {

    public static Event convertToEvent(EventFx eventFx){
        Event book = new Event();
        book.setId(eventFx.getId());
        book.setTitle(eventFx.getTitle());
        book.setDescription(eventFx.getDescription());
        return  book;
    }

    public static EventFx convertToEventFx(Event event){
        EventFx bookFx = new EventFx();
        bookFx.setId(event.getId());
        bookFx.setTitle(event.getTitle());
        bookFx.setDescription(event.getDescription());
        bookFx.setCategoryFx(ConverterCategory.convertToCategoryFx(event.getCategory()));
        return bookFx;
    }

}
