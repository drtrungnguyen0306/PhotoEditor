package com.fragmgia.photoeditor.data.source.local;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.fragmgia.photoeditor.data.model.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/23/17.
 */
public class ImagesDataSource {
    public static List<ImageInfo> getImagesSDCard(Activity activity) {
        ArrayList<ImageInfo> list = new ArrayList<>();
        int columnData, columnName;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.TITLE};
        Cursor cursor = activity.getContentResolver().query(uri, projection, null,
            null, null);
        if (cursor == null) return null;
        if (cursor.moveToFirst()) {
            columnData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            columnName = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE);
            while (!cursor.isAfterLast()) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setPath(cursor.getString(columnData));
                imageInfo.setName(cursor.getString(columnName));
                list.add(imageInfo);
                cursor.moveToNext();
            }
        }
        return list;
    }
}
