package gts.medical.gtsmedicalcompany.model;

public class PostModel {

    String banner_profile;
    String content_xrays;
    String xrayProfilePhoto;
    String xray_numbertraph;
    String xray_Uid;
    String xray_email;
    String xray_phone;
    String xrays_Date;
    String xrays_name;

    public PostModel() {
    }

    public PostModel(String banner_profile, String content_xrays, String xrayProfilePhoto, String xray_numbertraph, String xray_Uid, String xray_email, String xray_phone, String xrays_Date, String xrays_name) {
        this.banner_profile = banner_profile;
        this.content_xrays = content_xrays;
        this.xrayProfilePhoto = xrayProfilePhoto;
        this.xray_numbertraph = xray_numbertraph;
        this.xray_Uid = xray_Uid;
        this.xray_email = xray_email;
        this.xray_phone = xray_phone;
        this.xrays_Date = xrays_Date;
        this.xrays_name = xrays_name;
    }

    public String getBanner_profile() {
        return banner_profile;
    }

    public void setBanner_profile(String banner_profile) {
        this.banner_profile = banner_profile;
    }

    public String getContent_xrays() {
        return content_xrays;
    }

    public void setContent_xrays(String content_xrays) {
        this.content_xrays = content_xrays;
    }

    public String getXrayProfilePhoto() {
        return xrayProfilePhoto;
    }

    public void setXrayProfilePhoto(String xrayProfilePhoto) {
        this.xrayProfilePhoto = xrayProfilePhoto;
    }

    public String getXray_numbertraph() {
        return xray_numbertraph;
    }

    public void setXray_numbertraph(String xray_numbertraph) {
        this.xray_numbertraph = xray_numbertraph;
    }

    public String getXray_Uid() {
        return xray_Uid;
    }

    public void setXray_Uid(String xray_Uid) {
        this.xray_Uid = xray_Uid;
    }

    public String getXray_email() {
        return xray_email;
    }

    public void setXray_email(String xray_email) {
        this.xray_email = xray_email;
    }

    public String getXray_phone() {
        return xray_phone;
    }

    public void setXray_phone(String xray_phone) {
        this.xray_phone = xray_phone;
    }

    public String getXrays_Date() {
        return xrays_Date;
    }

    public void setXrays_Date(String xrays_Date) {
        this.xrays_Date = xrays_Date;
    }

    public String getXrays_name() {
        return xrays_name;
    }

    public void setXrays_name(String xrays_name) {
        this.xrays_name = xrays_name;
    }
}
