package com.example.time_app_v11;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioViewHolder> {
    private  File[] allFiles;
    private onItemListClick onItemListClick;
    private boolean isPlaying = false;
    private File fileToPlay;
    private File fileToStop;
    private MediaPlayer mediaPlayer = null;

    private Handler seekbarHandler;
    private Runnable updateSeeekbar;


    SeekBar playerSeekBar;


    public AudioListAdapter(File[] allFiles, onItemListClick onItemListClick){
        this.allFiles=allFiles;
        this.onItemListClick=onItemListClick;

    }


    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item,parent,false);
        return new AudioViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {

        holder.title.setText(allFiles[position].getName());

    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView title;
        private  SeekBar playerSeekbar;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.list_image_view);
            title = itemView.findViewById(R.id.list_title);
            playerSeekbar= itemView.findViewById(R.id.seekBar);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListClick.onClickListner(allFiles[getAdapterPosition()],getAdapterPosition());


            fileToPlay=allFiles[getAdapterPosition()];
            if(isPlaying){
                stopAudio();


            }else{

                playAudio(fileToPlay);

            } }

            private void pauseAudio(){
                mediaPlayer.pause();
            }

            private void resumeAudio(){
            mediaPlayer.start();
            }


        private void stopAudio() {
            imageView.setImageResource(R.drawable.play);
            isPlaying=false;
            mediaPlayer.stop();
        }


        private void playAudio(File fileToPlay) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                imageView.setImageResource(R.drawable.high_volume);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPlaying =true;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopAudio();
                }
            });
           playerSeekbar.setMax(mediaPlayer.getDuration());
            seekbarHandler= new Handler();
            updateSeeekbar= new Runnable() {
                @Override
                public void run() {
                   playerSeekbar.setProgress(mediaPlayer.getCurrentPosition());
                   playerSeekbar.postDelayed(this,500);
                }
            };
            seekbarHandler.postDelayed(updateSeeekbar,0);




        }
    }

    public interface  onItemListClick{
        void onClickListner(File file,int position);

    }
}



