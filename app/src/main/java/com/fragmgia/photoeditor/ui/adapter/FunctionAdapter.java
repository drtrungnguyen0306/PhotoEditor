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
import com.fragmgia.photoeditor.ui.activity.function.FunctionContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Function> mFunctions;
    private FunctionContract.View mView;

    public FunctionAdapter(Context context, List<Function> functions, FunctionContract.View view) {
        mLayoutInflater = LayoutInflater.from(context);
        mFunctions = functions;
        mView = view;
    }

    @Override
    public FunctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_function, parent, false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FunctionViewHolder holder, int position) {
        Function function = mFunctions.get(position);
        holder.bind(function);
    }

    @Override
    public int getItemCount() {
        return mFunctions == null ? 0 : mFunctions.size();
    }

    class FunctionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_layout_function)
        LinearLayout mLinearLayout;
        @BindView(R.id.image_function)
        ImageView mFunctionImageView;
        @BindView(R.id.text_function)
        TextView mFunctionTextView;

        public FunctionViewHolder(View itemView) {
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
