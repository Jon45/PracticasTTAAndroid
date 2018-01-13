package eus.ehu.tta.ttaejemplo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN = "login";
    private BusinessInterface business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.business = new Business();
        Intent intent = getIntent();
        TextView welcomeText = findViewById(R.id.welcomeUserTextView);
        welcomeText.setText(welcomeText.getText() + intent.getStringExtra(EXTRA_LOGIN));
    }

    public void newTest(View view)
    {
        Intent intent = new Intent(this,newTestActivity.class);
        startActivity(intent);
    }

    public void newExercise(View view)
    {
        Intent intent = new Intent(this,newExerciseActivity.class);
        startActivity(intent);
    }
}
