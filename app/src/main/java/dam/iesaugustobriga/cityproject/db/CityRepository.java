package dam.iesaugustobriga.cityproject.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import dam.iesaugustobriga.cityproject.db.dao.CityDao;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;

public class CityRepository {

    private final CityDao cityDao;
    private final LiveData<List<CityEntity>> allCities;

    public CityRepository(Application application) {
        CityRoomDatabase db = CityRoomDatabase.getDatabase(application);
        cityDao = db.cityDao();
        allCities = cityDao.getAll();
    }

    public LiveData<List<CityEntity>> getAll() {  return allCities; }

    public void insert(CityEntity city) {
        CityRoomDatabase.databaseWriteExecutor.execute(() -> cityDao.insert(city));
    }

    public void update(CityEntity city) {
        CityRoomDatabase.databaseWriteExecutor.execute(() -> cityDao.update(city));
    }

    public void delete(int idCity) {
        CityRoomDatabase.databaseWriteExecutor.execute(() -> cityDao.deleteById(idCity));
    }
}
