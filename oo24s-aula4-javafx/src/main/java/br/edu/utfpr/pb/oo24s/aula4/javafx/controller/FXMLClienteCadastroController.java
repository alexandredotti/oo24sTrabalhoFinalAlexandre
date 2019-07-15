package br.edu.utfpr.pb.oo24s.aula4.javafx.controller;

import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.CidadeDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.ClienteDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.dao.EstadoDao;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Cidade;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Cliente;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Estado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLClienteCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textCpf;
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

    private ClienteDao clienteDao;
    private EstadoDao estadoDao;
    private CidadeDao cidadeDao;
    private Stage stage;
    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.clienteDao = new ClienteDao();
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente.getId() != null) {
            textId.setText(cliente.getId().toString());
            textNome.setText(cliente.getNome());
            textCpf.setText(cliente.getCpf());
            textTelefone.setText(cliente.getTelefone());
            comboEstado.setValue(cliente.getEstado());
            comboCidade.setValue(cliente.getCidade());
            textCep.setText(cliente.getCep());
            textRua.setText(cliente.getRua());
            textNumero.setText(cliente.getNumero().toString());
        }
    }

    @FXML
    private void cancel() {
        this.stage.close();
    }

    @FXML
    private void save() {
        cliente.setNome(textNome.getText());
        cliente.setCpf(textCpf.getText());
        cliente.setTelefone(textTelefone.getText());
        cliente.setEstado(
                comboEstado.getSelectionModel()
                        .getSelectedItem());
        cliente.setCidade(
                comboCidade.getSelectionModel()
                        .getSelectedItem());
        cliente.setCep(textCep.getText());
        cliente.setRua(textRua.getText());
        cliente.setNumero(Integer.parseInt(textNumero.getText()));
        this.clienteDao.save(cliente);
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
