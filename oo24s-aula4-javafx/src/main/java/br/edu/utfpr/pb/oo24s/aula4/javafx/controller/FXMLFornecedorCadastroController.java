/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.CidadeDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.EstadoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.FornecedorDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Cidade;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Estado;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Fornecedor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLFornecedorCadastroController implements Initializable {
@FXML
    private TextField textId;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textCnpj;
    @FXML
    private TextField textTelefone;
    @FXML
    private ComboBox<Estado> comboEstado;
    @FXML
    private ComboBox<Cidade> comboCidade;
    @FXML
    private TextField textCep;
    @FXML
    private TextField textRua;
    @FXML
    private TextField textNumero;

    private FornecedorDao fornecedorDao;
    private EstadoDao estadoDao;
    private CidadeDao cidadeDao;
    private Stage stage;
    private Fornecedor fornecedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fornecedorDao = new FornecedorDao();
        this.estadoDao = new EstadoDao();
        this.cidadeDao = new CidadeDao();
        ObservableList<Estado> estados
                = FXCollections.observableArrayList(
                        estadoDao.getAll()
                );
        
        this.comboEstado.setItems(estados);
       
    }

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        if (fornecedor.getId() != null) {
            textId.setText(fornecedor.getId().toString());
            textNome.setText(fornecedor.getNome());
            textCnpj.setText(fornecedor.getCnpj());
            textTelefone.setText(fornecedor.getTelefone());
            comboEstado.setValue(fornecedor.getEstado());
            comboCidade.setValue(fornecedor.getCidade());
            textCep.setText(fornecedor.getCep());
            textRua.setText(fornecedor.getRua());
            textNumero.setText(fornecedor.getNumero().toString());
        }
    }

    @FXML
    private void cancel() {
        this.stage.close();
    }

    @FXML
    private void save() {
        fornecedor.setNome(textNome.getText());
        fornecedor.setCnpj(textCnpj.getText());
        fornecedor.setTelefone(textTelefone.getText());
        fornecedor.setEstado(
                comboEstado.getSelectionModel()
                        .getSelectedItem());
        fornecedor.setCidade(
                comboCidade.getSelectionModel()
                        .getSelectedItem());
        fornecedor.setCep(textCep.getText());
        fornecedor.setRua(textRua.getText());
        fornecedor.setNumero(Integer.parseInt(textNumero.getText()));
        this.fornecedorDao.save(fornecedor);
        this.stage.close();
    }
    
    @FXML
    private void carregarCidade(){
         ObservableList<Cidade> cidades
                = FXCollections.observableArrayList(
                        cidadeDao.findByCidadeId(comboEstado.getSelectionModel()
                        .getSelectedItem().getId())
                );
        this.comboCidade.setItems(cidades);
    } 
    
}
