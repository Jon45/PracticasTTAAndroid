package eus.ehu.tta.ttaejemplo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
    private static final int VIDEO_REQUEST_CODE = 1;
    private static final int AUDIO_REQUEST_CODE = 2;
    private static final int WRITE_EXTERNAL_STORAGE_FOR_PHOTO = 0;
    private static final int READ_EXTERNAL_STORAGE_FOR_VIDEO = 1;
    private static final int READ_EXTERNAL_STORAGE_FOR_AUDIO = 2;

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
        if (checkAndRequestPermissions(this,this,Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE_FOR_PHOTO))
        {
            takePhoto();
        }
    }

    private boolean checkAndRequestPermissions(Context context, Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
            return false;
        }
        return true;
    }

    public void onRecordAudioClick(View view) {
        if (checkAndRequestPermissions(this,this,Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE_FOR_AUDIO))
        {
            recordAudio();
        }
    }

    public void onRecordVideoClick(View view) {
        if (checkAndRequestPermissions(this,this,Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE_FOR_VIDEO))
        {
            recordVideo();
        }
    }

    public void takePhoto() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, R.string.noCamera, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                try {
                    File file = File.createTempFile("tta", ".jpg", dir);
                    pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".eus.ehu.tta.ttaejemplo.provider", file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordAudio() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            Toast.makeText(this, R.string.noMicrophone, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, AUDIO_REQUEST_CODE);
            } else {
                Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordVideo() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, R.string.noCamera, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (intent.resolveActivity(getPackageManager())!=null)
            {
                startActivityForResult(intent,VIDEO_REQUEST_CODE);
            }
            else
            {
                Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode)
        {
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                business.uploadFile(data.getData());
                break;
            case PICTURE_REQUEST_CODE:
                business.uploadFile(pictureUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_FOR_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    takePhoto();
                }
                break;
            case READ_EXTERNAL_STORAGE_FOR_VIDEO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    recordVideo();
                }
                break;
            case READ_EXTERNAL_STORAGE_FOR_AUDIO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    recordAudio();
                }
                break;
        }
    }
}
