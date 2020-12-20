package dam.iesaugustobriga.cityproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.iesaugustobriga.cityproject.db.entity.CityEntity;

@Dao
public interface CityDao {
    @Insert
    void insert(CityEntity city);

    @Update
    void update(CityEntity city);

    @Query("DELETE FROM city WHERE id = :idCity")
    void deleteById(int idCity);

    @Query("SELECT * FROM city ORDER BY nombre ASC")
    LiveData<List<CityEntity>> getAll();
}

