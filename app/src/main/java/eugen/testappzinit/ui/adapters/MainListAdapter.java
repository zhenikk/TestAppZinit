package eugen.testappzinit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eugen.testappzinit.R;
import eugen.testappzinit.model.BashImageModel;

/**
 * Created by Yevhenii on 16.05.16.
 */
public class MainListAdapter extends
        RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private static OnItemClickListener listener;
    private List<BashImageModel> mList;

    private Context context;

    public MainListAdapter(List<BashImageModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        BashImageModel item = mList.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(item.getTitle());
        ImageView imageView = viewHolder.imageView;
        Picasso.with(context).load(item.getThumbnail()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            nameTextView = (TextView) itemView.findViewById(R.id.textInfo);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            if (listener != null) listener.onItemClick(v, position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        MainListAdapter.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

}


