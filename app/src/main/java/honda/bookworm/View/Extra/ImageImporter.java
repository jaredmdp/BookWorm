package honda.bookworm.View.Extra;

import android.app.Activity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImageImporter{
    private static final int REQUEST_CODE = 1;
    private static final int PICK_IMAGE = 1;
    private static final int MAX_FILE_SIZE = 5 * (1024 * 1024) ; // 5MB


    public static void importImage(Activity parentActivity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");


        parentActivity.startActivityForResult(intent, REQUEST_CODE);
    }


    public static void handleActivityResult(int requestCode, int resultCode, Intent data, ImageImportCallback callback) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (callback != null) {
                callback.onImageImported(selectedImageUri);
            }
        }
    }

    public static boolean isValidImageSize(Context c, Bitmap b ){
        int size = b.getByteCount();
        boolean validSize = size<MAX_FILE_SIZE;

        if(!validSize) {
            Toast.makeText(c, "Image Size must not exceed: 5MB", Toast.LENGTH_LONG).show();
        }

        return validSize;
    }



    public interface ImageImportCallback {
        void onImageImported(Uri imageUri);
    }


}
