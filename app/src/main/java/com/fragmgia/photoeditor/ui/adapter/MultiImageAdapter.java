package com.fragmgia.photoeditor.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.multiphoto.MultiImageContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/12/17.
 */
public class MultiImageAdapter
    extends RecyclerView.Adapter<MultiImageAdapter.MultiImageViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ImageInfo> mImageInfos;
    private MultiImageContract.View mView;

    public MultiImageAdapter(Context context, MultiImageContract.View view) {
        mLayoutInflater = LayoutInflater.from(context);
        mView = view;
    }

    public void setImageInfos(List<ImageInfo> imageInfos) {
        mImageInfos = imageInfos;
    }

    @Override
    public MultiImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_multi_image, parent, false);
        return new MultiImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MultiImageViewHolder holder, int position) {
        ImageInfo imageInfo = mImageInfos.get(position);
        holder.bind(imageInfo);
    }

    @Override
    public int getItemCount() {
        return mImageInfos == null ? 0 : mImageInfos.size();
    }

    class MultiImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.relative_multi_image)
        RelativeLayout mRelativeLayout;
        @BindView(R.id.image_avatar_multi)
        ImageView mAvatarImageView;
        @BindView(R.id.check_box_multi)
        CheckBox mAvatarCheckBox;

        public MultiImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ImageInfo imageInfo) {
            if (imageInfo == null) return;
            mAvatarImageView.setImageURI(Uri.parse(imageInfo.getPath()));
        }

        @OnClick({R.id.relative_multi_image, R.id.check_box_multi})
        public void onCheckedImage(View view) {
            if (view.getId() == R.id.relative_multi_image) {
                mAvatarCheckBox.setChecked(!mAvatarCheckBox.isChecked());
            }
            int pos = getAdapterPosition();
            if (mAvatarCheckBox.isChecked()) {
                mView.checkItem(mImageInfos.get(pos));
            } else {
                mView.uncheckItem(mImageInfos.get(pos));
            }
        }
    }
}
