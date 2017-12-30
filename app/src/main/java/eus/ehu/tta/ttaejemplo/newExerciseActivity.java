package eus.ehu.tta.ttaejemplo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class newExerciseActivity extends AppCompatActivity {

    BusinessInterface business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        business = new Business();

        TextView exerciseText = findViewById(R.id.exerciseText);
        exerciseText.setText(business.getNewExercise());
    }

    public void uploadFile(View view) {
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

    public void takePhoto(View view) {
        String text;
        if (business.takePhoto())
        {
            text=getResources().getString(R.string.takePhotoSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.takePhotoFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void recordAudio(View view) {
        String text;
        if (business.recordAudio())
        {
            text=getResources().getString(R.string.recordAudioSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.recordAudioFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void recordVideo(View view) {
        String text;
        if (business.recordVideo())
        {
            text=getResources().getString(R.string.recordVideoSuccess);;
        }
        else
        {
            text=getResources().getString(R.string.recordVideoFail);
        }
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}
