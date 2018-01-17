package eus.ehu.tta.ttaejemplo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGINPREF = "login";
    private BusinessInterface business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.business = new BusinessReal(getResources().getString(R.string.baseURL));
        String login = getLogin();
        EditText editText = findViewById(R.id.loginEditText);
        editText.setText(login);
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

    public void saveLogin(String login)
    {
        SharedPreferences prefs = this.getSharedPreferences("eus.ehu.tta.ttaejemplo", Context.MODE_PRIVATE);
        prefs.edit().putString(LOGINPREF,login).apply();
    }

    public String getLogin()
    {
        SharedPreferences prefs = this.getSharedPreferences("eus.ehu.tta.ttaejemplo", Context.MODE_PRIVATE);
        return prefs.getString(LOGINPREF,"");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        EditText editText = findViewById(R.id.loginEditText);
        saveLogin(editText.getText().toString());
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
                EditText editText = findViewById(R.id.loginEditText);
                saveLogin(editText.getText().toString());

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
