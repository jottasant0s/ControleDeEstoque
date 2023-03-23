package com.example.controledeprodutos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.onClick {

    private AdapterProduto adapterProduto;
    private SwipeableRecyclerView rvProdutos;
    private ImageButton ibAdd;
    private ImageButton ibMore;

    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);

        ibAdd = findViewById(R.id.ib_add);
        ibMore = findViewById(R.id.ib_more);
        rvProdutos = findViewById(R.id.rvProdutos);

        configRecyclerView();
        listenClick();
    }

    private void listenClick() {
        ibAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, FormProdutoActivity.class));
        });

        ibMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, ibMore);
            popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_sobre) {
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void configRecyclerView() {
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtoDAO.getListProducts(), this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                produtoDAO.getListProducts().remove(position);
                adapterProduto.notifyItemRemoved(position);
            }
        });
    }

    @Override
    public void onClickListener(Produto produto) {
        Toast.makeText(this, produto.getName(), Toast.LENGTH_SHORT).show();
    }

}