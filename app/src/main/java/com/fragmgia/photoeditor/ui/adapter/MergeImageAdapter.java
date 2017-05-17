package com.fragmgia.photoeditor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Control;
import com.fragmgia.photoeditor.ui.activity.merge.MergeContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 5/17/17.
 */
public class MergeImageAdapter extends RecyclerView.Adapter<MergeImageAdapter.MergeHolder> {
    private List<Control> mControls;
    private LayoutInflater mInflater;
    private Context mContext;
    private MergeContract.View mView;

    public MergeImageAdapter(Context context, List<Control> controls, MergeContract.View view) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mControls = controls;
        mView = view;
    }

    @Override
    public MergeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_control_image, parent, false);
        return new MergeHolder(view);
    }

    @Override
    public void onBindViewHolder(MergeHolder holder, int position) {
        Control control = mControls.get(position);
        holder.bind(control);
    }

    @Override
    public int getItemCount() {
        return mControls == null ? 0 : mControls.size();
    }

    public class MergeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_feature)
        ImageView mImageControl;
        @BindView(R.id.text_feature)
        TextView mTextControl;
        @BindView(R.id.linear_feature)
        LinearLayout mLinearLayout;

        public MergeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Control control) {
            mTextControl.setText(control.getTitle() != null ? control.getTitle() : "");
            mImageControl.setImageResource(control.getImage());
        }

        @OnClick(R.id.linear_feature)
        public void clickItem() {
            if (mView == null) return;
            mView.selectControl(getAdapterPosition());
        }
    }
}
