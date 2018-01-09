package eus.ehu.tta.ttaejemplo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class newExerciseActivity extends AppCompatActivity {

    private static final java.lang.String EXERCISE = "exercise";
    private static final int PICTURE_REQUEST_CODE = 0;
    private static final int READ_EXTERNAL_STORAGE_FOR_PHOTO = 0;
    BusinessInterface business;
    String exercise;
    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        business = new Business();

        TextView exerciseText = findViewById(R.id.exerciseText);
        if (savedInstanceState == null)
        {
            exercise = business.getNewExercise();
        }
        else
        {
            exercise = savedInstanceState.getString(EXERCISE);
        }
        exerciseText.setText(exercise);
    }

    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXERCISE,exercise);
    }

    public void onUploadFileClick(View view) {
        uploadFile();
    }

    private void uploadFile() {
    }

    public void onTakePhotoClick(View view) {
        takePhoto();
    }

    public void onRecordAudioClick(View view) {
        String text;
        if (recordAudio())
        {
            text=getResources().getString(R.string.recordAudioSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.recordAudioFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void onRecordVideoClick(View view) {
        String text;
        if (recordVideo())
        {
            text=getResources().getString(R.string.recordVideoSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.recordVideoFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_FOR_PHOTO);
        }
        else
        {
            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            {
                Toast.makeText(this,R.string.noCamera,Toast.LENGTH_SHORT).show();
            }

            else
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                {
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    try {
                        File file = File.createTempFile("tta",".jpg",dir);
                        pictureUri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                        startActivityForResult(intent,PICTURE_REQUEST_CODE);
                    } catch (IOException e ) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT).show();
                }
            }
        }
     }

    public boolean recordAudio() {
        return true;
    }

    public boolean recordVideo() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode)
        {
            case PICTURE_REQUEST_CODE:
                business.uploadFile(pictureUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_FOR_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    takePhoto();
                }
                break;
        }
    }
}
