package eus.ehu.tta.ttaejemplo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private BusinessInterface business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.business = new BusinessReal(getResources().getString(R.string.baseURL));
    }

    public void login(View view) {
        String login;
        String password;
        EditText loginEditText = (EditText) findViewById(R.id.loginEditText);
        login=loginEditText.getText().toString();
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        password=passwordEditText.getText().toString();
        new LoginTask().execute(login,password);
    }

    private class LoginTask extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Button loginButton = findViewById(R.id.loginButton);
            loginButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return business.login(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.booleanValue())
            {
                Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                intent.putExtra(MenuActivity.EXTRA_LOGIN,UserData.getInstance().getUserName());
                startActivity(intent);
            }

            else
            {
                Toast.makeText(LoginActivity.this,R.string.loginError, Toast.LENGTH_SHORT).show();
            }
            Button loginButton = findViewById(R.id.loginButton);
            loginButton.setEnabled(true);
        }
    }
}
