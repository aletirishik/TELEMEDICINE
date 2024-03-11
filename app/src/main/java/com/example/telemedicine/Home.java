package com.example.telemedicine;

import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import android.app.ProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("deprecation")
public class Home extends AppCompatActivity {

    private TextView tvPdfTag;
    private Button btnOpenPdf;

    private  TextView  qrres;
    private ProgressDialog progressDialog;


    private StorageReference storageReference;
    Button btn_scan;
    Button btn_upload;
    private Uri pdfUri;
    private static final int PICK_PDF_REQUEST = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//      Intent intent = getIntent();
//        String action = intent.getAction();
//        String type = intent.getType();
//
//        if (action != null && type != null && action.equals(Intent.ACTION_SEND) && type.equals("application/pdf")) {
//            Uri pdfUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
//
//            if (pdfUri != null) {
//                // Handle the shared PDF file URI
//                displayPdf(pdfUri);
//            } else {
//                Toast.makeText(this, "No PDF file shared", Toast.LENGTH_SHORT).show();
//            }
//        }
        tvPdfTag = findViewById(R.id.tvPdfTag);
        btnOpenPdf = findViewById(R.id.btnOpenPdf);
        btn_scan=findViewById(R.id.btnScan);
        qrres=findViewById(R.id.qrres);
        btn_upload=findViewById(R.id.upload);
        btn_scan.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator=new IntentIntegrator(Home.this);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setPrompt("Scan a QR code");
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.initiateScan();
        });
        btn_upload.setOnClickListener(v -> upload_to_firebase());
                // Replace these with your connection details










        // Set the PDF tag, you may replace this with the actual PDF tag logic
    //    tvPdfTag.setText("Your PDF Tag");
        Intent intent = getIntent();
        if (intent != null && Intent.ACTION_SEND.equals(intent.getAction()) && "application/pdf".equals(intent.getType())) {
            Uri pdfUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            String pdfName = PdfUtil.getPdfNameFromUri(this,pdfUri); // Implement PdfUtil.getPdfNameFromUri method
            tvPdfTag.setText(pdfName);
            Toast.makeText(getApplicationContext(),pdfUri.toString(),Toast.LENGTH_LONG).show();
        }
        btnOpenPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf();  // Call the method to open the PDF
            }
        });

    }

    public void upload_to_firebase() {

//        Toast.makeText(this,"Upload Started",Toast.LENGTH_SHORT).show();
//
//        Date date=new Date();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
//        String time=simpleDateFormat.format(date);
//        String id=qrres.getText().toString()+time;
//        //Upload to Storage
//        FirebaseStorage storage=FirebaseStorage.getInstance();
//        FirebaseFirestore db=FirebaseFirestore.getInstance();
//        StorageReference storageReference=storage.getReference();
//        StorageReference filepath=storageReference.child(id+"."+"pdf");
//        filepath.putFile(getPdfUri())
//        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                final String[] download_uri = {""};
//                    Toast.makeText(getApplicationContext(),"Complete upload"+filepath.getDownloadUrl().toString(),Toast.LENGTH_SHORT).show();
//                    filepath.getDownloadUrl().
//                        addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                               download_uri[0] = String.valueOf(uri);
//                            }
//                        })
//                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                db.collection("ids").document(qrres.getText().toString()).collection("documents").add(new Upload_class(download_uri[0].toString(),time));
//                                Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_LONG).show();
//                            }
//                        });

//                }
//        });  pac
        
        
        try {


            Toast.makeText(this, "Upload Started", Toast.LENGTH_SHORT).show();

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String time = simpleDateFormat.format(date);
            String id = qrres.getText().toString() + time;

// Upload to Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference filepath = storageReference.child(id + "." + "pdf");

            filepath.putFile(getPdfUri())
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                // Retrieve download URL directly from the completed task
                                Toast.makeText(Home.this, task.getResult().getUploadSessionUri().toString(), Toast.LENGTH_SHORT).show();
                                String downloadUri = task.getResult().getUploadSessionUri().toString();
                                if (downloadUri != "null") {





                                    PatientInfo patientInfo = new PatientInfo();
                                    patientInfo.setPatientId(qrres.getText().toString());
                                    patientInfo.setDownloadUrl(downloadUri); // Assuming downloadUri is the PDF download URL
                                    patientInfo.setDateTime(time); // Assuming currentDateTime is the date and time

                                    Map<String, Object> pinfo = new HashMap<>();
                                    pinfo.put("url", downloadUri);
                                    pinfo.put("time", time);
                                    Toast.makeText(Home.this, qrres.getText().toString(), Toast.LENGTH_SHORT).show();
                                    db.collection("cities").document(qrres.getText().toString())
                                            .set(pinfo)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });

// You can set other properties of PatientInfo as needed
                                    db.collection("ids")
                                            .document(qrres.getText().toString()).collection("documents")
                                            .add(pinfo);

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Download URL is null", Toast.LENGTH_LONG).show();
                                            }

