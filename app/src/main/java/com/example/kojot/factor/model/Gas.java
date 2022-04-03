package com.example.kojot.factor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Gas implements Parcelable {

    private int id;
    private String name;
    private String M;
    private String cp;
    private String cv;
    private String pac1, pac2, pac3;

    public Gas() {
    }


    protected Gas(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.M = in.readString();
        this.cp = in.readString();
        this.cv = in.readString();
        this.pac1 = in.readString();
        this.pac2 = in.readString();
        this.pac3 = in.readString();
    }

    public static final Parcelable.Creator<Gas> CREATOR = new Parcelable.Creator<Gas>() {
        @Override
        public Gas createFromParcel(Parcel source) {
            return new Gas(source);
        }

        @Override
        public Gas[] newArray(int size) {
            return new Gas[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        this.M = m;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String Cp) {
        this.cp = Cp;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String Cv) {
        this.cv = Cv;
    }

    public String getPac1() {
        return pac1;
    }

    public void setPac1(String pac1) {
        this.pac1 = pac1;
    }

    public String getPac2() {
        return pac2;
    }

    public void setPac2(String pac2) {
        this.pac2 = pac2;
    }

    public String getPac3() {
        return pac3;
    }

    public void setPac3(String pac3) {
        this.pac3 = pac3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.M);
        dest.writeString(this.cp);
        dest.writeString(this.cv);
        dest.writeString(this.pac1);
        dest.writeString(this.pac2);
        dest.writeString(this.pac3);
    }

}