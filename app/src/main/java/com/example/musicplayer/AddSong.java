package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSong extends AppCompatActivity {

    Button addSongBtn;
    EditText songName;
    EditText songUrl;
    EditText songImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        songName = findViewById(R.id.song_name);
        songUrl = findViewById(R.id.song_lnk);
        songImgUrl = findViewById(R.id.song_img_lnk);
        addSongBtn = findViewById(R.id.add_song);

    }


    public void addSong(View view){
        String nameStr = songName.getText().toString();
        String songUrlStr = songUrl.getText().toString();
        String songImgUrlStr = songImgUrl.getText().toString();
        Song songToAdd = new Song(nameStr, songUrlStr,songImgUrlStr);
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("song_to_add",songToAdd);
        startActivity(intent);
    }
}