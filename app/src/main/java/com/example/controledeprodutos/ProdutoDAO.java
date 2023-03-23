package com.example.controledeprodutos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ProdutoDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }

    public void saveProduct(Produto produto) {

        ContentValues cv = new ContentValues();
        cv.put("nome", produto.getName());
        cv.put("quantidade", produto.getEstoque());
        cv.put("valor", produto.getValor());

        try {
            write.insert(DBHelper.TB_PRODUTO, null, cv);
        //  write.close();
        } catch (Exception e) {
            Log.i("ERROR", "Error ao salvar produto: " + e.getMessage());
        }
    }

    @SuppressLint("Range")
    public List<Produto> getListProducts() {
        List<Produto> produtoList = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TB_PRODUTO + ";";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            int estoque = c.getInt(c.getColumnIndex("Estoque"));
            double valor = c.getDouble(c.getColumnIndex("valor"));

            Produto produto = new Produto();
            produto.setId(id);
            produto.setName(nome);
            produto.setEstoque(estoque);
            produto.setValor(valor);

            produtoList.add(produto);
        }

        return produtoList;
    }
}
