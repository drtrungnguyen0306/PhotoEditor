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
import com.fragmgia.photoeditor.ui.activity.selectedimage.SelectedImageContract;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 5/17/17.
 */
public class SelectedImageAdapter
    extends RecyclerView.Adapter<SelectedImageAdapter.SelectedImageViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ImageInfo> mImageInfos;
    private SelectedImageContract.View mListener;

    public SelectedImageAdapter(Context context, SelectedImageContract.View listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    public void setImageInfos(List<ImageInfo> imageInfos) {
        mImageInfos = imageInfos;
    }

    @Override
    public SelectedImageAdapter.SelectedImageViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_image, parent, false);
        return new SelectedImageAdapter.SelectedImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedImageViewHolder holder, int position) {
        ImageInfo imageInfo = mImageInfos.get(position);
        holder.bind(imageInfo);
    }

    @Override
    public int getItemCount() {
        return mImageInfos == null ? 0 : mImageInfos.size();
    }

    class SelectedImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_avatar)
        ImageView mAvatar;

        SelectedImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ImageInfo imageInfo) {
            if (imageInfo == null) return;
            mAvatar.setImageURI(Uri.parse(imageInfo.getPath()));
        }

        @OnClick(R.id.image_avatar)
        void onClick(View view) {
            int pos = getAdapterPosition();
            if (mListener != null) mListener.selectSingleImage(mImageInfos.get(pos));
        }
    }
}
