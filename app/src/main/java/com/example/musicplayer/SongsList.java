package com.example.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongsList implements Parcelable {

    public String[] nameArray = {"One More Cup Of Coffee", "Sara", "The Man In Me"};
    public String[] songLinkArray = {"https://www.syntax.org.il/xtra/bob.m4a", "https://www.syntax.org.il/xtra/bob1.m4a", "https://www.syntax.org.il/xtra/bob2.mp3"};
    public String[] imgLinkArray = {"https://img.discogs.com/zjRP8Xyhtj_QBVmzYyc5I9EQ2Pc=/fit-in/521x523/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-4882204-1378373149-1399.jpeg.jpg", "https://cdn11.bigcommerce.com/s-n6h3dlxzq9/images/stencil/1200x1200/products/37216/379651/SLPTDVNYL1138__55993.1610646691.jpg?c=2", "https://f4.bcbits.com/img/a4152887838_10.jpg"};
    public ArrayList<Song> songs = new ArrayList<Song>();

    protected SongsList(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SongsList> CREATOR = new Creator<SongsList>() {
        @Override
        public SongsList createFromParcel(Parcel in) {
            return new SongsList(in);
        }

        @Override
        public SongsList[] newArray(int size) {
            return new SongsList[size];
        }
    };

    public void addSong(String songName, String songLink, String songImgLink) {
        Song songToAdd = new Song(songName, songLink, songImgLink);
        this.songs.add(songToAdd);
    }

    public void addSong(Song songToAdd) {
        this.songs.add(songToAdd);
    }
    public void removeSong(Song songToRemove){
        this.songs.remove(songToRemove);
    }
}