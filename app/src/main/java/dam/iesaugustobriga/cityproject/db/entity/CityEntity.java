package dam.iesaugustobriga.cityproject.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "city")
public class CityEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String nombre;
    private String ruta;
    private String descripcion;
    private float puntuacion;

    public CityEntity(String nombre, String ruta, String descripcion, float puntuacion) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }
}
