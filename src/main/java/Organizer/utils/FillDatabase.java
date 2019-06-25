package Organizer.utils;

import Organizer.database.dao.EventDao;
import Organizer.database.dao.CategoryDao;
import Organizer.database.dao.EventDao;
import Organizer.database.dbuitls.DbManager;
import Organizer.database.models.Event;
import Organizer.database.models.Category;
import Organizer.database.models.Event;
import Organizer.utils.exceptions.ApplicationException;


public class FillDatabase {
    public static  void fillDatabase(){
        Category category1 = new Category();
        category1.setName("Lista zakupów");
        Event event1 = new Event();
        event1.setCategory(category1);
        event1.setTitle("Lista 1");
        event1.setDescription("Kupić bułki i masło");


        Category category3 = new Category();
        category3.setName("Lista filmów");
        Event event2 = new Event();
        event2.setCategory(category3);
        event2.setTitle("Szybcy i wściekli");
        event2.setDescription("Zwrócić uwagę na grę aktorską Vin Diesel'a");

        Category category4 = new Category();
        category4.setName("Lista piosenek");
        Event event3 = new Event();
        event3.setCategory(category4);
        event3.setTitle("Happysad");
        event3.setDescription("Zasłyszane na zajęciach");



        EventDao eventDao = new EventDao();
        try {
            eventDao.creatOrUpdate(event1);
            eventDao.creatOrUpdate(event2);
            eventDao.creatOrUpdate(event3);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        DbManager.closeConnectionSource();
    }
}
