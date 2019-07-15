
package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ContaPagarDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.TipoPagamentoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaPagar;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.TipoPagamento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLContaPagarCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private ComboBox<TipoPagamento> comboTipo;
    @FXML
    private TextField textValor;
    @FXML
    private DatePicker DateVenc;
    @FXML
    private TextArea textObs;
    
    private ContaPagarDao contaPagarDao;
    private TipoPagamentoDao tipoPagamentoDao;
    private Stage stage;
    private ContaPagar contaPagar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaPagarDao = new ContaPagarDao();
        this.tipoPagamentoDao = new TipoPagamentoDao();
        ObservableList<TipoPagamento> tipoPagamento
                = FXCollections.observableArrayList(
                        tipoPagamentoDao.getAll()
                );
        this.comboTipo.setItems(tipoPagamento);
    }
    public void setDialogStage(Stage stage) {
            this.stage = stage;
        }    
    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
        if (contaPagar.getId() != null) {
            textId.setText(contaPagar.getId().toString());
            comboTipo.setValue(contaPagar.getTipoPagamento());
            textValor.setText(contaPagar.getValor().toString());
            DateVenc.setValue(contaPagar.getDataVenc());
            textObs.setText(contaPagar.getObs());
        }
    }
    @FXML
    private void cancel() {
        this.stage.close();
    }
    @FXML
    private void save() {
        contaPagar.setTipoPagamento(comboTipo.getSelectionModel()
                        .getSelectedItem());
        contaPagar.setValor(Double.parseDouble(
                textValor.getText()));
        contaPagar.setDataVenc(DateVenc.getValue());
        contaPagar.setObs(textObs.getText());
        this.contaPagarDao.save(contaPagar);
        this.stage.close();
    }
}
