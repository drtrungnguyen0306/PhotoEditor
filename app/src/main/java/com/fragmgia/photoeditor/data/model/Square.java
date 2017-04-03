package com.fragmgia.photoeditor.data.model;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by trungnguyens93gmail.com on 3/2/17.
 */
public class Square {
    private static final String TAG = "Square";
    private static final int COORDS_PER_VERTEX = 3;
    private final float[] squareCoords = {
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        -1.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f
    };
    private final float[] textureVertices = {
        0.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 0.0f,
        1.0f, 0.0f, 0.0f
    };
    private final String mVertexShader =
        "attribute vec4 aPosition;" +
            "attribute vec2 aTexPosition;" +
            "varying vec2 vTexPosition;" +
            "void main() {" +
            "  gl_Position = aPosition;" +
            "  vTexPosition = aTexPosition;" +
            "}";
    private final String mFragmentShader =
        "precision mediump float;" +
            "uniform sampler2D uTexture;" +
            "varying vec2 vTexPosition;" +
            "void main() {" +
            "  gl_FragColor = texture2D(uTexture, vTexPosition);" +
            "}";
    private final int mVertexStride = COORDS_PER_VERTEX * 4;
    private FloatBuffer mVerticesBuffer;
    private FloatBuffer mTexVerticesBuffer;
    private int maPositionHandle;
    private int maTexPositionHandle;
    private int muTextureHandle;
    private int mProgram;
    private int mViewWidth;
    private int mViewHeight;

    public Square() {
        initializeBuffers();
        mProgram = createProgram(mVertexShader, mFragmentShader);
    }

    public void setViewWidth(int viewWidth) {
        mViewWidth = viewWidth;
    }

    public void setViewHeight(int viewHeight) {
        mViewHeight = viewHeight;
    }

    public void initializeBuffers() {
        ByteBuffer verticesBuffer = ByteBuffer.allocateDirect(squareCoords.length * 4);
        verticesBuffer.order(ByteOrder.nativeOrder());
        mVerticesBuffer = verticesBuffer.asFloatBuffer();
        mVerticesBuffer.put(squareCoords);
        mVerticesBuffer.position(0);

        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(textureVertices.length * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        mTexVerticesBuffer = textureBuffer.asFloatBuffer();
        mTexVerticesBuffer.put(textureVertices);
        mTexVerticesBuffer.position(0);
    }

    public void draw(int textureId) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glViewport(0, 0, mViewWidth, mViewHeight);

        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glVertexAttribPointer(maPositionHandle, COORDS_PER_VERTEX,
            GLES20.GL_FLOAT, false,
            mVertexStride, mVerticesBuffer);

        maTexPositionHandle = GLES20.glGetAttribLocation(mProgram, "aTexPosition");
        GLES20.glEnableVertexAttribArray(maTexPositionHandle);
        GLES20.glVertexAttribPointer(maTexPositionHandle, COORDS_PER_VERTEX,
            GLES20.GL_FLOAT, false,
            mVertexStride, mTexVerticesBuffer);

        muTextureHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(muTextureHandle, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glEnable(GLES20.GL_DITHER);
        GLES20.glDisableVertexAttribArray(maPositionHandle);
    }

    public void onPhotoSizeChanged(int photoWidth, int photoHeight) {
        float viewRatio = (float) mViewWidth / mViewHeight;
        float photoRatio = (float) photoWidth / photoHeight;
        float ratio = viewRatio / photoRatio;
        float x0, y0, x1, y1;
        if (ratio > 1.0f) {
            x0 = -1.0f / ratio;
            y0 = -1.0f;
            x1 = 1.0f / ratio;
            y1 = 1.0f;
        } else {
            x0 = -1.0f;
            y0 = -ratio;
            x1 = 1.0f;
            y1 = ratio;
        }
        float[] coords = new float[]{x0, y0, 0.0f, x1, y0, 0.0f, x0, y1, 0.0f, x1, y1, 0.0f};
        mVerticesBuffer.put(coords).position(0);
    }

    public int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) return 0;
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragmentShader == 0) return 0;
        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGLError("glAttachShader");
            GLES20.glAttachShader(program, fragmentShader);
            checkGLError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    public int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public void checkGLError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(op + " : glError " + error);
        }
    }
}
