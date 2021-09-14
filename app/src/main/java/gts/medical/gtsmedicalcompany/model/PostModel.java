package gts.medical.gtsmedicalcompany.model;

public class PostModel {

    String userName;
    String postDate;
    String postDesc;
    int userImage;
    int postImage;

    public PostModel(String userName, String postDate, String postDesc, int userImage, int postImage) {
        this.userName = userName;
        this.postDate = postDate;
        this.postDesc = postDesc;
        this.userImage = userImage;
        this.postImage = postImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }
}
