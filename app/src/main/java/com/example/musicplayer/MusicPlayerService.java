package com.example.musicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private MediaPlayer player = new MediaPlayer();
    ArrayList<String> songs;
    NotificationManager manager;
    final int NOTIF_ID = 1;
    int currentPlaying = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
        player.reset();

        String channelId = null;
        channelId = "channel_id";
        String channelName = "Music Channel";
        if(Build.VERSION.SDK_INT >= 26){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,importance);
            manager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.music_notif);

        Intent playIntent = new Intent(this,MusicPlayerService.class);
        playIntent.putExtra("command","play");
        PendingIntent playPendingIntent = PendingIntent.getService(this,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_btn,playPendingIntent);

        Intent pauseIntent = new Intent(this,MusicPlayerService.class);
        pauseIntent.putExtra("command","pause");
        PendingIntent pausePendingIntent = PendingIntent.getService(this,1,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pause_btn,pausePendingIntent);

        Intent nextIntent = new Intent(this,MusicPlayerService.class);
        nextIntent.putExtra("command","next");
        PendingIntent nextPendingIntent = PendingIntent.getService(this,2,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_btn,nextPendingIntent);

        Intent prevIntent = new Intent(this,MusicPlayerService.class);
        prevIntent.putExtra("command","prev");
        PendingIntent prevPendingIntent = PendingIntent.getService(this,3,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.prev_btn,prevPendingIntent);

        Intent closeIntent = new Intent(this,MusicPlayerService.class);
        closeIntent.putExtra("command","close");
        PendingIntent closePendingIntent = PendingIntent.getService(this,4,closeIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.close_btn,closePendingIntent);


        builder.setCustomContentView(remoteViews);

        builder.setSmallIcon(android.R.drawable.ic_media_play);

        startForeground(NOTIF_ID,builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String command = intent.getStringExtra("command");

        switch (command){
            case "new_instance":
                songs = intent.getParcelableExtra("list");
                try {
                    player.setDataSource(songs.get(currentPlaying));
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "play":
                if(!player.isPlaying())
                    player.start();
            case "next":
                if(player.isPlaying())
                    player.stop();
                playSong(true);
                break;
            case "prev":
                if(player.isPlaying())
                    player.stop();
                playSong(false);
                break;
            case "pause":
                if(player.isPlaying())
                    player.pause();
            case "close":
                stopSelf();

        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void playSong(boolean isNext)  {
        if(isNext) {
            currentPlaying++;
            if(currentPlaying == songs.size())
                currentPlaying = 0;
        }
        else{
            currentPlaying--;
            if(currentPlaying<0)
                currentPlaying = songs.size() - 1;
        }
        player.reset();
        try {
            player.setDataSource(songs.get(currentPlaying));
            player.prepareAsync();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null){
            if(player.isPlaying())
                player.stop();
            player.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playSong(true);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        player.start();

    }
}
