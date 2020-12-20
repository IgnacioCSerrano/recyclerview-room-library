package dam.iesaugustobriga.cityproject.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
//import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import dam.iesaugustobriga.cityproject.R;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;
import dam.iesaugustobriga.cityproject.viewmodel.CityViewModel;

public class MyCityRecyclerViewAdapter extends RecyclerView.Adapter<MyCityRecyclerViewAdapter.ViewHolder> {

    private final Context ctx;
    private List<CityEntity> mValues;
    private final CityViewModel viewModel;

    public MyCityRecyclerViewAdapter(Context context, List<CityEntity> items) {
        ctx = context;
        mValues = items;
        viewModel = new ViewModelProvider((AppCompatActivity)ctx).get(CityViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    private AlertDialog confirmarBorrado(CityEntity city)
    {
        return new AlertDialog.Builder(ctx)
                .setTitle("Borrar")
                .setMessage("¿Está seguro de que desea borrar esta ciudad?")
                .setIcon(R.drawable.ic_baseline_delete_24)

                .setPositiveButton("Borrar", (dialog, whichButton) -> {
                    viewModel.deleteCity(city);
                    dialog.dismiss();
                })

                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.textViewNombre.setText(holder.mItem.getNombre());
        holder.textViewDesc.setText(holder.mItem.getDescripcion());
//        holder.textViewDesc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        holder.textViewPunt.setText(String.format(Locale.getDefault(),"%.1f / 5.0", holder.mItem.getPuntuacion()));

        Glide.with(ctx)
                .load(holder.mItem.getRuta())
                .centerCrop()
                .error(R.drawable.dummy)
                .into(holder.imageViewFoto);

        holder.mView.setOnClickListener(v -> {
//            FragmentManager fm = ((FragmentActivity)ctx).getSupportFragmentManager();
//            CityDialogFragment dialogNota = new CityDialogFragment(holder.mItem);
//            dialogNota.show(fm, "Editar ciudad");

            Intent intent = new Intent(ctx, CityFormActivity.class);
            intent.putExtra("city", holder.mItem);
            ctx.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> {
            AlertDialog diaBox = confirmarBorrado(holder.mItem);
            diaBox.show();
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNewCities(List<CityEntity> newCities) {
        this.mValues = newCities;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewFoto;
        public final TextView textViewNombre;
        public final TextView textViewDesc;
        public final TextView textViewPunt;
        public final ImageView ivDelete;
        public CityEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewFoto = view.findViewById(R.id.imageViewCity);
            textViewNombre = view.findViewById(R.id.textViewCity);
            textViewDesc = view.findViewById(R.id.textViewDesc);
            textViewPunt = view.findViewById(R.id.textViewScore);
            ivDelete = view.findViewById(R.id.imageViewDelete);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + textViewNombre.getText() + "'";
        }
    }
}