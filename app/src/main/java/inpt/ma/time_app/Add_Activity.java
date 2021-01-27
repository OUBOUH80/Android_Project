package inpt.ma.time_app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Activity extends AppCompatActivity {

    //constante
    //cam
    private static final int RETOUR_PRENDRE_PHOTO =1;
    private static final int MY_PERMISSIONS_CAMERA =0 ;
    //gallery
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE =1001;

    //proprietes
    //cam
    private ImageView cam;
    private ImageView imgAffichePhoto;
    private String photoPath = null;
    //gallery
    ImageView gallery;
    ImageView mchooseBtn;

    //Request for camera permission
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);
        initActivity();
        //views
        gallery = findViewById(R.id.image_view);
        mchooseBtn = findViewById(R.id.choose_image_btn);

        //handle boutton click
        mchooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtimr permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        //permission not granted ,request it.
                        String[] permissions= {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtimepermission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        PickImageFromGallery();
                    }
                }
                else {
                    //system os is less then marshmallow
                    PickImageFromGallery();
                }
            }
        });
    }
    private void  PickImageFromGallery(){
        //intent to pick image
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void imgClick(View v){
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
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