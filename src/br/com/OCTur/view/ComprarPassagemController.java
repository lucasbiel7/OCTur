/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.AeroportoDAO;
import br.com.OCTur.control.DAO.PassagemDAO;
import br.com.OCTur.control.DAO.TrajetoDAO;
import br.com.OCTur.control.DAO.VooDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Empresa;
import br.com.OCTur.model.entity.Passagem;
import br.com.OCTur.model.entity.Trajeto;
import br.com.OCTur.model.entity.Voo;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ComprarPassagemController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private RadioButton rbIdaVolta;
    @FXML
    private RadioButton rbApenasIda;
    @FXML
    private ComboBox<Aeroporto> cbOrigem;
    @FXML
    private ComboBox<Trajeto> cbDestino;
    @FXML
    private DatePicker dpIda;
    @FXML
    private DatePicker dpVolta;
    @FXML
    private Spinner<Integer> spNumero;
    @FXML
    private Label lbVolta;
    @FXML
    private TableView<Voo> tvIda;
    @FXML
    private TableColumn<Voo, Empresa> tcEmpresaIda;
    @FXML
    private TableColumn<Voo, String> tcPartidaIda;
    @FXML
    private TableColumn<Voo, String> tcChegadaIda;
    @FXML
    private TableColumn<Voo, Double> tcPrecoIda;
    @FXML
    private TableView<Voo> tvVolta;
    @FXML
    private TableColumn<Voo, Empresa> tcEmpresaVolta;
    @FXML
    private TableColumn<Voo, String> tcPartidaVolta;
    @FXML
    private TableColumn<Voo, String> tcChegadaVolta;
    @FXML
    private TableColumn<Voo, Double> tcPrecoVolta;
    @FXML
    private Label lbPreco;
    private ObservableList<Aeroporto> origem = FXCollections.observableArrayList();
    private ObservableList<Trajeto> destino = FXCollections.observableArrayList();
    private ObservableList<Voo> voosIda = FXCollections.observableArrayList();
    private ObservableList<Voo> voosVolta = FXCollections.observableArrayList();

    private double total = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbVolta.visibleProperty().bind(tvVolta.visibleProperty());
        cbOrigem.setItems(origem);
        cbDestino.setItems(destino);
        tvIda.setItems(voosIda);
        tvVolta.setItems(voosVolta);
        spNumero.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (getValue() - steps > 0) {
                    setValue(getValue() - steps);
                }
            }

            @Override
            public void increment(int steps) {
                setValue(getValue() + steps);
            }
        });
        spNumero.getValueFactory().setValue(1);
        origem.setAll(new AeroportoDAO().pegarTodos());
        tcEmpresaIda.setCellValueFactory((TableColumn.CellDataFeatures<Voo, Empresa> param) -> new SimpleObjectProperty<>(param.getValue().getAviao().getCompanhia().getEmpresa()));
        tcPartidaIda.setCellValueFactory((TableColumn.CellDataFeatures<Voo, String> param) -> new SimpleStringProperty(DateFormatter.toDate(param.getValue().getDatapartida()) + " " + DateFormatter.toHour(param.getValue().getHorapartida())));
        tcChegadaIda.setCellValueFactory((TableColumn.CellDataFeatures<Voo, String> param) -> new SimpleStringProperty(DateFormatter.toDate(param.getValue().getDatachegada()) + " " + DateFormatter.toHour(param.getValue().getHorachegada())));
        tcPrecoIda.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tcPrecoIda.setCellFactory((TableColumn<Voo, Double> param) -> new TableCell<Voo, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(NumberFormatter.toMoney(item));
                }
            }

        });
        tcEmpresaVolta.setCellValueFactory((TableColumn.CellDataFeatures<Voo, Empresa> param) -> new SimpleObjectProperty<>(param.getValue().getAviao().getCompanhia().getEmpresa()));
        tcPartidaVolta.setCellValueFactory((TableColumn.CellDataFeatures<Voo, String> param) -> new SimpleStringProperty(DateFormatter.toDate(param.getValue().getDatapartida()) + " " + DateFormatter.toHour(param.getValue().getHorapartida())));
        tcChegadaVolta.setCellValueFactory((TableColumn.CellDataFeatures<Voo, String> param) -> new SimpleStringProperty(DateFormatter.toDate(param.getValue().getDatachegada()) + " " + DateFormatter.toHour(param.getValue().getHorachegada())));
        tcPrecoVolta.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tcPrecoVolta.setCellFactory((TableColumn<Voo, Double> param) -> new TableCell<Voo, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(NumberFormatter.toMoney(item));
                }
            }

        });
        spNumero.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            eCarregarTabelasActionEvent(null);
            tvTabelasMouseReleased(null);
        });
        tvTabelasMouseReleased(null);
    }

    @FXML
    private void rbTipoActionEvent(ActionEvent actionEvent) {
        tvVolta.setVisible(rbIdaVolta.isSelected());
        dpVolta.setDisable(rbApenasIda.isSelected());
        tvTabelasMouseReleased(null);
    }

    @FXML
    private void cbOrigemActionEvent(ActionEvent actionEvent) {
        if (cbOrigem.getSelectionModel().getSelectedItem() != null) {
            destino.setAll(new TrajetoDAO().pegarPorOrigem(cbOrigem.getSelectionModel().getSelectedItem()));
        } else {
            destino.clear();
        }
    }

    @FXML
    private void eCarregarTabelasActionEvent(ActionEvent actionEvent) {
        if (cbDestino.getSelectionModel().getSelectedItem() != null) {
            Trajeto trajetoIda = cbDestino.getSelectionModel().getSelectedItem();
            if (dpIda.getValue() != null) {
                voosIda.setAll(new VooDAO().pegarPorTrajeto(trajetoIda, DateFormatter.toDate(dpIda.getValue())));
            } else {
                voosIda.setAll(new VooDAO().pegarPorTrajeto(trajetoIda));
            }
            voosIda.removeIf((Voo t) -> new PassagemDAO().pegarPorVoo(t).stream().mapToInt(Passagem::getNumeropessoas).sum() + spNumero.getValue() >= t.getAviao().getCapacidade());
            Trajeto trajetoVolta = new TrajetoDAO().pegarPorOrigemDestino(trajetoIda.getDestino(), trajetoIda.getOrigem());
            if (trajetoVolta != null) {
                if (dpVolta.getValue() != null) {
                    voosVolta.setAll(new VooDAO().pegarPorTrajeto(trajetoIda, DateFormatter.toDate(dpIda.getValue())));
                } else {
                    voosVolta.setAll(new VooDAO().pegarPorTrajeto(trajetoIda));
                }
                voosVolta.removeIf((Voo t) -> new PassagemDAO().pegarPorVoo(t).stream().mapToInt(Passagem::getNumeropessoas).sum() + spNumero.getValue() >= t.getAviao().getCapacidade());
            } else {
                voosVolta.clear();
            }
        } else {
            voosIda.clear();
            voosVolta.clear();
        }
    }

    @FXML
    private void tvTabelasMouseReleased(MouseEvent mouseEvent) {
        double subtotal = 0;
        double taxaEmbarque = 0;
        Voo vooIda = tvIda.getSelectionModel().getSelectedItem();
        if (vooIda != null) {
            taxaEmbarque += vooIda.getTrajeto().getOrigem().getTaxaembarque() * spNumero.getValue();
            subtotal += vooIda.getPreco() * spNumero.getValue();
        }
        Voo vooVolta = tvVolta.getSelectionModel().getSelectedItem();
        if (vooVolta != null && rbIdaVolta.isSelected()) {
            taxaEmbarque += vooVolta.getTrajeto().getOrigem().getTaxaembarque() * spNumero.getValue();
            subtotal += vooVolta.getPreco() * spNumero.getValue();
        }
        lbPreco.setText(
                ControlTranducao.traduzirPalavra("subtotal") + ": " + NumberFormatter.toMoney(subtotal) + "\n"
                + ControlTranducao.traduzirPalavra("taxaembarque") + ": " + NumberFormatter.toMoney(taxaEmbarque) + "\n"
                + ControlTranducao.traduzirPalavra("total") + ": " + NumberFormatter.toMoney(taxaEmbarque + subtotal) + "");
        this.total = taxaEmbarque + subtotal;
    }

    @FXML
    private void btComprarActionEvent(ActionEvent actionEvent) {
        Voo vooIda = tvIda.getSelectionModel().getSelectedItem();
        Voo vooVolta = tvVolta.getSelectionModel().getSelectedItem();
        if ((rbIdaVolta.isSelected() && vooIda != null && vooVolta != null) || (rbApenasIda.isSelected() && vooIda != null)) {
            Passagem passagem = new Passagem();
            passagem.setVooida(vooIda);
            passagem.setVoovolta(vooVolta);
            passagem.setData(new Date());
            passagem.setNumeropessoas(spNumero.getValue());
            passagem.setTotal(total);
            new PassagemDAO().cadastrar(passagem);
            Message.show("", ControlTranducao.traduzirPalavra("EVPASSAGEMCOMPRADA"), Alert.AlertType.INFORMATION);
            FxMananger.inserirPainel((ScrollPane) apPrincipal.getParent().getParent().getParent(), FxMananger.carregarComponente("ComprarPassagem"));
        } else {
            Message.show("Selecione voos", "É necessário selecionar o(s) voo(s)", Alert.AlertType.ERROR);
        }
    }
}
