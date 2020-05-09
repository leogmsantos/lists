package br.com.leo.minhalistadecompras.views.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;
import br.com.leo.minhalistadecompras.model.User;

public class SignInActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtRepeatedPassword;
    private Button btnSignIn;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private User user;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initializeComponents();
    }

    private void initializeComponents(){
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepeatedPassword = findViewById(R.id.edtRepeatedPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        signInSettings();
    }

    private void signInSettings(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String repeatedPassword = edtRepeatedPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty() && !repeatedPassword.isEmpty()){
                    if (password.equals(repeatedPassword)){
                        registerUser(email, password);
                    }else{
                        Toast.makeText(SignInActivity.this, "Senhas incompatíveis.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignInActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                user = new User();
                userId = task.getResult().getUser().getUid();
                user.setId(userId);
                user.setEmail(email);
                user.setSenha(password);
                user.saveUserOnDatabase();

                FirebaseUser user = auth.getCurrentUser();
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                intent.putExtra("user", userId);
                intent.putExtra(AppGeral.USER, user);

                startActivity(intent);
            }
        });
    }

    public static String getUserId() {
        return userId;
    }
}
