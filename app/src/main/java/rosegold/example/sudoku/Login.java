package rosegold.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText name, password;
    Button login;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText)findViewById(R.id.email2);
        password = (EditText)findViewById(R.id.password2);
        login = (Button)findViewById(R.id.login1);
        userRegistration = (TextView)findViewById(R.id.register);

        login.setOnClickListener(this);
        userRegistration.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            Log.i("Login Menu", user.getEmail()+" "+user.getDisplayName());
            finish();
            startActivity(new Intent(Login.this, MainMenu.class));
        }

    }


    @Override
    public void onClick(View v) {
        if(v == login) {
            validate(name.getText().toString(), password.getText().toString());
        }
        else if(v == userRegistration) {
            startActivity(new Intent(Login.this, Register.class));
        }
    }

    public void validate(String userName, String userPassword) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    verifyEmail();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyEmail() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //Boolean flag = firebaseUser.isEmailVerified();

        startActivity(new Intent(Login.this, MainMenu.class));
    }
}
