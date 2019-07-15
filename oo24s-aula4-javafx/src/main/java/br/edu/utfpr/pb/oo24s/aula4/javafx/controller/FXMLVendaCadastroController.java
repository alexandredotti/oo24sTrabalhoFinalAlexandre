/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ClienteDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ProdutoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.VendaDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Cliente;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaReceber;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Produto;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Venda;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.VendaProduto;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMLVendaCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private DatePicker dataVenda;
    @FXML
    private ComboBox comboCliente;
    @FXML
    private ComboBox comboProduto;
    @FXML
    private TextField textQtde;
    @FXML
    private TextField textValor;
    @FXML
    private TextField textValorTotal;
    @FXML
    private TableView<VendaProduto> tableData;
    @FXML
    private TableColumn<VendaProduto, Produto> columnProduto;
    @FXML
    private TableColumn<VendaProduto, Integer> columnQtde;
    @FXML
    private TableColumn<VendaProduto, Double> columnValorUnit;
    @FXML
    private TableColumn<VendaProduto, Double> columnValorTotal;
    
    private Venda venda;
    private VendaDao vendaDao;
    private ProdutoDao produtoDao;
    private ClienteDao clienteDao;
    private Stage dialogStage;
    private List<VendaProduto> vendaProdutoList;
    private ObservableList<VendaProduto> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.vendaDao = new VendaDao();
        this.produtoDao = new ProdutoDao();
        this.clienteDao = new ClienteDao();
        this.venda = new Venda();
        this.vendaProdutoList = new ArrayList<VendaProduto>();

        ObservableList<Produto> produtos =
                FXCollections.observableArrayList(
                        this.produtoDao.getAll()
                );
        this.comboProduto.setItems(produtos);

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList(
                        this.clienteDao.getAll()
                );
        this.comboCliente.setItems(clientes);
        this.comboProduto.setOnAction(event -> {
            this.textValor.setText(
                    ((Produto) this.comboProduto.getSelectionModel()
                            .getSelectedItem()).getValor().toString()
            );
        });

        this.tableData.getSelectionModel()
                .setSelectionMode(
                        SelectionMode.SINGLE);
        setColumnProperties();
        this.setDefaultValues();
    }    

    private void setColumnProperties() {
        this.columnProduto.setCellValueFactory(
                new PropertyValueFactory<>("produto")
        );
        this.columnValorUnit.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );
        this.columnQtde.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );
        this.columnValorTotal.setCellValueFactory(
                new PropertyValueFactory<>("vlrTotal")
        );
    }

    private void setDefaultValues() {
         this.textValor.setText("0.00");
        this.textValorTotal.setText("0.00");
        this.dataVenda.setValue(LocalDate.now());
    }
    
    public void setVenda(Venda venda) {
        this.venda = venda;
        if (venda.getId() != null) {
            this.textId.setText(venda.getId().toString());
            this.dataVenda.setValue(venda.getData());
            this.comboCliente.setValue(venda.getCliente());
            this.textValorTotal.setText(venda.getValorTotal().toString());
            this.vendaProdutoList = venda.getVendaProdutos();
        }
        loadData();
    }

    @FXML
    private void inserirProduto() {
        if (this.comboProduto.getSelectionModel().getSelectedItem() != null
                && this.textQtde != null) {
            VendaProduto vendaProduto = new VendaProduto();
            vendaProduto.setProduto((Produto) this.comboProduto.getSelectionModel().getSelectedItem());
            vendaProduto.setQuantidade(new Integer(this.textQtde.getText()));
            vendaProduto.setValor(((Produto) this.comboProduto.getSelectionModel().getSelectedItem()).getValor());
            vendaProduto.setVenda(this.venda);
            this.vendaProdutoList.add(vendaProduto);
        }
        this.venda.setVendaProdutos(this.vendaProdutoList);
        this.textValorTotal.setText(venda.getValorTotal().toString());
        loadData();
    }
    
    private void loadData() {
        this.list.clear();
        this.list.addAll(this.vendaProdutoList);
        tableData.setItems(list);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void cancel() {
        this.dialogStage.close();
    }

    @FXML
    private void save(ActionEvent event) {
        venda.setData(dataVenda.getValue());
        venda.setCliente((Cliente) comboCliente.getSelectionModel().getSelectedItem());
        venda.setVendaProdutos(vendaProdutoList);
        this.openPagamento(new ContaReceber(), event);
        if (this.vendaDao.isValid(venda)) {
            this.vendaDao.save(venda);
            this.dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            alert.setContentText(this.vendaDao.getErrors(venda));
            alert.showAndWait();
        }
    }

    private void openPagamento(ContaReceber contaReceber,
                               ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLContaReceberCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Pagamento");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLContaReceberCadastroController controller =
                    loader.getController();
            contaReceber.setVenda(this.venda);
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
    }
    
}
