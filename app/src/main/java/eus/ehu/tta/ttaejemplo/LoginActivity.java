package eus.ehu.tta.ttaejemplo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Business business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.business = new Business();
    }

    public void login(View view) {
        String login;
        String password;
        EditText loginEditText = (EditText) findViewById(R.id.loginEditText);
        login=loginEditText.getText().toString();
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        password=passwordEditText.getText().toString();

        if (business.login(login,password))
        {
            Intent intent = new Intent(this,MenuActivity.class);
            intent.putExtra(MenuActivity.EXTRA_LOGIN,login);
            startActivity(intent);
        }

        else
        {
            Toast.makeText(this,R.string.loginError, Toast.LENGTH_SHORT).show();
        }
    }
}
