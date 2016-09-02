/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.AeroportoDAO;
import br.com.OCTur.control.DAO.AviaoDAO;
import br.com.OCTur.control.DAO.CidadeDAO;
import br.com.OCTur.control.DAO.PaisDAO;
import br.com.OCTur.control.DAO.TrajetoDAO;
import br.com.OCTur.control.DAO.VooDAO;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Aviao;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Pais;
import br.com.OCTur.model.entity.Trajeto;
import br.com.OCTur.model.entity.Voo;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarVoosController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbCompanhia;
    @FXML
    private ComboBox<Pais> cbPaisOrigem;
    @FXML
    private ComboBox<Pais> cbPaisDestino;
    @FXML
    private ComboBox<Cidade> cbOrigem;
    @FXML
    private ComboBox<Cidade> cbDestino;
    @FXML
    private DatePicker dpPartida;
    @FXML
    private DatePicker dpChegada;
    @FXML
    private TableView<Voo> tvVoo;
    @FXML
    private TableColumn<Voo, Aviao> tcAviao;
    @FXML
    private TableColumn<Voo, Aeroporto> tcDestino;
    @FXML
    private TableColumn<Voo, String> tcPartida;
    @FXML
    private TableColumn<Voo, String> tcChegada;
    @FXML
    private TableColumn<Voo, Double> tcPreco;
    @FXML
    private FormattedTextField ftfHorarioPartida;
    @FXML
    private FormattedTextField ftfHorarioChegada;
    @FXML
    private TextField tfPreco;
    @FXML
    private ComboBox<Aviao> cbAviao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btRemover;

    private ObservableList<Voo> voos = FXCollections.observableArrayList();
    private ObservableList<Pais> pais = FXCollections.observableArrayList();
    private ObservableList<Cidade> origem = FXCollections.observableArrayList();
    private ObservableList<Cidade> destino = FXCollections.observableArrayList();
    private ObservableList<Aviao> aviaos = FXCollections.observableArrayList();

    private Voo voo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btSalvar, new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY)));
            apPrincipal.getScene().addMnemonic(new Mnemonic(btRemover, new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_ANY)));
        });
        cbPaisDestino.setItems(pais);
        cbPaisOrigem.setItems(pais);
        cbOrigem.setItems(origem);
        cbDestino.setItems(destino);
        cbAviao.setItems(aviaos);
        tvVoo.setItems(voos);
        aviaos.setAll(new AviaoDAO().pegarTodos());
        pais.setAll(new PaisDAO().pegarTodos());
        tcDestino.setCellValueFactory((TableColumn.CellDataFeatures<Voo, Aeroporto> param) -> new SimpleObjectProperty<>(param.getValue().getTrajeto().getDestino()));
        tcAviao.setCellValueFactory(new PropertyValueFactory<>("aviao"));
        tcPartida.setCellValueFactory((TableColumn.CellDataFeatures<Voo, String> param) -> new SimpleStringProperty(DateFormatter.toDate(param.getValue().getDatapartida()) + " " + DateFormatter.toHour(param.getValue().getHorapartida())));
        tcChegada.setCellValueFactory((TableColumn.CellDataFeatures<Voo, String> param) -> new SimpleStringProperty(DateFormatter.toDate(param.getValue().getDatachegada()) + " " + DateFormatter.toHour(param.getValue().getHorachegada())));
        tcPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tcPreco.setCellFactory((TableColumn<Voo, Double> param) -> new TableCell<Voo, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(NumberFormatter.toMoney(item));
                }
            }

        });
        ftfHorarioChegada.getPatternVerifiers().put("HH", new IntegerRangePatternVerifier(0, 23));
        ftfHorarioChegada.getPatternVerifiers().put("mm", new IntegerRangePatternVerifier(0, 59));
        ftfHorarioChegada.getPatternVerifiers().put("ss", new IntegerRangePatternVerifier(0, 59));
        ftfHorarioPartida.getPatternVerifiers().put("HH", new IntegerRangePatternVerifier(0, 23));
        ftfHorarioPartida.getPatternVerifiers().put("mm", new IntegerRangePatternVerifier(0, 59));
        ftfHorarioPartida.getPatternVerifiers().put("ss", new IntegerRangePatternVerifier(0, 59));
        ftfHorarioChegada.setPattern("HH:mm:ss");
        ftfHorarioPartida.setPattern("HH:mm:ss");
        eCarregarTabelaActionEvent(null);
    }

    @FXML
    private void cbPaisOrigemActionEvent(ActionEvent actionEvent) {
        if (cbPaisOrigem.getSelectionModel().getSelectedItem() != null) {
            origem.setAll(new CidadeDAO().pegarPorPais(cbPaisOrigem.getSelectionModel().getSelectedItem()));
        } else {
            origem.clear();
        }
    }

    @FXML
    private void cbPaisDestinoActionEvent(ActionEvent actionEvent) {
        if (cbPaisDestino.getSelectionModel().getSelectedItem() != null) {
            destino.setAll(new CidadeDAO().pegarPorPais(cbPaisDestino.getSelectionModel().getSelectedItem()));
        } else {
            destino.clear();
        }
    }

    @FXML
    private void eCarregarTabelaActionEvent(ActionEvent actionEvent) {
        List<Voo> voos;
        if (cbOrigem.getSelectionModel().getSelectedItem() != null) {
            if (dpPartida.getValue() != null) {
                voos = new VooDAO().pegarPorOrigemPartida(cbOrigem.getSelectionModel().getSelectedItem(), DateFormatter.toDate(dpPartida.getValue()));
            } else {
                voos = new VooDAO().pegarPorOrigem(cbOrigem.getSelectionModel().getSelectedItem());
            }
        } else if (dpPartida.getValue() != null) {
            voos = new VooDAO().pegarPorPartida(DateFormatter.toDate(dpPartida.getValue()));
        } else {
            voos = new VooDAO().pegarTodos();
        }
        this.voos.setAll(voos);

    }

    @FXML
    private void tvVoosMouseReleased(MouseEvent mouseEvent) {
        voo = tvVoo.getSelectionModel().getSelectedItem();
        btSalvar.setDisable(voo == null);
        if (voo != null) {
            cbPaisOrigem.getSelectionModel().select(voo.getTrajeto().getOrigem().getCidade().getEstado().getPais());
            cbPaisDestino.getSelectionModel().select(voo.getTrajeto().getDestino().getCidade().getEstado().getPais());
            cbPaisOrigemActionEvent(null);
            cbPaisDestinoActionEvent(null);
            cbOrigem.getSelectionModel().select(voo.getTrajeto().getOrigem().getCidade());
            cbDestino.getSelectionModel().select(voo.getTrajeto().getDestino().getCidade());
            dpPartida.setValue(DateFormatter.toLocalDate(voo.getDatapartida()));
            dpChegada.setValue(DateFormatter.toLocalDate(voo.getDatachegada()));
            tfPreco.setText(String.valueOf(voo.getPreco()));
            ftfHorarioChegada.setText(DateFormatter.toHour(voo.getHorachegada()));
            ftfHorarioPartida.setText(DateFormatter.toHour(voo.getHorapartida()));
            cbAviao.getSelectionModel().select(voo.getAviao());
        } else {
            voo = new Voo();
            cbPaisOrigem.getSelectionModel().clearSelection();
            cbPaisDestino.getSelectionModel().clearSelection();
            cbPaisOrigemActionEvent(null);
            cbPaisDestinoActionEvent(null);
            cbOrigem.getSelectionModel().clearSelection();
            cbDestino.getSelectionModel().clearSelection();
            dpPartida.setValue(DateFormatter.toLocalDate(voo.getDatapartida()));
            dpChegada.setValue(DateFormatter.toLocalDate(voo.getDatachegada()));
            tfPreco.setText(String.valueOf(voo.getPreco()));
            ftfHorarioChegada.setText(DateFormatter.toHour(voo.getHorachegada()));
            ftfHorarioPartida.setText(DateFormatter.toHour(voo.getHorapartida()));
            cbAviao.getSelectionModel().select(voo.getAviao());
        }
        btRemover.setDisable(tvVoo.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        voo.setAviao(cbAviao.getSelectionModel().getSelectedItem());
        voo.setDatachegada(DateFormatter.toDate(dpChegada.getValue()));
        voo.setDatapartida(DateFormatter.toDate(dpPartida.getValue()));
        voo.setHorachegada(Time.valueOf(ftfHorarioChegada.getText()));
        voo.setHorapartida(Time.valueOf(ftfHorarioPartida.getText()));
        voo.setPreco(Double.parseDouble(tfPreco.getText().replace(",", ".")));
        List<Aeroporto> origem = new AeroportoDAO().pegarPorCidade(cbOrigem.getSelectionModel().getSelectedItem());
        List<Aeroporto> destino = new AeroportoDAO().pegarPorCidade(cbDestino.getSelectionModel().getSelectedItem());
        if (origem.isEmpty() || destino.isEmpty()) {
            Message.show("", "Não é possível criar voo pois não há trajeto \n"
                    + "pois não há aeroportos nos locais", Alert.AlertType.ERROR);
        } else {
            Trajeto trajeto = new TrajetoDAO().pegarPorOrigemDestino(origem.get(0), destino.get(0));
            if (trajeto == null) {
                trajeto = new Trajeto();
                trajeto.setOrigem(origem.get(0));
                trajeto.setDestino(destino.get(0));
                new TrajetoDAO().cadastrar(trajeto);
            }
            voo.setTrajeto(trajeto);
            if (voo.getId() != null) {
                new VooDAO().editar(voo);
                Message.show("", ControlTranducao.traduzirPalavra("EVVOOATUALIZADO"), Alert.AlertType.INFORMATION);
                eCarregarTabelaActionEvent(null);
            } else {
                new VooDAO().cadastrar(voo);
                Message.show("", ControlTranducao.traduzirPalavra("evvooadicionado"), Alert.AlertType.INFORMATION);
                eCarregarTabelaActionEvent(null);
            }
        }
    }

    @FXML
    private void btRemoverActionEvent(ActionEvent actionEvent) {
        new VooDAO().excluir(tvVoo.getSelectionModel().getSelectedItem());
        eCarregarTabelaActionEvent(actionEvent);
        btRemover.setDisable(tvVoo.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        cbAviao.getSelectionModel().clearSelection();
        voo = new Voo();
        cbPaisOrigem.getSelectionModel().clearSelection();
        cbPaisDestino.getSelectionModel().clearSelection();
        cbPaisOrigemActionEvent(null);
        cbPaisDestinoActionEvent(null);
        cbOrigem.getSelectionModel().clearSelection();
        cbDestino.getSelectionModel().clearSelection();
        dpPartida.setValue(DateFormatter.toLocalDate(voo.getDatapartida()));
        dpChegada.setValue(DateFormatter.toLocalDate(voo.getDatachegada()));
        tfPreco.clear();
        ftfHorarioChegada.clear();
        ftfHorarioPartida.clear();
        cbAviao.getSelectionModel().select(voo.getAviao());
        btSalvar.setDisable(false);
    }
}
