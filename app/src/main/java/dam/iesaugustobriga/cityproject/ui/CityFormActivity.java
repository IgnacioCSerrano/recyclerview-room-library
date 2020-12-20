package dam.iesaugustobriga.cityproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;

import dam.iesaugustobriga.cityproject.R;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;
import dam.iesaugustobriga.cityproject.viewmodel.CityViewModel;

public class CityFormActivity extends AppCompatActivity {

    private CityEntity city;
    private EditText etNombre, etLink, etDescripcion;
    private ImageView ivFoto;
    private RatingBar ratingBar;
    private ImageButton btnSave;
    private CityViewModel mViewModel;

    private void showImage() {
        Glide.with(this)
                .load(etLink.getText().toString())
                .centerCrop()
                .into(ivFoto);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_form);
        mViewModel = new ViewModelProvider(this).get(CityViewModel.class);

        if (getIntent().getExtras() != null) {
            city = (CityEntity) getIntent().getSerializableExtra("city");
        }

        setTitle((city == null) ? "Añadir ciudad" : "Editar ciudad");

        etNombre = findViewById(R.id.editTextNombre);
        etLink = findViewById(R.id.editTextLink);
        etDescripcion = findViewById(R.id.editTextDescripcion);
        ivFoto = findViewById(R.id.imageViewFoto);
        ratingBar = findViewById(R.id.ratingBar);
        btnSave = findViewById(R.id.btnSave);

        if (city != null) {
            etNombre.setText(city.getNombre());
            etLink.setText(city.getRuta());
            etDescripcion.setText(city.getDescripcion());
            ratingBar.setRating(city.getPuntuacion());
            btnSave.setImageResource(R.drawable.ic_baseline_edit_24);
            showImage();
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String link = etLink.getText().toString().startsWith("http")
                ? etLink.getText().toString()
                : "http://" + etLink.getText().toString();
            String descripcion = etDescripcion.getText().toString();
            float puntos = ratingBar.getRating();

            if (nombre.isEmpty()) {
                etNombre.setError("Escriba un nombre para la ciudad");
                etNombre.requestFocus();
            } else if (link.equals("http://")) {
                etLink.setError("Escriba un enlace de imagen para la ciudad");
                etLink.requestFocus();
            } else if (descripcion.isEmpty()) {
                etDescripcion.setError("Escriba una descripción para la ciudad");
                etDescripcion.requestFocus();
            } else if (descripcion.length() > 100 || etDescripcion.getLineCount() > 3) {
                etDescripcion.setError("La descripción no debe superar 100 caracteres u ocupar más de tres líneas");
                etDescripcion.requestFocus();
            } else {
                if (city == null) {
                    mViewModel.insertCity(new CityEntity(nombre, link, descripcion, puntos));
                } else {
                    city.setNombre(nombre);
                    city.setRuta(link);
                    city.setDescripcion(descripcion);
                    city.setPuntuacion(puntos);
                    mViewModel.updateCity(city);
                }
                this.finish();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(v -> this.finish());

        findViewById(R.id.btnPreview).setOnClickListener(v -> {
            if (!etLink.getText().toString().isEmpty()) {
                showImage();
            }
        });
    }
}