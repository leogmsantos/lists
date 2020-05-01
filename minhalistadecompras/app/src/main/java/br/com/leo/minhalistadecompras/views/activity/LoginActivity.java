package br.com.leo.minhalistadecompras.views.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import br.com.leo.minhalistadecompras.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin, edtSenha;
    private Button btnEntrar, btnFacebook;
    private TextView tvSignIn;

    private CallbackManager callbackManager;
    private FirebaseUser user;
    private AccessToken accessToken = AccessToken.getCurrentAccessToken();
    private FirebaseAuth firebaseAuth;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();
        initializeFirebaseSettings();
        initializeFacebookSettings();
        alreadyLogged();
    }


    @SuppressLint("WrongViewCast")
    private void initializeComponents(){
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnFacebook = findViewById(R.id.btnFacebook);
        tvSignIn = findViewById(R.id.tvSignIn);
        userLoginWithEmailandPassword();
    }

    private void initializeFirebaseSettings(){
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeFacebookSettings(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        userRegisterWhoLoggedWithFacebook(loginResult.getAccessToken());
                        goToHomeActivity();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, "FALHA! " + error, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void userRegisterWhoLoggedWithFacebook(final AccessToken acesstoken){
        firebaseAuth.createUserWithEmailAndPassword(acesstoken.getUserId(), acesstoken.getToken())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                             user = firebaseAuth.getCurrentUser();
                            Log.d("LOGIN", "SUCESSO!");
                        }else{
                            Log.d("LOGIN", "FALHA" + task.getException());
                        }
                    }
                });
    }

    private void userLoginWithEmailandPassword(){
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtLogin.getText().toString();
                String password = edtLogin.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            goToHomeActivity();
                        }
                    });
                }
            }
        });



    }

    private void alreadyLogged(){
        if (accessToken !=null && !accessToken.isExpired()){
            goToHomeActivity();
        }
    }

    private void goToHomeActivity(){
        intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            goToHomeActivity();
        }
    }
}
