package honda.bookworm.View.Extra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

//With reference to https://stackoverflow.com/questions/14153641/how-to-convert-bitmap-to-byte-array
//for conversions between bitmaps and byte[]
public class ImageConverter {
    public static Bitmap DecodeToBitmap(String encoded)
    {
        byte[] decodedBytes = Base64.decode(encoded, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String EncodeToBase64(Bitmap bitmap)
    {
        String res = "";

        if(bitmap != null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            res = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        }

        return res;
    }
}
