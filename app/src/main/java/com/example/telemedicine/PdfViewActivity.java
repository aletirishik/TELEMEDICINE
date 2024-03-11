package com.example.telemedicine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PdfViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Check if the Intent contains data
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Get the data (URI) from the Intent
            Uri pdfUri = intent.getData();

            // Now you have the URI, and you can use it as needed
            // For example, you might want to display the PDF using an external PDF viewer app
            // or you can use a library like PDFView to display it within your app
            openExternalPdfViewer(pdfUri);
        }
    }

    private void openExternalPdfViewer(Uri pdfUri) {
        // Implement the code to open the PDF using an external PDF viewer app
        // For example, create an Intent with ACTION_VIEW and set the data to the PDF URI
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(pdfUri, "application/pdf");
        viewIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the activity
        startActivity(viewIntent);
    }
}
