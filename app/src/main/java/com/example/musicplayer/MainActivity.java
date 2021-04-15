package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardCustomAdapter.OnCardListener {

    boolean isPlaying = false;
    Button playBtn;
    Button addSongBtn;
    Song songToAdd;
    private ArrayList<Song> songsArrayList;
    private RecyclerView songsRV;
    private CardCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSongBtn = findViewById(R.id.add_song_btn);
        playBtn = findViewById(R.id.play_btn);
        songsRV = findViewById(R.id.songs_recycler_view);
        Intent intent = new Intent();
        intent.putExtra("list",songsArrayList);
        intent.putExtra("command","new_instance");
        loadData();
        if(getIntent().getParcelableExtra("song_to_add")!=null){
            songToAdd = getIntent().getParcelableExtra("song_to_add");
            saveData(songToAdd);
        }

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlaying){
                    playBtn.setText("Play");
                    stopMusic();
                }
                else{
                    playBtn.setText("Stop");
                    playMusic();
                }
                isPlaying = !isPlaying;
            }
        });

        adapter = new CardCustomAdapter(songsArrayList,  this);
        songsRV.setAdapter(adapter);
    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("songs", null);

        Type type = new TypeToken<ArrayList<Song>>() {}.getType();

        songsArrayList = gson.fromJson(json, type);

        if (songsArrayList == null) {

            songsArrayList = new ArrayList<>();
        }
    }

    private void saveData(Song song) {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(songsArrayList);

        editor.putString("songs", json);

        editor.apply();

        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    private void playMusic(){
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.putExtra("list", songsArrayList);
        startService(intent);

    }

    private void stopMusic(){
        Intent intent = new Intent(this, MusicPlayerService.class);
        stopService(intent);
    }


    public void goToAddSong(View view) {

        Button toAddSongBtn = (Button) view;
        Intent intent = new Intent(this, AddSong.class);
        startActivity(intent);

    }

    @Override
    public void onCardClick(int position) {

    }
}