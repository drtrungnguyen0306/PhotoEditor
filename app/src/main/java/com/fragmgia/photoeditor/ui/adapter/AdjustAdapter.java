package com.fragmgia.photoeditor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.activity.adjust.AdjustContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 5/6/17.
 */
public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.AdjustViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Function> mFunctions;
    private AdjustContract.View mView;

    public AdjustAdapter(Context context, List<Function> functions, AdjustContract.View view) {
        mLayoutInflater = LayoutInflater.from(context);
        mFunctions = functions;
        mView = view;
    }

    @Override
    public AdjustViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_function, parent, false);
        return new AdjustViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdjustViewHolder holder, int position) {
        Function function = mFunctions.get(position);
        holder.bind(function);
    }

    @Override
    public int getItemCount() {
        return mFunctions == null ? 0 : mFunctions.size();
    }

    class AdjustViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_layout_function)
        LinearLayout mLinearLayout;
        @BindView(R.id.image_function)
        ImageView mFunctionImageView;
        @BindView(R.id.text_function)
        TextView mFunctionTextView;

        public AdjustViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Function function) {
            if (function == null) return;
            mFunctionImageView.setImageResource(function.getImageId());
            mFunctionTextView.setText(function.getName());
        }

        @OnClick(R.id.linear_layout_function)
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (mView == null) return;
            mView.selectFunction(mFunctions.get(pos));
        }
    }
}
