/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ContaReceberDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.TipoPagamentoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaReceber;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.TipoPagamento;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Venda;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLContaReceberCadastroController implements Initializable {
    @FXML
    private TextField textId;
    @FXML
    private ComboBox comboTipoPagamento;
    @FXML
    private TextField textValor;
    @FXML
    private TextField textNroParcelas;
    @FXML
    private TextField textValorParcela;
    @FXML
    private DatePicker dataVenc;
    @FXML
    private TextArea textAreaObservacao;
   
    private ContaReceber contaReceber;
    private ContaReceberDao contaReceberDao;
    private TipoPagamentoDao tipoPagamentoDao;
    private Stage dialogStage;
    private Venda venda;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaReceberDao = new ContaReceberDao();
        this.contaReceber = new ContaReceber();
        this.tipoPagamentoDao = new TipoPagamentoDao();
        ObservableList<TipoPagamento> tipoPagamento
                = FXCollections.observableArrayList(
                        tipoPagamentoDao.getAll()
                );
        this.comboTipoPagamento.setItems(tipoPagamento);
        this.setDefaultValues();
    }    

    private void setDefaultValues() {
        this.textValor.setText(BigDecimal.ZERO.toString());
        this.dataVenc.setValue(LocalDate.now());
        this.textValorParcela.setText(BigDecimal.ZERO.toString());
    }
    public void setContaReceber(ContaReceber contaReceber) {
        if (contaReceber.getVenda() != null) {
            this.venda = contaReceber.getVenda();
            this.textValor.setText(this.venda.getValorTotal().toString());
            this.textValor.setEditable(false);
        }
        this.contaReceber = contaReceber;
        if (contaReceber.getId() != null) {
            this.textId.setText(contaReceber.getId().toString());
            this.comboTipoPagamento.setValue(contaReceber.getTipoPagamento());
            this.textValor.setText(contaReceber.getValorConta().toString());
            this.textNroParcelas.setText(contaReceber.getNroParcelas().toString());
            this.textValorParcela.setText(contaReceber.getValorParcela().toString());
            this.dataVenc.setValue(contaReceber.getDataVenc());
            this.textAreaObservacao.setText(contaReceber.getObs());
        }
    }
     public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void cancel() {
        this.dialogStage.close();
    }

    @FXML
    private void save() {
        contaReceber.setTipoPagamento((TipoPagamento) comboTipoPagamento.getSelectionModel()
                        .getSelectedItem());
        contaReceber.setValorConta(new BigDecimal(textValor.getText()));
        contaReceber.setNroParcelas(new Integer(textNroParcelas.getText()));
        contaReceber.setValorParcela(new BigDecimal(textValorParcela.getText()));
        contaReceber.setDataVenc(dataVenc.getValue());
        contaReceber.setObs(textAreaObservacao.getText());
        if (this.venda != null) {
            List<ContaReceber> contaReceberList = new ArrayList<>();
            contaReceberList.add(this.contaReceber);
            this.venda.setContasAReceber(contaReceberList);
            this.dialogStage.close();
        } else {
            if (this.contaReceberDao.isValid(contaReceber)) {
                this.contaReceberDao.save(contaReceber);
                this.dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao salvar registro");
                alert.setContentText(this.contaReceberDao.getErrors(contaReceber));
                alert.showAndWait();
            }
        }


    }
    
}
