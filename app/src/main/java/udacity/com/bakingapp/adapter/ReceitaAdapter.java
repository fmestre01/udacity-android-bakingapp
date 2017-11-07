package udacity.com.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import udacity.com.bakingapp.R;
import udacity.com.bakingapp.model.Ingrediente;
import udacity.com.bakingapp.model.Passos;
import udacity.com.bakingapp.model.Receita;

public class ReceitaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ReceitaAdapter";
    private final int INGREDIENT = 0, STEP = 1;
    Context mContext;
    private List<Object> items = new ArrayList<>();
    private ListItemClickListener mOnClickListener;

    public ReceitaAdapter() {

    }

    public ReceitaAdapter(Context context, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Ingrediente) {
            return INGREDIENT;
        } else if (items.get(position) instanceof Passos) {
            return STEP;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case INGREDIENT:
                View v1 = inflater.inflate(R.layout.item_layout_ingredient, viewGroup, false);
                viewHolder = new ViewHolderIngredient(v1);
                break;
            case STEP:
                View v2 = inflater.inflate(R.layout.item_layout_step, viewGroup, false);
                viewHolder = new ViewHolderStep(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case INGREDIENT:
                ViewHolderIngredient vh1 = (ViewHolderIngredient) viewHolder;
                configureViewHolderIngredient(vh1, position);
                break;
            case STEP:
                ViewHolderStep vh2 = (ViewHolderStep) viewHolder;
                configureViewHolderStep(vh2, position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolderIngredient(ViewHolderIngredient vh1, int position) {
        Ingrediente ingredient = (Ingrediente) items.get(position);
        if (ingredient != null) {
            vh1.getIngredientName().setText(ingredient.getIngrediente());
            vh1.getIngredientQuantity().setText("Quantidade: " + ingredient.getQuantidade());
            vh1.getIngredientMeasure().setText("Medida: " + ingredient.getMedida());
        }
    }

    private void configureViewHolderStep(ViewHolderStep vh2, int position) {
        vh2.getTextView().setText("Passo " + (position + 1) + ": " + ((Passos) items.get(position)).getDescricaoCurta());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refill(Receita recipes, int type) {
        if (items != null) {
            items.clear();
        }
        if (type == INGREDIENT) {
            items.addAll(recipes.getIngredientes());
            notifyDataSetChanged();
        } else if (type == STEP) {
            items.addAll(recipes.getPassos());
            notifyDataSetChanged();
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class ViewHolderIngredient extends RecyclerView.ViewHolder {
        private final TextView ingredientName, ingredientQuantity, ingredientMeasure;

        public ViewHolderIngredient(View v) {
            super(v);
            ingredientName = (TextView) v.findViewById(R.id.ingredient_name);
            ingredientQuantity = (TextView) v.findViewById(R.id.ingredient_quantity);
            ingredientMeasure = (TextView) v.findViewById(R.id.ingredient_measure);
        }

        public TextView getIngredientName() {
            return ingredientName;
        }

        public TextView getIngredientQuantity() {
            return ingredientQuantity;
        }

        public TextView getIngredientMeasure() {
            return ingredientMeasure;
        }

    }

    public class ViewHolderStep extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolderStep(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mOnClickListener.onListItemClick(clickedPosition);
                }
            });
            textView = (TextView) v.findViewById(R.id.step_number_text);
        }

        public TextView getTextView() {
            return textView;
        }

    }
}
