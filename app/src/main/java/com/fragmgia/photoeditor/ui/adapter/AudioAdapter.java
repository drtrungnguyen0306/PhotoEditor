package com.fragmgia.photoeditor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.ui.activity.audio.AudioContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/14/17.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<AudioInfo> mAudioInfos;
    private AudioContract.View mView;

    public AudioAdapter(Context context, AudioContract.View view) {
        mLayoutInflater = LayoutInflater.from(context);
        mView = view;
    }

    public void setAudioInfos(List<AudioInfo> audioInfos) {
        mAudioInfos = audioInfos;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_audio, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, int position) {
        AudioInfo audioInfo = mAudioInfos.get(position);
        holder.bind(audioInfo);
    }

    @Override
    public int getItemCount() {
        return mAudioInfos == null ? 0 : mAudioInfos.size();
    }

    class AudioViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_audio)
        CardView mAudioCardView;
        @BindView(R.id.text_audio_name)
        TextView mNameTextView;

        AudioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AudioInfo audioInfo) {
            if (audioInfo == null) return;
            mNameTextView.setText(audioInfo.getName());
        }

        @OnClick(R.id.card_view_audio)
        public void onClick(View view) {
            if (mView == null) return;
            int pos = getAdapterPosition();
            mView.selectAudio(mAudioInfos.get(pos));
        }
    }
}
