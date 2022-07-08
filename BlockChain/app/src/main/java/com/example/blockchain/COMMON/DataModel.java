package com.example.blockchain.COMMON;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable{
    protected DataModel(Parcel in) {
        blood = in.readString();
        count = in.readString();
        choles = in.readString();
        image = in.readString();
        link = in.readString();
        phone = in.readString();
        addres = in.readString();
        email = in.readString();
        pid = in.readString();
        key = in.readString();
        age = in.readString();
        patientname = in.readString();
        name = in.readString();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCholes() {
        return choles;
    }

    public void setCholes(String choles) {
        this.choles = choles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    private String blood;
    private String count;
    private String choles;
    private String image;
    private String link;
    private String phone;
    private String addres;
    private String email;
    private String pid;
    private String key;
    private String age;
    private String patientname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(blood);
        parcel.writeString(count);
        parcel.writeString(choles);
        parcel.writeString(image);
        parcel.writeString(link);
        parcel.writeString(phone);
        parcel.writeString(addres);
        parcel.writeString(email);
        parcel.writeString(pid);
        parcel.writeString(key);
        parcel.writeString(age);
        parcel.writeString(patientname);
        parcel.writeString(name);
    }
}
