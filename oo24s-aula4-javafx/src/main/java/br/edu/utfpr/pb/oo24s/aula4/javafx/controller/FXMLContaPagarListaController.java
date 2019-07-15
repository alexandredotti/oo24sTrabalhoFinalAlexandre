package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ContaPagarDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaPagar;
import java.net.URL;
import java.time.LocalDate;
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

public class FXMLContaPagarListaController implements Initializable {

    @FXML
    private TableView<ContaPagar> tableData;
    @FXML
    private TableColumn<ContaPagar, Long> columnId;
    @FXML
    private TableColumn<ContaPagar, String> columnTipo;
    @FXML
    private TableColumn<ContaPagar, Double> columnValor;
    @FXML
    private TableColumn<ContaPagar, LocalDate> columnDataV;
    @FXML
    private TableColumn<ContaPagar, String> columnObs;
    @FXML
    private Button buttonEdit;
    private ContaPagarDao contaPagarDao;
    private ObservableList<ContaPagar> list
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaPagarDao = new ContaPagarDao();
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
        this.columnTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipoPagamento")
        );
        this.columnValor.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );
        this.columnDataV.setCellValueFactory(
                new PropertyValueFactory<>("dataVenc")
        );
        this.columnObs.setCellValueFactory(
                new PropertyValueFactory<>("obs")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.contaPagarDao.getAll());
        tableData.setItems(list);
    }
    private void openForm(ContaPagar contaPagar,
            ActionEvent event) {
        try {
            // Carregar o arquivo fxml e cria um
            //novo stage para a janela Modal
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLContaPagarCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            //Criando o stage para o modal
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Conta a pagar");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLContaPagarCadastroController controller
                    = loader.getController();
            controller.setContaPagar(contaPagar);
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
        ContaPagar contaPagar
                = tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(contaPagar, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new ContaPagar(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                ContaPagar contaPagar = tableData
                        .getSelectionModel().getSelectedItem();
                contaPagarDao.delete(contaPagar.getId());
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
