package com.fragmgia.photoeditor.util;

/**
 * Created by trungnguyens93gmail.com on 2/23/17.
 */
public class ConstantManager {
    public static final int LONG_TIME_THREAD = 2000;
    public static final int NUM_COL_OF_ROW_RECYCLE_VIEW = 3;
    public static final int REQUEST_TAKE_IMAGE = 1;
    public static final int PERMISSION_REQUEST_CODE = 200;
    public static final int MAX_VALUE_SEEK_BAR = 100;
    public static final String CAPTURE_IMAGE = "CAPTURE_IMAGE";
    public static final String EXTRA_BITMAP_CAPTURE = "data";
    public static final String EXTRA_TYPE_OF_IMAGE = "EXTRA_TYPE_OF_IMAGE";
    public static final String EXTRA_IMAGE_INFO = "EXTRA_IMAGE_INFO";
    public static final String EXTRA_BITMAP = "EXTRA_BITMAP";
    public static final String EXTRA_LIST_IMAGE_INFO = "EXTRA_LIST_IMAGE_INFO";
    public static final String EXTRA_AUDIO = "EXTRA_AUDIO";

    public class Functions {
        public static final String EFFECT_FUNTION = "Effect";
        public static final String CROP_FUNCTION = "Crop";
        public static final String COLOR_FUNCTION = "Color";
        public static final String ADJUST_FUNCTION = "Adjust";
    }

    public class Effects {
        public static final String EFFECT_NONE = "None";
        public static final String EFFECT_CROSSPROCESS = "Cross";
        public static final String EFFECT_DOCUMENTARY = "Document";
        public static final String EFFECT_GRAYSCALE = "GrayScale";
        public static final String EFFECT_FILLLIGHT = "FillLight";
        public static final String EFFECT_LOMOISH = "Lomoish";
        public static final String EFFECT_SEPIA = "Sepia";
        public static final String EFFECT_SHARPEN = "Sharpen";
        public static final String EFFECT_TEMPERATURE = "Tempera";
    }

    public class Adjusts {
        public static final String BRIGHTNESS_FUNCTION = "Brightness";
        public static final String CONTRAST_FUNCTION = "Contrast";
        public static final String HUE_FUNCTION = "HUE";
    }
}
