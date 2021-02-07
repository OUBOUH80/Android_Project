package com.example.time_app_v11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;

public class MesActivites extends AppCompatActivity implements AudioListAdapter.onItemListClick  {

    private RecyclerView audio_listV;
    private ImageView list_imageV;
    private SeekBar playerSeekBar;
    private Handler seekbarHandler;
    private Runnable updateSeekbar;
    private File[] allfiles;
    private AudioListAdapter audioListAdapter;
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private File fileToPlay;
    private File fileToStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_list);
        audio_listV= (RecyclerView) findViewById(R.id.audio_list_view);
        String path=getApplicationContext().getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(path);
        allfiles = directory.listFiles();
        audioListAdapter = new AudioListAdapter(allfiles,this);
        audio_listV.setHasFixedSize(true);
        audio_listV.setLayoutManager(new LinearLayoutManager(this));
        audio_listV.setAdapter(audioListAdapter);
    }


    @Override
    public void onClickListner(File file, int position) {

    }
}