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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 3/16/17.
 */
public class CombiningImageAdapter
    extends RecyclerView.Adapter<CombiningImageAdapter.CombiningImageViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ImageInfo> mImageInfos;

    public CombiningImageAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setImageInfos(List<ImageInfo> imageInfos) {
        mImageInfos = imageInfos;
    }

    @Override
    public CombiningImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_combining_image, parent, false);
        return new CombiningImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CombiningImageViewHolder holder, int position) {
        ImageInfo imageInfo = mImageInfos.get(position);
        holder.bind(imageInfo);
    }

    @Override
    public int getItemCount() {
        return mImageInfos == null ? 0 : mImageInfos.size();
    }

    class CombiningImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_combine)
        ImageView mAvartaImageView;

        public CombiningImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ImageInfo imageInfo) {
            if (imageInfo == null) return;
            mAvartaImageView.setImageURI(Uri.parse(imageInfo.getPath()));
        }
    }
}
