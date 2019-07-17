
package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.CompraDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.FornecedorDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ProdutoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Compra;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.CompraProduto;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaPagar;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Fornecedor;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Produto;
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


public class FXMLCompraCadastroController implements Initializable {
    @FXML
    private TextField textId;
    @FXML
    private DatePicker dataCompra;
    @FXML
    private ComboBox comboFornecedor;
    @FXML
    private ComboBox comboProduto;
    @FXML
    private TextField textQtde;
    @FXML
    private TextField textValor;
    @FXML
    private TextField textValorTotal;
    @FXML
    private TableView<CompraProduto> tableData;
    @FXML
    private TableColumn<CompraProduto, Produto> columnProduto;
    @FXML
    private TableColumn<CompraProduto, Integer> columnQtde;
    @FXML
    private TableColumn<CompraProduto, Double> columnValorUnit;
    @FXML
    private TableColumn<CompraProduto, Double> columnValorTotal;
    
    private Compra compra;
    private CompraDao compraDao;
    private ProdutoDao produtoDao;
    private FornecedorDao fornecedorDao;
    private Stage dialogStage;
    private List<CompraProduto> compraProdutoList;
    private ObservableList<CompraProduto> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.compraDao = new CompraDao();
        this.produtoDao = new ProdutoDao();
        this.fornecedorDao = new FornecedorDao();
        this.compra = new Compra();
        this.compraProdutoList = new ArrayList<CompraProduto>();
        
        ObservableList<Produto> produtos =
                FXCollections.observableArrayList(
                        this.produtoDao.getAll()
                );
        this.comboProduto.setItems(produtos);

        ObservableList<Fornecedor> fornecedor =
                FXCollections.observableArrayList(
                        this.fornecedorDao.getAll()
                );
        this.comboFornecedor.setItems(fornecedor);

        this.comboProduto.setOnAction(event -> {
            this.textValor.setText(
                    ((Produto) this.comboProduto.getSelectionModel()
                            .getSelectedItem()).getValorCusto().toString()
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
        this.dataCompra.setValue(LocalDate.now());
    }
    
    public void setCompra(Compra compra) {
        this.compra = compra;
        if (compra.getId() != null) {
            this.textId.setText(compra.getId().toString());
            this.dataCompra.setValue(compra.getData());
            this.comboFornecedor.setValue(compra.getFornecedor());
            this.columnValorTotal.setText(compra.getValorTotal().toString());
            this.compraProdutoList = compra.getCompraProdutos();
        }
        loadData();
    }
    @FXML
    private void inserirProduto() {
        if (this.comboProduto.getSelectionModel().getSelectedItem() != null
                && this.textQtde != null) {
            CompraProduto compraProduto = new CompraProduto();
            compraProduto.setProduto((Produto) this.comboProduto.getSelectionModel().getSelectedItem());
            compraProduto.setQuantidade(new Integer(this.textQtde.getText()));
            compraProduto.setValor(((Produto) this.comboProduto.getSelectionModel().getSelectedItem()).getValorCusto());
            compraProduto.setCompra(this.compra);
            this.compraProdutoList.add(compraProduto);
        }
        this.compra.setCompraProdutos(this.compraProdutoList);
        this.textValorTotal.setText(compra.getValorTotal().toString());
        loadData();
    }
    
    private void loadData() {
        this.list.clear();
        this.list.addAll(this.compraProdutoList);
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
        compra.setData(dataCompra.getValue());
        compra.setFornecedor((Fornecedor) comboFornecedor.getSelectionModel().getSelectedItem());
        compra.setCompraProdutos(compraProdutoList);
        ContaPagar cp = new ContaPagar();
        compra.setContaPagar(cp);
        this.openPagamento(cp, event);
        if (this.compraDao.isValid(compra)) {
            this.compraDao.save(compra);
            this.dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            alert.setContentText(this.compraDao.getErrors(compra));
            alert.showAndWait();
        }
    }
    
     private void openPagamento(ContaPagar contaPagar,
                               ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLContaPagarCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Pagamento");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            FXMLContaPagarCadastroController controller =
                    loader.getController();
            controller.setContaPagar(contaPagar);
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
    
}
