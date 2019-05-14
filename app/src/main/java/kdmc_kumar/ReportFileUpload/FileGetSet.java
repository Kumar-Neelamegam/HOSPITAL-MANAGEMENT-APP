package kdmc_kumar.ReportFileUpload;

/**
 * Created by Ponnusamy M on 4/21/2017.
 */

class FileGetSet {

    private String ImageName;
    private String ImagePath;
    private String ImageData;
    private String Id;
    private String UploadFileDetail;
    private String ReportType;

    public FileGetSet(String imageName, String imagePath, String imageData, String id, String uploadFileDetail, String reportType) {
        this.ImageName = imageName;
        this.ImagePath = imagePath;
        this.ImageData = imageData;
        this.Id = id;
        this.UploadFileDetail = uploadFileDetail;
        this.ReportType = reportType;
    }

    public FileGetSet(String imageName, String imagePath, String imageData, String id, String uploadFileDetail) {
        this.ImageName = imageName;
        this.ImagePath = imagePath;
        this.ImageData = imageData;
        this.Id = id;
        this.UploadFileDetail = uploadFileDetail;
    }

    public FileGetSet() {
    }

    public FileGetSet(String imageName, String imagePath, String imageData) {
        this.ImageName = imageName;
        this.ImagePath = imagePath;
        this.ImageData = imageData;
    }

    public final String getReportType() {
        return this.ReportType;
    }

    public final void setReportType(String reportType) {
        this.ReportType = reportType;
    }

    public final String getId() {
        return this.Id;
    }

    public final void setId(String id) {
        this.Id = id;
    }

    public final String getUploadFileDetail() {
        return this.UploadFileDetail;
    }

    public final void setUploadFileDetail(String uploadFileDetail) {
        this.UploadFileDetail = uploadFileDetail;
    }

    public final String getImageName() {
        return this.ImageName;
    }

    public final void setImageName(String imageName) {
        this.ImageName = imageName;
    }

    public final String getImagePath() {
        return this.ImagePath;
    }

    public final void setImagePath(String imagePath) {
        this.ImagePath = imagePath;
    }

    public final String getImageData() {
        return this.ImageData;
    }

    public final void setImageData(String imageData) {
        this.ImageData = imageData;
    }
}
