package com.example.demo;

public class Contact {
    String Fname,Email,MobileNo,ProfileImage;

    public Contact() {
    }

    public Contact(String fname, String email, String mobileNo, String profileImage) {
        this.Fname = fname;
        this.Email = email;
        this.MobileNo = mobileNo;
        this.ProfileImage = profileImage;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }
}
