package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.CategoriaDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ProdutoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Categoria;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Produto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLProdutoCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML 
    private TextField textNome;
    @FXML
    private TextField textCusto;
    @FXML
    private TextField textValor;
    @FXML
    private ComboBox<Categoria> comboCategoria;
    @FXML
    private TextArea textDescricao;
    
    private ProdutoDao produtoDao;
    private CategoriaDao categoriaDao;
    private Stage stage;
    private Produto produto;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.produtoDao = new ProdutoDao();
        this.categoriaDao = new CategoriaDao();
        ObservableList<Categoria> categorias = 
                FXCollections.observableArrayList(
                        categoriaDao.getAll()
                );
        this.comboCategoria.setItems(categorias);
    }    
    
    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
        if (produto.getId() != null) {
            textId.setText(produto.getId().toString());
            textNome.setText(produto.getNome());
            textCusto.setText(produto.getValorCusto().toString());
            textValor.setText(produto.getValor().toString());
            comboCategoria.setValue(produto.getCategoria());
            textDescricao.setText(produto.getDescricao());
        }
    }
    /*
    private void carregarCidades() {
        Long id = this.comboCategoria.getSelectionModel().getSelectedItem().getId();
        ObservableList<Produto> produtos = 
                FXCollections.observableArrayList(
                        produtoDao.findByCategoriaId(id)
                );
        this.comboProduto.setItems(produtos);
    }
    */
    
    @FXML
    private void cancel() {
        this.stage.close();
    }
    @FXML
    private void save() {
        produto.setNome(textNome.getText());
        produto.setValorCusto(Double.valueOf(textCusto.getText()));
        produto.setValor(Double.parseDouble(
                textValor.getText()));
        produto.setDescricao(textDescricao.getText());
        produto.setCategoria(
                comboCategoria.getSelectionModel()
                        .getSelectedItem());
        this.produtoDao.save(produto);
        this.stage.close();
    }
}
