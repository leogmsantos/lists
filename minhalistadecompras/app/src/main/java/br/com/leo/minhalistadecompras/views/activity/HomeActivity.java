package br.com.leo.minhalistadecompras.views.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.leo.minhalistadecompras.AppGeral;
import br.com.leo.minhalistadecompras.R;
import br.com.leo.minhalistadecompras.helper.ConfiguracaoFirebase;
import br.com.leo.minhalistadecompras.model.FilmeModel;
import br.com.leo.minhalistadecompras.model.ListaDeComprasModel;
import br.com.leo.minhalistadecompras.model.ListaModel;
import br.com.leo.minhalistadecompras.model.User;
import br.com.leo.minhalistadecompras.views.adapter.ItemBuyListAdapter;
import br.com.leo.minhalistadecompras.views.adapter.ItemListAdapter;
import br.com.leo.minhalistadecompras.views.dialog.CreateBuyListDialog;
import br.com.leo.minhalistadecompras.views.dialog.CreateMovieListDialog;
import br.com.leo.minhalistadecompras.views.dialog.CreateTaskListDialog;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference buyReference, taskReference, movieReference;
    private Button btnDeslogar;
    private FloatingActionButton menu_item_lista_de_compras, menu_item_lista_de_tarefas, menu_item_lista_de_filmes;
    private String email;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView recyclerHome;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ListaModel> lista = new ArrayList();
    private ProgressBar progressHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        initiliazeComponents();
    }


    private void initiliazeComponents(){
        menu_item_lista_de_compras = findViewById(R.id.menu_item_lista_de_compras);
        menu_item_lista_de_tarefas = findViewById(R.id.menu_item_lista_de_tarefas);
        menu_item_lista_de_filmes = findViewById(R.id.menu_item_lista_de_filmes);
        btnDeslogar = findViewById(R.id.btnDeslogar);
        progressHome = findViewById(R.id.progressHome);
        recyclerHome = findViewById(R.id.recyclerHomeList);
        recyclerListSetup();
        createList();
        deslogarUser();
        getExtras();
    }

    private void getExtras(){
    }

    private void deslogarUser(){

        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

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

    private void recyclerListSetup(){
        layoutManager = new LinearLayoutManager(this);
        recyclerHome.setLayoutManager(layoutManager);
        recyclerHome.setHasFixedSize(true);
        mAdapter = new ItemListAdapter(lista, this);
        recyclerHome.setAdapter(mAdapter);
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

    private void recuperarListasCadastradas(FirebaseUser user){
         buyReference = ConfiguracaoFirebase.getReferenciaFirebase().child("compras").child(user.getUid());
         taskReference = ConfiguracaoFirebase.getReferenciaFirebase().child("tarefas").child(user.getUid());
         movieReference = ConfiguracaoFirebase.getReferenciaFirebase().child("filmes").child(user.getUid());

         progressHome.setVisibility(View.VISIBLE);

         buyReference.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 ListaModel listaModel = new ListaModel();
                 for (DataSnapshot ds : dataSnapshot.getChildren()){
                     ListaDeComprasModel compras = ds.getValue(ListaDeComprasModel.class);
                     listaModel.setCategoria(AppGeral.LISTA_DE_COMRAS);
                     listaModel.setIdLista(compras.getId());
                     listaModel.setTitulo(ds.getKey());
                     lista.add(listaModel);
                 }
                 mAdapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

         taskReference.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 ListaModel listaModel = new ListaModel();
                 for (DataSnapshot ds : dataSnapshot.getChildren()){
                     ListaDeComprasModel compras = ds.getValue(ListaDeComprasModel.class);
                     listaModel.setCategoria(AppGeral.LISTA_DE_TAREFAS);
                     listaModel.setIdLista(compras.getId());
                     listaModel.setTitulo(ds.getKey());
                     lista.add(listaModel);
                 }
                 mAdapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

         movieReference.addListenerForSingleValueEvent(new ValueEventListener() {
             ListaModel listaModel = new ListaModel();
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for (DataSnapshot ds : dataSnapshot.getChildren()){
                     ListaDeComprasModel compras = ds.getValue(ListaDeComprasModel.class);
                     listaModel.setCategoria(AppGeral.LISTA_DE_FILMES);
                     listaModel.setIdLista(compras.getId());
                     listaModel.setTitulo(ds.getKey());
                     lista.add(listaModel);
                 }
                 mAdapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
         progressHome.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        recuperarListasCadastradas(user);
    }
}
