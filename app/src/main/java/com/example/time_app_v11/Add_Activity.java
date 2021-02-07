package com.example.time_app_v11;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Add_Activity extends AppCompatActivity {

    // tkhrbi9_1 d record
    private LinearLayout layout_micro;
    private ImageView micro;


    /// tkhrbi9_2 d Record

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private MediaRecorder recorder = null;

    private MediaPlayer player = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private  String recordPermission=  Manifest.permission.RECORD_AUDIO;


    // Record
    private boolean isRecording = false;
    private String recordFile;
    private MediaRecorder mediaRecorder;
    private  int PERMISSION_CODE1= 21;
    private Chronometer chrono;
    private TextView Annuler;

    //constante
    //cam
    private static final int RETOUR_PRENDRE_PHOTO = 1;
    private static final int MY_PERMISSIONS_CAMERA = 0;
    //gallery
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    //proprietes
    //cam
    private ImageView cam;
    private ImageView imgAffichePhoto;
    private String photoPath = null;
    //gallery
    ImageView gallery;
    ImageView mchooseBtn;

    ////////////////////////////////Record/////////////////////////////////////////////////////
/*
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    boolean mStartRecording = true;

    public void ClickRecord(View v) {
        ///////////// ppermission d record ///////
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";




        if(checkPermissions()){
        if (mStartRecording) {


            Toast.makeText(this," Start Recording",Toast.LENGTH_SHORT).show();
            onRecord(mStartRecording);
        } else {

                Toast.makeText(this," End Recording",Toast.LENGTH_SHORT).show();
                layout_micro= (LinearLayout) findViewById(R.id.layout_mic);
                layout_micro.setBackgroundDrawable(getResources().getDrawable(R.color.white));
                micro=(ImageButton) findViewById(R.id.play);
                micro.setImageResource(R.drawable.play);

        }}

        mStartRecording = !mStartRecording;
    }
*/

/////////////////////////////////////Playing Record ////////////////////////////////////////////////////////



/*

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }


    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }


    boolean mStartPlaying=true;


    public void ClickPlay(View v){


        onPlay(mStartPlaying);
        if (mStartPlaying) {
            Toast.makeText(this," Start Playing",Toast.LENGTH_SHORT).show();
            micro.setImageResource(R.drawable.high_volume);
        } else {
            Toast.makeText(this," End Playing",Toast.LENGTH_SHORT).show();
            micro.setImageResource(R.drawable.play);
        }
        mStartPlaying = !mStartPlaying;
    }
    private boolean checkPermissions(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),recordPermission)== PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(this, new String[]{recordPermission},PERMISSION_CODE);
            return false;
        }
    }


*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ClickRecord(View v) {
        if(isRecording){
            //stop recording
            stopRecording();
            Toast.makeText(getApplicationContext(),"recording is stoped",Toast.LENGTH_SHORT).show();
            isRecording= false;
        }else{
            if(checkPermissions()){
                startRecording();
                Toast.makeText(getApplicationContext(),"recording is started",Toast.LENGTH_SHORT).show();
                isRecording= true;
            }
        } }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startRecording() {
        // bach mayb9ach timer khdam f l background fach kankono hbsna record kanzido hadi
        layout_micro= (LinearLayout) findViewById(R.id.layout_mic);
        layout_micro.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded));
        Annuler.setTextColor(R.color.blue9);
        chrono.setTextColor(R.color.black);
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();

        String recordPath= this.getExternalFilesDir("/").getAbsolutePath();
        android.icu.text.SimpleDateFormat formatter= new android.icu.text.SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.FRANCE);
        Date now = new Date();
        recordFile="Record"+formatter.format(now)+".3gp";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath+"/"+recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void stopRecording() {
        chrono.stop();
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;

    }

    private boolean checkPermissions(){
        int a=1;
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),recordPermission)== PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(this, new String[]{recordPermission},PERMISSION_CODE1);
            return false;

        }
    }












    ////////////////////////////////////////////Dial Khaoula   ET Rajae///////////////////////////////////////////////


    //Request for camera permission
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);
        initActivity();
        //Record to the external cache directory for visibility

        //views
        gallery = findViewById(R.id.image_view);
        mchooseBtn = findViewById(R.id.choose_image_btn);
        chrono= (Chronometer)findViewById(R.id.record_timer);
        Annuler =(TextView) findViewById(R.id.annuler);

        //handle boutton click
        mchooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtimr permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted ,request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtimepermission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        PickImageFromGallery();
                    }
                } else {
                    //system os is less then marshmallow
                    PickImageFromGallery();
                }
            }
        });
    }

    private void PickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }




    /**
     * initialisation de l'activity
     */
    private void initActivity(){
        //recuperation des objets graphiques
        cam =(ImageView)findViewById(R.id.cam);
        imgAffichePhoto =(ImageView) findViewById(R.id.imgAffichePhoto);
        //methode pour gerer les evenements
        createOnClickBtnPrendrePhoto();
    }

    /**
     *evenement click sur bouton PrendrePhoto
     */
    private void createOnClickBtnPrendrePhoto(){
        cam.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                prendreUnePhoto();
            }
        });
    }

    /**
     * acces a l appareil photo et memorise dans un fichier temporaire
     */
    private void prendreUnePhoto() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Open Camera ! :-)",
                    Toast.LENGTH_LONG).show();
            //cree une intent pour ouvrir une fenetre pour prendre une photo
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //test pour controle que l'intent peut etre géré
            if (intent.resolveActivity(getPackageManager()) != null) {
                //creer un nom de fichier unique
                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File photoFile = File.createTempFile("photo" + time, ".jpg", photoDir);
                    //enregistrer le chemin complet
                    photoPath = photoFile.getAbsolutePath();
                    //creer l'URI
                    Uri photoUri = FileProvider.getUriForFile(Add_Activity.this,
                            Add_Activity.this.getApplicationContext().getPackageName() + ".provider",
                            photoFile);
                    //transfert uri vers l'intent pour enregistrement photo dans fichier temporaire
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    //ouvrir l'activity par rapport a l'intent
                    startActivityForResult(intent, RETOUR_PRENDRE_PHOTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * retour de l appel de l appareil photo (startActivityForResult)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //handle result on picked image
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            gallery.setImageURI(data.getData());
        }
        //verifie ,le bon code de retour etl'etat du retour ok
        if(requestCode==RETOUR_PRENDRE_PHOTO && resultCode==RESULT_OK){
            //recuperer l'image
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            //afficher l image
            imgAffichePhoto.setImageBitmap(image);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "OPEN CAMERA ! :-)",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "DEMANDE REFUSE", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    PickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied :(",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}