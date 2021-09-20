package gts.medical.gtsmedicalcompany.model;

public class ConfirmedRequests {

    String IdUser;
    String Postid;
    String confirmId;
    String xrayName;
    String xraycontent;

    public ConfirmedRequests() {
    }

    public ConfirmedRequests(String idUser, String postid, String confirmId, String xrayName, String xraycontent) {
        IdUser = idUser;
        Postid = postid;
        this.confirmId = confirmId;
        this.xrayName = xrayName;
        this.xraycontent = xraycontent;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getPostid() {
        return Postid;
    }

    public void setPostid(String postid) {
        Postid = postid;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public String getXrayName() {
        return xrayName;
    }

    public void setXrayName(String xrayName) {
        this.xrayName = xrayName;
    }

    public String getXraycontent() {
        return xraycontent;
    }

    public void setXraycontent(String xraycontent) {
        this.xraycontent = xraycontent;
    }
}
