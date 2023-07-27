package honda.bookworm.View.Extra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;

import honda.bookworm.Business.Exceptions.GeneralPersistenceException;

public class ImageImporter{
    private static final int REQUEST_CODE = 1;
    private static final int PICK_IMAGE = 1;

    private static final int MB = 2;
    private static final int MAX_FILE_SIZE = MB * (1024 * 1024) ;


    public static void importImage(Activity parentActivity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");


        parentActivity.startActivityForResult(intent, REQUEST_CODE);
    }


    public static void handleActivityResult(int requestCode, int resultCode, Intent data, ImageImportCallback callback, Context context) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (callback != null) {


                try{
                    callback.onImageImported(selectedImageUri);
                } catch(IOException e)
                {
                    Toast.makeText(context, "Failed to import image: ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public static boolean isValidImageSize(Context c, Bitmap b ){
        int size = b.getByteCount();
        boolean validSize = size<MAX_FILE_SIZE;

        if(!validSize) {
            Toast.makeText(c, "Image too large, try a smaller image", Toast.LENGTH_LONG).show();
        }

        return validSize;
    }



    public interface ImageImportCallback {
        void onImageImported(Uri imageUri) throws IOException;
    }


}
