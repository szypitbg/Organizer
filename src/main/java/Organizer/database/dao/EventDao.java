package Organizer.database.dao;

import Organizer.database.models.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import Organizer.utils.exceptions.ApplicationException;
import java.sql.SQLException;


public class EventDao extends CommonDao {

    public EventDao() {
        super();
    }

    public void deleteByColumnName(String columName, int id) throws ApplicationException, SQLException {
        Dao<Event, Object> dao = getDao(Event.class);
        DeleteBuilder<Event, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columName, id);
        dao.delete(deleteBuilder.prepare());
    }

}
