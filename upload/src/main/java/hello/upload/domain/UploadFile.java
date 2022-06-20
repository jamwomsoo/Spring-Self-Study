package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName; // 고객이 업로드한 파일
    private String storeFileName;  // (시스템에)서버 내부에서 저장한 파일

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
