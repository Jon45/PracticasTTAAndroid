package eus.ehu.tta.ttaejemplo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class newExerciseActivity extends AppCompatActivity {

    private static final java.lang.String EXERCISE = "exercise";
    BusinessInterface business;
    String exercise;

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
        String text;
        if (business.uploadFile())
        {
            text=getResources().getString(R.string.uploadSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.uploadFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void onTakePhotoClick(View view) {
        String text;
        if (takePhoto())
        {
            text=getResources().getString(R.string.takePhotoSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.takePhotoFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
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
    
    public boolean takePhoto() {
        return true;
    }

    public boolean recordAudio() {
        return true;
    }

    public boolean recordVideo() {
        return true;
    }
}
