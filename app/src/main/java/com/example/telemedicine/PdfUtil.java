package com.example.telemedicine;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

public class PdfUtil {

   
    public static String getPdfNameFromUri(Context context,Uri pdfUri) {
       
            String pdfName = "";


        Uri uri = null;
        if (context == null || uri == null) {
                return pdfName; // Return an empty string or handle the error as needed
            }

            Cursor cursor = null;

            try {
                // Use the ContentResolver to query the file name from the Uri
                cursor = context.getContentResolver().query(uri, null, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    // Retrieve the file name column index
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

                    // Get the file name from the cursor
                    pdfName = cursor.getString(nameIndex);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            return pdfName;
        }
    }

    // Add other utility methods as needed

