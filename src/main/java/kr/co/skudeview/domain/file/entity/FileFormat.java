package kr.co.skudeview.domain.file.entity;


import lombok.Getter;

@Getter
public class FileFormat {

    private String fileName;
    private String fileExtension;
    private String fileUrl;

    public FileFormat(File file) {
        this.fileName = file.getFileName();
        this.fileExtension = file.getFileExtension();
        this.fileUrl = file.getFileUrl();
    }
}
