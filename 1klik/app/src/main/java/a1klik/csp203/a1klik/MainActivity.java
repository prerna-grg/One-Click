package a1klik.csp203.a1klik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import java.util.concurrent.ExecutionException;

import java.util.regex.Pattern;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Context stored;
    EditText UsernameEt,PasswordEt;
    CheckBox saveLoginCheckBox;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    Boolean saveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = (EditText)findViewById(R.id.emailInput);
        PasswordEt = (EditText)findViewById(R.id.passwordInput);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.rememberMeCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            UsernameEt.setText(loginPreferences.getString("username", ""));
            PasswordEt.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();

        String type = "login";
        if (isNetworkAvailable()==false)
        {
            TextView tv = (TextView) findViewById(R.id.tmpShowScreen);
            tv.setText("Please Check your Internet Connection");
            return;
        }
        if (isValid_email(username)==false)
        {
            TextView tv = (TextView) findViewById(R.id.tmpShowScreen);
            tv.setText("Please enter valid email ID");
            return;
        }
        else if (password.length()<6)
        {
            TextView tv = (TextView) findViewById(R.id.tmpShowScreen);
            tv.setText("Please enter valid Password");
            return;
        }
        else
        {
            try {
                TextView tv = (TextView) findViewById(R.id.tmpShowScreen);
                tv.setText("Please wait while we login");
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                String output = backgroundWorker.execute(type, username, password).get();
                if (output.equals("invalid")) {

                    TextView tv_new = (TextView) findViewById(R.id.tmpShowScreen);
                    tv_new.setText("Invalid emailID and Password ");
                    return;
                }

                else
                {
                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                    Intent iNewActivity = new Intent(MainActivity.this, after_login.class);
                    iNewActivity.putExtra("MyData", output);
                    startActivity(iNewActivity);
            }
            }
            catch (InterruptedException e)
            {
                System.out.println("Well found an Exception in MainActivity 1 ");
                ExceptionHandlerRedirector useThis=new ExceptionHandlerRedirector();
                useThis.loadNewActivity();
                e.getMessage();
                e.printStackTrace();
                startActivity(getIntent());

            }
            catch (ExecutionException e)
            {
                System.out.println("Well found an Exception in MainActivity 2");
                ExceptionHandlerRedirector useThis=new ExceptionHandlerRedirector();
                useThis.loadNewActivity();
                e.getMessage();
                e.printStackTrace();
                startActivity(getIntent());
            }
        }

    }
    public static boolean isValid_email(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public MainActivity() {
        this.stored=this;
    }
}
