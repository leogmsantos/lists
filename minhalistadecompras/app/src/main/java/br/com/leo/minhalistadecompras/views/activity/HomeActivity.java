package br.com.leo.minhalistadecompras.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.views.dialog.CreateBuyListDialog;
import br.com.leo.minhalistadecompras.views.dialog.CreateMovieListDialog;
import br.com.leo.minhalistadecompras.views.dialog.CreateTaskListDialog;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FloatingActionButton menu_item_lista_de_compras, menu_item_lista_de_tarefas, menu_item_lista_de_filmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiliazeComponents();
    }


    private void initiliazeComponents(){
        menu_item_lista_de_compras = findViewById(R.id.menu_item_lista_de_compras);
        menu_item_lista_de_tarefas = findViewById(R.id.menu_item_lista_de_tarefas);
        menu_item_lista_de_filmes = findViewById(R.id.menu_item_lista_de_filmes);

        createList();
    }

    private void getExtras(){
        Intent intent = getIntent();
        user = (FirebaseUser) intent.getSerializableExtra(AppGeral.USER);
    }

    private void deslogarUser(){
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()){
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void createList(){
        menu_item_lista_de_compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBuysListDialog();
            }
        });

        menu_item_lista_de_tarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskListDialog();
            }
        });

        menu_item_lista_de_filmes .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieListDialog();
            }
        });
    }

    private void openBuysListDialog() {
        CreateBuyListDialog listDialog = new CreateBuyListDialog();
        listDialog.show(getSupportFragmentManager(), "Create List Dialog");
    }

    private void openTaskListDialog(){
        CreateTaskListDialog listDialog = new CreateTaskListDialog();
        listDialog.show(getSupportFragmentManager(), "Create List Dialog");
    }

    private void openMovieListDialog(){
        CreateMovieListDialog listDialog = new CreateMovieListDialog();
        listDialog.show(getSupportFragmentManager(), "Create List Dialog");
    }
}
