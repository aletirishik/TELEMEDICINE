package com.example.telemedicine;

public class PatientInfo {
    private String patientId;
    private String downloadUrl;
    private String dateTime; // You can store date and time as a string or separate fields
    // Add other properties as needed

    // Default constructor required for Firestore
    public PatientInfo() {
    }

    public PatientInfo(String patientId, String downloadUrl, String dateTime) {
        this.patientId = patientId;
        this.downloadUrl = downloadUrl;
        this.dateTime = dateTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
