package dam.iesaugustobriga.cityproject.ui;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import dam.iesaugustobriga.cityproject.R;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;
import dam.iesaugustobriga.cityproject.viewmodel.CityViewModel;

public class CityDialogFragment extends DialogFragment {

    private CityEntity city;
    private View view;
    private EditText etNombre, etLink, etDescripcion;
    private ImageView ivFoto;
    private ImageButton btnPreview;
    private RatingBar ratingBar;

    public CityDialogFragment(CityEntity city) {
        this.city = city;
    }

    private void showImage() {
        Glide.with(getActivity())
                .load(etLink.getText().toString())
                .centerCrop()
                .into(ivFoto);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title = (city == null) ? "Añadir ciudad" : "Editar ciudad";
        String message = (city == null) ? "Introduzca los datos de la nueva ciudad" : "Edite los datos de la ciudad existente";

        builder.setTitle(title);
        builder.setMessage(message)
                .setPositiveButton("Guardar", (dialog, id) -> {
                    String nombre = etNombre.getText().toString();
                    String link = etLink.getText().toString();
                    String descripcion = etDescripcion.getText().toString();
                    float puntos = ratingBar.getRating();

                    CityViewModel mViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);

                    if (city == null) {
                        mViewModel.insertCity(new CityEntity(nombre, link, descripcion, puntos));
                    } else {
                        city.setNombre(nombre);
                        city.setRuta(link);
                        city.setDescripcion(descripcion);
                        city.setPuntuacion(puntos);
                        mViewModel.updateCity(city);
                    }

                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        view = inflater.inflate(R.layout.city_dialog_fragment, null);
        view = View.inflate(getActivity(), R.layout.city_dialog_fragment, null);

        etNombre = view.findViewById(R.id.editTextNombre);
        etLink = view.findViewById(R.id.editTextLink);
        etDescripcion = view.findViewById(R.id.editTextDescripcion);
        ivFoto = view.findViewById(R.id.imageViewFoto);
        btnPreview = view.findViewById(R.id.btnPreview);
        ratingBar = view.findViewById(R.id.ratingBar);

        if (city != null) {
            etNombre.setText(city.getNombre());
            etLink.setText(city.getRuta());
            etDescripcion.setText(city.getDescripcion());
            ratingBar.setRating(city.getPuntuacion());
            showImage();
        }

        btnPreview.setOnClickListener(v -> {
            if (!etLink.getText().toString().isEmpty()) {
                showImage();
            }
        });

        builder.setView(view);

        //set to adjust screen height automatically, when soft keyboard appears on screen
//        Dialog dialog = builder.create();
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return builder.create();
    }

}