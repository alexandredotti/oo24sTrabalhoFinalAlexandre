/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.FornecedorDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Fornecedor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class FXMLFornecedorListaController implements Initializable {

    @FXML
    private TableView<Fornecedor> tableData;
    @FXML
    private TableColumn<Fornecedor, Long> columnId;
    @FXML
    private TableColumn<Fornecedor, String> columnNome;
    @FXML
    private TableColumn<Fornecedor, String> columnCnpj;
    @FXML
    private TableColumn<Fornecedor, String> columnTelefone;
    @FXML
    private Button buttonEdit;
    
    private FornecedorDao fornecedorDao;
    private ObservableList<Fornecedor> list
            = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fornecedorDao = new FornecedorDao();
        this.tableData.getSelectionModel()
                .setSelectionMode(
                        SelectionMode.SINGLE);
        setColumnProperties();
        loadData();
    }
    private void setColumnProperties() {
        this.columnId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        this.columnNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );
        this.columnCnpj.setCellValueFactory(
                new PropertyValueFactory<>("cnpj")
        );
        this.columnTelefone.setCellValueFactory(
                new PropertyValueFactory<>("telefone")
        );
       

    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.fornecedorDao.getAll());
        tableData.setItems(list);
    }
    private void openForm(Fornecedor fornecedor,
            ActionEvent event) {
        try {
            // Carregar o arquivo fxml e cria um
            //novo stage para a janela Modal
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLFornecedorCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            //Criando o stage para o modal
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Fornecedor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLFornecedorCadastroController controller
                    = loader.getController();
            controller.setFornecedor(fornecedor);
            controller.setDialogStage(dialogStage);
            // Exibe a janela Modal e espera até o usuário
            //fechar
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ocorreu um erro ao abrir "
                    + "a janela de cadastro!");
            alert.setContentText("Por favor, tente realizar "
                    + "a operação novamente!");
            alert.showAndWait();
        }
        loadData();
    }

    @FXML
    private void edit(ActionEvent event) {
        Fornecedor fornecedor
                = tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(fornecedor, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new Fornecedor(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                Fornecedor fornecedor = tableData
                        .getSelectionModel().getSelectedItem();
                fornecedorDao.delete(fornecedor.getId());
                tableData.getItems().remove(
                        tableData.getSelectionModel()
                                .getSelectedIndex());

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Ocorreu um erro "
                        + " ao remover o registro!");
                alert.setContentText("Por favor, tente realizar "
                        + "a operação novamente!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum registro "
                    + "selecionado");
            alert.setContentText("Por favor, "
                    + "selecione um registro "
                    + "na tabela!");
            alert.showAndWait();
        }
    }    
    
}
