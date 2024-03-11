package com.example.telemedicine;

public class Pdfinfo {

    private String pdfUrl;
//    public PdfInfo() {
//        // Default constructor required for calls to DataSnapshot.getValue(PdfInfo.class)
//    }

    // Constructor with parameters
    public void PdfInfo(String pdfUrl) {

        this.pdfUrl=pdfUrl;
        //this.pdfName = pdfName;
        // Set pdfUrl to an initial value, or leave it null if it will be set later
    }



    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }


}
