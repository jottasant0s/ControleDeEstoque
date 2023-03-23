package com.example.controledeprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FormProdutoActivity extends AppCompatActivity {

    private EditText editProduto;
    private EditText editQuantidade;
    private EditText editValor;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        produtoDAO = new ProdutoDAO(this);

        editProduto = findViewById(R.id.edit_produto);
        editQuantidade = findViewById(R.id.edit_quantidade);
        editValor = findViewById(R.id.edit_valor);
    }

    public void saveProduct(View view) {
        String nameProduto = editProduto.getText().toString();
        String quantidadeProduto = editQuantidade.getText().toString();
        String valorProduto = editValor.getText().toString();

        if (!nameProduto.isEmpty()) {
            if (!quantidadeProduto.isEmpty()) {
                int qtd = Integer.parseInt(quantidadeProduto);

                if(qtd >= 1) {
                    if (!valorProduto.isEmpty()) {

                        double valor = Double.parseDouble(valorProduto);

                        if(valor > 0) {

                            Produto produto = new Produto();
                            produto.setName(nameProduto);
                            produto.setEstoque(qtd);
                            produto.setValor(valor);

                            produtoDAO.saveProduct(produto);

                            finish();

                        } else {
                            editValor.requestFocus();
                            editValor.setError("Informe um valor maior que 0.");
                        }

                    } else {
                        editValor.requestFocus();
                        editValor.setError("Informe o valor do produto.");
                    }
                } else {
                    editQuantidade.requestFocus();
                    editQuantidade.setError("Informe um valor maior que 0.");
                }

            } else {
                editQuantidade.requestFocus();
                editQuantidade.setError("Informe um valor válido.");
            }
        } else {
            editProduto.requestFocus();
            editProduto.setError("Informe o nome do produto.");
        }
    }
}