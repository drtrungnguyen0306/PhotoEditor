package com.fragmgia.photoeditor.data.source.local;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class FunctionsDataSource {
    public static List<Function> getFunctions() {
        return Arrays.asList(
            new Function(R.drawable.ic_function_effect, ConstantManager.Functions.EFFECT_FUNTION),
            new Function(R.drawable.ic_function_crop, ConstantManager.Functions.CROP_FUNCTION),
            new Function(R.drawable.ic_function_color, ConstantManager.Functions.COLOR_FUNCTION),
            new Function(R.drawable.ic_adjust, ConstantManager.Functions.ADJUST_FUNCTION));
    }

    public static List<Function> getEffectsFunction() {
        return Arrays.asList(
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_NONE),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_CROSSPROCESS),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_DOCUMENTARY),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_FILLLIGHT),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_GRAYSCALE),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_LOMOISH),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_SEPIA),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_SHARPEN),
            new Function(R.drawable.ic_function_effect, ConstantManager.Effects.EFFECT_TEMPERATURE)
        );
    }

    public static List<Function> getAdjustFunctions() {
        return Arrays.asList(
            new Function(R.drawable.ic_adjust_brightness, "Brightness"),
            new Function(R.drawable.ic_adjust_contrast, "Contrast"),
            new Function(R.drawable.ic_adjust_hue, "HUE")
        );
    }
}
