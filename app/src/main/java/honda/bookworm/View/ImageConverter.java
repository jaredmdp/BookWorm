package honda.bookworm.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

//With reference to https://stackoverflow.com/questions/14153641/how-to-convert-bitmap-to-byte-array
//for conversions between bitmaps and byte[]
public class ImageConverter {
    public static Bitmap BytesToBitmap(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static byte[] BitmapToBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
