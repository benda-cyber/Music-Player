package com.example.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String songName;
    private String songLink;
    private String imgLink;
//    private String bigImgLink;

    public Song(String songName, String songLink, String imgLink){
        this.songName = songName;
        this.songLink = songLink;
        this.imgLink = imgLink;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    protected Song(Parcel in) {
        songName = in.readString();
        songLink = in.readString();
        imgLink = in.readString();
//        bigImgLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(songLink);
        dest.writeString(imgLink);
//        dest.writeString(bigImgLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
