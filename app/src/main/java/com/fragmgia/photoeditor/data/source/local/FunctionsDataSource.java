package com.fragmgia.photoeditor.data.source.local;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class FunctionsDataSource {
    public static List<Function> getFunctions() {
        return Arrays.asList(
            new Function(R.drawable.ic_effect, "Effect"),
            new Function(R.drawable.ic_crop, "Crop"),
            new Function(R.drawable.ic_color, "Color"),
            new Function(R.drawable.ic_adjust, "Adjust"));
    }
}
