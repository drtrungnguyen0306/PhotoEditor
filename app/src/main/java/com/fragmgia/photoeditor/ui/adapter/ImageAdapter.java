package com.fragmgia.photoeditor.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.images.ImagesContract;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ImageInfo> mImageInfos;
    private ImagesContract.View mListener;

    public ImageAdapter(Context context, ImagesContract.View listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    public void setImageInfos(List<ImageInfo> imageInfos) {
        mImageInfos = imageInfos;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageInfo imageInfo = mImageInfos.get(position);
        holder.bind(imageInfo);
    }

    @Override
    public int getItemCount() {
        return mImageInfos == null ? 0 : mImageInfos.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_avatar)
        ImageView mAvatar;

        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ImageInfo imageInfo) {
            if (imageInfo == null) return;
            if (imageInfo.getName().equals(ConstantManager.CAPTURE_IMAGE))
                mAvatar.setImageResource(R.drawable.ic_photo);
            else
                mAvatar.setImageURI(Uri.parse(imageInfo.getPath()));
        }

        @OnClick(R.id.image_avatar)
        void onClick(View view) {
            int pos = getAdapterPosition();
            if (mListener != null) mListener.selectSingleImage(mImageInfos.get(pos));
        }
    }
}
