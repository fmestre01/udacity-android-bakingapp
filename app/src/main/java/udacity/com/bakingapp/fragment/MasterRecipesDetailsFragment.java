package udacity.com.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import udacity.com.bakingapp.R;
import udacity.com.bakingapp.adapter.ReceitaAdapter;
import udacity.com.bakingapp.model.Receita;

public class MasterRecipesDetailsFragment extends Fragment implements
        ReceitaAdapter.ListItemClickListener {
    private static final String RECIPE = "arg_recipe";

    private View view;

    private OnRecipeClickListener mCallback;

    private Receita mReceita;

    public MasterRecipesDetailsFragment() {
    }

    public static MasterRecipesDetailsFragment newInstance(Receita receita) {
        MasterRecipesDetailsFragment fragment = new MasterRecipesDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE, receita);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mReceita = getArguments().getParcelable(RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.master_recipes_fragment, container, false);

            CardView ingredientCardView = (CardView) view.findViewById(R.id.ingredients_card);
            ingredientCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null) {
                        mCallback.onIngredientsSelected(view, mReceita);
                    }
                }
            });

            RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.steps_recycler);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ReceitaAdapter mAdapter = new ReceitaAdapter(getActivity(), this);
            mAdapter.refill(mReceita, 1);
            mRecyclerView.setAdapter(mAdapter);

            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        return view;
    }

    private ArrayList<Object> getSampleArrayList(List<Receita> receita) {
        ArrayList<Object> items = new ArrayList<>();

        for (int i = 0; i < receita.size(); i++) {
            items.add(i, receita.get(i));
        }
        return items;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener) {
            mCallback = (OnRecipeClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        mCallback.onStepSelected(clickedItemIndex);
    }

    public interface OnRecipeClickListener {
        void onIngredientsSelected(View view, Receita receita);

        void onStepSelected(int position);
    }
}
