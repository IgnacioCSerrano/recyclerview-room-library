package dam.iesaugustobriga.cityproject.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dam.iesaugustobriga.cityproject.R;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;
import dam.iesaugustobriga.cityproject.viewmodel.CityViewModel;

/**
 * A fragment representing a list of Items.
 */
public class CityFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyCityRecyclerViewAdapter adapterCity;
    private List<CityEntity> cityList;
    private CityViewModel cityViewModel;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityFragment() {
    }

    public static CityFragment newInstance(int columnCount) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            cityList = new ArrayList<>();

            adapterCity = new MyCityRecyclerViewAdapter(getActivity(), cityList);
            recyclerView.setAdapter(adapterCity);

            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {
        cityViewModel = new ViewModelProvider(getActivity()).get(CityViewModel.class);
        cityViewModel.getAllCities().observe(getActivity(), cityEntities -> adapterCity.setNewCities(cityEntities));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_city_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_add_city) {
            mostrarActivityForm();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarActivityForm() {
//        FragmentManager fm = ((FragmentActivity)ctx).getSupportFragmentManager();
//        CityDialogFragment dialogNota = new CityDialogFragment(null);
//        dialogNota.show(fm, "Añadir ciudad");

        Intent intent = new Intent(getActivity(), CityFormActivity.class);
        startActivity(intent);
    }
}