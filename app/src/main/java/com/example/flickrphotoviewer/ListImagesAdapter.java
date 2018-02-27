package com.example.flickrphotoviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cepreu on 29.08.17.
 */

class ListImagesAdapter extends RecyclerView.Adapter<ListImagesAdapter.ViewHolder> {
    private List<Photo> photos = new ArrayList();
    private static final int PREVIEW_HEIGHT = 128;
    private static final int PREVIEW_WIDTH = 128;
    private static final int DEFAULT_PADDING = 8;
    private OnItemClickListener clickListener;
    private RecyclerView recyclerView;

    public interface OnItemClickListener {
        void onItemClick(View view, Photo photo);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public ListImagesAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static int pxFromDp(Context context, int dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (dp * scale + 0.5f);
        return padding_in_px;
    }

    public static int getElementWidth(Context context){
        return pxFromDp(context, PREVIEW_WIDTH) + 2 * pxFromDp(context, DEFAULT_PADDING);
    }

    @Override
    public ListImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        ImageView v = new ImageView(parent.getContext());
        v.setMinimumHeight(pxFromDp(parent.getContext(), PREVIEW_HEIGHT));
        v.setMinimumWidth(pxFromDp(parent.getContext(), PREVIEW_WIDTH));
        // borders between images
        int padding = pxFromDp(parent.getContext(), DEFAULT_PADDING);
        v.setPadding(padding, padding, padding, padding);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(view,
                        photos.get(recyclerView.getChildAdapterPosition(view)));
            }
        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideApp.with(holder.imageView)
                .load(photos.get(position).previewUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
