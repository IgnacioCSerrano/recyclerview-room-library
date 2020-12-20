package dam.iesaugustobriga.cityproject.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dam.iesaugustobriga.cityproject.db.CityRepository;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;

public class CityViewModel extends AndroidViewModel {

    private final LiveData<List<CityEntity>> allCities;
    private final CityRepository cityRepository;

    public CityViewModel(Application application) {
        super(application);
        cityRepository = new CityRepository(application);
        allCities = cityRepository.getAll();
    }

    public LiveData<List<CityEntity>> getAllCities() { return allCities; }

    public void insertCity(CityEntity newCity) { cityRepository.insert(newCity); }

    public void updateCity(CityEntity updatedCity) { cityRepository.update(updatedCity); }

    public void deleteCity(CityEntity deletedCity) { cityRepository.delete(deletedCity.getId()); }

}