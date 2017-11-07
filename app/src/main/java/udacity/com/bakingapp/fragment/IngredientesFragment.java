package udacity.com.bakingapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import udacity.com.bakingapp.R;
import udacity.com.bakingapp.adapter.ReceitaAdapter;
import udacity.com.bakingapp.model.Receita;

public class IngredientesFragment extends Fragment {
    private static final String PARAM1 = "param1";

    private Receita mParam1;
    private View view;

    public IngredientesFragment() {
    }

    public static IngredientesFragment newInstance(Receita param1) {
        IngredientesFragment fragment = new IngredientesFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.ingredients_fragments, container, false);

            RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.ingredients_recycler);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ReceitaAdapter mAdapter = new ReceitaAdapter();
            mAdapter.refill(mParam1, 0);
            mRecyclerView.setAdapter(mAdapter);

            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        return view;
    }
}
