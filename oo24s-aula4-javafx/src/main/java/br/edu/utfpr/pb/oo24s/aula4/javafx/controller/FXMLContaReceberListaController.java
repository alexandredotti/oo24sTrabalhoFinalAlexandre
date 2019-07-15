package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ContaReceberDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaReceber;
import java.math.BigDecimal;
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

public class FXMLContaReceberListaController implements Initializable {

    @FXML
    private TableView<ContaReceber> tableData;
    @FXML
    private TableColumn<ContaReceber, Long> columnId;
    @FXML
    private TableColumn<ContaReceber, String> columnTipo;
    @FXML
    private TableColumn<ContaReceber, BigDecimal> columnValor;
    @FXML
    private TableColumn<ContaReceber, LocalDate> columnDataV;
    @FXML
    private TableColumn<ContaReceber, String> columnObs;

    @FXML
    private Button buttonEdit;
    private ContaReceberDao contaReceberDao;
    private ObservableList<ContaReceber> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaReceberDao = new ContaReceberDao();
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
                new PropertyValueFactory<>("valorConta")
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
        this.list.addAll(this.contaReceberDao.getAll());
        tableData.setItems(list);
    }
    
    private void openForm(ContaReceber contaReceber,
                          ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLContaReceberCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Contas a Receber");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLContaReceberCadastroController controller =
                    loader.getController();
            controller.setContaReceber(contaReceber);
            controller.setDialogStage(dialogStage);
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
        ContaReceber contaReceber =
                tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(contaReceber, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new ContaReceber(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                ContaReceber contaReceber = tableData
                        .getSelectionModel().getSelectedItem();
                contaReceberDao.delete(contaReceber.getId());
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
