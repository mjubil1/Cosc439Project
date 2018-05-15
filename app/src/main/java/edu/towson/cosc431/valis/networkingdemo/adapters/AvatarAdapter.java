package edu.towson.cosc431.valis.networkingdemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.towson.cosc431.valis.networkingdemo.R;
import edu.towson.cosc431.valis.networkingdemo.models.Avatar;
import edu.towson.cosc431.valis.networkingdemo.models.Message;

/**
 * Created by Montrell on 5/15/2018.
 */

public class AvatarAdapter extends  RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {

    Context context;
    List<Message> list;

    public AvatarAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AvatarAdapter.AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);
        AvatarViewHolder viewHolder = new AvatarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {
        Message message = this.list.get(position);
        holder.bindMessageToView(message);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AvatarViewHolder extends RecyclerView.ViewHolder {

        Message message;
        //ImageView imageView;
        TextView textView;
        TextView imgTv,nameTv,messageTv;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            //imageView = (ImageView)itemView.findViewById(R.id.imageView);
            //imageView.setVisibility(View.INVISIBLE);
            //  textView = (TextView)itemView.findViewById(R.id.avatarTv);
            imgTv = (TextView)itemView.findViewById(R.id.imgTv);
            nameTv = (TextView)itemView.findViewById(R.id.nameTv);
            messageTv = (TextView)itemView.findViewById(R.id.messageTv);
        }

        public void bindMessageToView(Message message) {
            this.message = message;
            //imgTv.setText(message.getImg());
            nameTv.setText(message.getName());
            messageTv.setText(message.getMessage());
        }
    }

}
