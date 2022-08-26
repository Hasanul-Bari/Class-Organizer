package com.example.gridlayout;

public class FileItem {

    private String fileName,fileUrl,fileUploader,uploadDate;


    public FileItem(String fileName, String fileUrl, String fileUploader, String uploadDate) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileUploader = fileUploader;
        this.uploadDate = uploadDate;
    }

    public FileItem(){

    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUploader() {
        return fileUploader;
    }
    public void setFileUploader(String fileUploader) {
        this.fileUploader = fileUploader;
    }

    public String getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
