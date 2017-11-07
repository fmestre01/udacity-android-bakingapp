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

import udacity.com.bakingapp.R;
import udacity.com.bakingapp.model.Receita;

public class PrincipalAdapter extends RecyclerView.Adapter<PrincipalAdapter.ViewHolder> {
    private static final String TAG = "PrincipalAdapter";

    final private ListItemClickListener mOnClickListener;
    Context mContext;

    private ArrayList<Receita> mReceita;

    public PrincipalAdapter(Context context, ArrayList<Receita> dataSet, ListItemClickListener listener) {
        mContext = context;
        mReceita = dataSet;
        mOnClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout_recipe, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(mReceita.get(position).getNome());
        Glide.with(mContext).load(mReceita.get(position).getNome()).centerCrop()
                .placeholder(R.mipmap.recipe).into(viewHolder.getImageView());

    }

    @Override
    public int getItemCount() {
        return mReceita.size();
    }

    public void refill(ArrayList<Receita> receitas) {
        if (mReceita != null) {
            mReceita.clear();
        }
        mReceita.addAll(receitas);
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mOnClickListener.onListItemClick(clickedPosition);
                }
            });
            textView = (TextView) v.findViewById(R.id.recipe_name);
            imageView = (ImageView) v.findViewById(R.id.recipe_image);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }
}
