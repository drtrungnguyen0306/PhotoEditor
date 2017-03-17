package com.fragmgia.photoeditor.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.model.GLToolbox;
import com.fragmgia.photoeditor.data.model.Square;
import com.fragmgia.photoeditor.ui.activity.effect.EffectContract;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.nio.IntBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/3/17.
 */
public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.EffectViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Bitmap mBitmap;
    private List<Function> mFunctions;
    private EffectContract.View mView;
    private boolean mIsNone;

    public EffectAdapter(Context context, Bitmap bitmap, List<Function> functions,
                         EffectContract.View view) {
        mLayoutInflater = LayoutInflater.from(context);
        mBitmap = bitmap;
        mFunctions = functions;
        mView = view;
    }

    @Override
    public EffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_effect, parent, false);
        return new EffectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EffectViewHolder holder, int position) {
        Function function = mFunctions.get(position);
        holder.bind(function);
    }

    @Override
    public int getItemCount() {
        return mFunctions == null ? 0 : mFunctions.size();
    }

    class EffectViewHolder extends RecyclerView.ViewHolder implements GLSurfaceView.Renderer {
        @BindView(R.id.relative_effect)
        RelativeLayout mEffectLayout;
        @BindView(R.id.surface_view_effect)
        GLSurfaceView mSurfaceView;
        @BindView(R.id.text_effect)
        TextView mEffectName;
        private int[] mTextures = new int[2];
        private Square mSquare = new Square();
        private EffectContext mEffectContext;
        private Effect mEffect;
        private Bitmap mEffectBitmap;

        public EffectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSurfaceView.setEGLContextClientVersion(2);
            mSurfaceView.setRenderer(EffectViewHolder.this);
            mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        public void bind(Function function) {
            if (function == null) return;
            mEffectName.setText(function.getName());
        }

        @OnClick(R.id.relative_effect)
        public void onClick(View view) {
            if (mView != null) mView.selectEffect(mEffectBitmap);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.2f, 0.2f, 0.2f, 0.5f);
            mSquare.init();
            loadTextures();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            if (mSquare != null) mSquare.updateViewSize(width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            int pos = getAdapterPosition();
            if (mFunctions.get(pos).getName().equals(ConstantManager.Effects.EFFECT_NONE)) {
                mIsNone = true;
            } else {
                if (mEffectContext == null) {
                    mEffectContext = EffectContext.createWithCurrentGlContext();
                }
                if (mEffect != null) mEffect.release();
                EffectFactory factory = mEffectContext.getFactory();
                switch (mFunctions.get(pos).getName()) {
                    case ConstantManager.Effects.EFFECT_CROSSPROCESS:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
                        break;
                    case ConstantManager.Effects.EFFECT_DOCUMENTARY:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
                        break;
                    case ConstantManager.Effects.EFFECT_FILLLIGHT:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_FILLLIGHT);
                        break;
                    case ConstantManager.Effects.EFFECT_GRAYSCALE:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
                        break;
                    case ConstantManager.Effects.EFFECT_LOMOISH:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_LOMOISH);
                        break;
                    case ConstantManager.Effects.EFFECT_SEPIA:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_SEPIA);
                        break;
                    case ConstantManager.Effects.EFFECT_SHARPEN:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_SHARPEN);
                        break;
                    case ConstantManager.Effects.EFFECT_TEMPERATURE:
                        mEffect = factory.createEffect(EffectFactory.EFFECT_TEMPERATURE);
                        break;
                    default:
                        break;
                }
                mIsNone = false;
                mEffect.apply(mTextures[0], mBitmap.getWidth(), mBitmap.getHeight(), mTextures[1]);
            }
            if (mIsNone) mSquare.renderTexture(mTextures[0]);
            else mSquare.renderTexture(mTextures[1]);
            mEffectBitmap = getEffectBitmap();
        }

        private void loadTextures() {
            mEffectContext = EffectContext.createWithCurrentGlContext();
            GLES20.glGenTextures(2, mTextures, 0);
            mSquare.updateTextureSize(mBitmap.getWidth(), mBitmap.getHeight());
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
            GLToolbox.initTexParams();
        }

        public Bitmap getEffectBitmap() {
            final int width = mSurfaceView.getWidth();
            final int height = mSurfaceView.getHeight();
            IntBuffer ib = IntBuffer.allocate(width * height);
            IntBuffer ibt = IntBuffer.allocate(width * height);
            GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ibt.put((height - i - 1) * width + j, ib.get(i * width + j));
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(ibt);
            return bitmap;
        }
    }
}
