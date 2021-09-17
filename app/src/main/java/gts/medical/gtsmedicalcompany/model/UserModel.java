package gts.medical.gtsmedicalcompany.model;

public class UserModel {

    String uid;
    String name ;
    String image;
    String phone;
    String address;
    String email;
    String nationalID;
    String password;
    String confirmPassword;

    public UserModel() {
    }

    public UserModel(String uid, String name, String image, String phone, String address, String email, String nationalID, String password, String confirmPassword) {
        this.uid = uid;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.nationalID = nationalID;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