//                                task.getResult().getUploadSessionUri().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> urlTask) {
//                                        if (urlTask.isSuccessful()) {
//                                            Uri downloadUri = urlTask.getResult();
//                                            if (downloadUri != null) {
//                                                String downloadUrl = downloadUri.toString();
//                                                db.collection("ids").document(qrres.getText().toString())
//                                                        .collection("documents")
//                                                        .add(new Upload_class(downloadUrl, time))
//                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                                if (task.isSuccessful()) {
//                                                                    Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_LONG).show();
//                                                                } else {
//                                                                    Toast.makeText(getApplicationContext(), "Error uploading to Firestore", Toast.LENGTH_LONG).show();
//                                                                }
//                                                            }
//                                                        });
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), "Download URL is null", Toast.LENGTH_LONG).show();
//                                            }
//                                        } else {
//                                            Toast.makeText(getApplicationContext(), "Error retrieving download URL", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Error uploading to Storage", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch ( Exception  e){
  e.printStackTrace();
        }


    }




    private void displayPdf(Uri pdfUri) {
        tvPdfTag.setText((CharSequence) pdfUri);
    }



    private Uri getPdfUri() {

        // Replace this with your logic to obtain the PDF Uri
        // For example, you might use Intent to receive PDF file from another app
        Intent intent = getIntent();
        if (intent != null && Intent.ACTION_SEND.equals(intent.getAction()) && "application/pdf".equals(intent.getType())) {
            return intent.getParcelableExtra(Intent.EXTRA_STREAM);
        } else {
            // Provide a default value or handle the case when Uri is not available
            return Uri.parse("content://default/pdf");
        }
    }
    private void openPdf() {
        // Replace this Uri with the actual Uri pointing to your PDF file
        Uri pdfUri = getPdfUri();

        // Create an Intent for viewing the PDF
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(pdfUri, "application/pdf");
        viewIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the external PDF viewer
        try {
            startActivity(viewIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("PDFViewer", "No app to handle PDF");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null) {
//            Uri selectedPdfUri = data.getData();
//
//            boolean uploadStatus = PdfUploader.uploadPdfToMongoDB(this, "123456", selectedPdfUri);
//
//            if (uploadStatus) {
//                // Display a success Toaste
//                Toast.makeText(this, "PDF uploaded successfully!", Toast.LENGTH_SHORT).show();
//            } else {
//                // Display an error Toast
//                Toast.makeText(this, "Failed to upload PDF", Toast.LENGTH_SHORT).show();
//            }
//        }
//
        IntentResult intentResult;
        intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){
            String contents = intentResult.getContents();
            if(contents!=null){
                qrres.setText(intentResult.getContents());
            }
        }else {
        super.onActivityResult(requestCode,resultCode,data);
    }





    // Construct the connection string

//
//     Create a MongoClient instance
//    class Mymongo {
////       // String databaseHost = "localhost";
////      //  int databasePort = 27017; // Default MongoDB port
//private static final String TAG = "MainActivity";
//   private static final String  connectionString = "mongodb+srv://0000:<password>@cluster0.idxidv0.mongodb.net/?retryWrites=true&w=majority&appName=cluster0";
//       public  MongoClient connect(){
//           Log.v(TAG,"Done");
//           return  MongoClients.create(connectionString);
//
//       }
//
//
//    }

 }}