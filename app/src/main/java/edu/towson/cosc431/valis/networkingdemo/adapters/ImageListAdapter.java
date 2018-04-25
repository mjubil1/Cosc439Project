package edu.towson.cosc431.valis.networkingdemo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.towson.cosc431.valis.networkingdemo.models.Avatar;
import edu.towson.cosc431.valis.networkingdemo.R;
import edu.towson.cosc431.valis.networkingdemo.async.ImageFetcher;

/**
 * Created by randy on 3/29/17.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private List<Avatar> data;
    private Handler uiHandler;

    public ImageListAdapter(List<Avatar> data, Handler handler) {
        this.data = data;
        // this handler comes from the Main UI thread.
        // it is used to update the UI
        this.uiHandler = handler;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Avatar avatar = data.get(position);
        // show the progress spinner
        holder.reset();
        holder.bind(avatar);

        // fetch the image icon asynchronously
        ImageFetcher.fetch(holder.getContext(), avatar, new ImageFetcherImpl(holder));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            imageView.setVisibility(View.INVISIBLE);
            textView = (TextView)itemView.findViewById(R.id.textView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }

        /**
         * Makes the progress bar visible and hides the icon
         */
        public void reset() {
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            imageView.setImageDrawable(null);
        }

        /**
         * Makes the icon visible and hides the progress bar
         */
        public void set() {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }

        /**
         * Binds the Avatar's text to the textview
         * @param avatar
         */
        public void bind(Avatar avatar) {
            textView.setText(avatar.name);
        }

        /**
         * Clears the icon view
         */
        public void clear() {
            imageView.setImageBitmap(null);
            set();
        }

        public Context getContext() {
            return itemView.getContext();
        }
    }

    /**
     * Implements the image fetch interface
     */
    class ImageFetcherImpl implements ImageFetcher.Callback {

        private ImageViewHolder holder;
        public ImageFetcherImpl(ImageViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onSuccess(final Bitmap img, Avatar avatar) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    holder.imageView.setImageBitmap(img);
                    holder.set();
                }
            });
        }

        @Override
        public void onError(final String error) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    holder.clear();
                    Toast.makeText(holder.imageView.getContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
