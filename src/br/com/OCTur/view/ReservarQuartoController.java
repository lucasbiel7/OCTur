/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CidadeDAO;
import br.com.OCTur.control.DAO.HotelDAO;
import br.com.OCTur.control.DAO.OrcamentoDAO;
import br.com.OCTur.control.DAO.PaisDAO;
import br.com.OCTur.control.DAO.QuartoDAO;
import br.com.OCTur.control.DAO.ReservaDAO;
import br.com.OCTur.control.DAO.TipoQuartoDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Orcamento;
import br.com.OCTur.model.entity.Pais;
import br.com.OCTur.model.entity.Quarto;
import br.com.OCTur.model.entity.Reserva;
import br.com.OCTur.model.entity.TipoQuarto;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ReservarQuartoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private TableView<Hotel> tvHotel;
    @FXML
    private TableColumn<Hotel, String> tcHotel;
    @FXML
    private ComboBox<Cidade> cbCidade;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private TextField tfDe;
    @FXML
    private TextField tfPara;
    @FXML
    private AnchorPane apDetalhe;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescricao;
    @FXML
    private ComboBox<TipoQuarto> cbTipoQuarto;
    @FXML
    private Label lbCaracteristica;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Button btReservar;

    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Hotel> hoteis = FXCollections.observableArrayList();
    private ObservableList<Cidade> cidades = FXCollections.observableArrayList();
    private ObservableList<TipoQuarto> tipoQuartos = FXCollections.observableArrayList();

    private Hotel hotel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btReservar, new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_ANY)));
        });
        apDetalhe.setVisible(false);
        cbCidade.setItems(cidades);
        cbPais.setItems(paises);
        cbTipoQuarto.setItems(tipoQuartos);
        tvHotel.setItems(hoteis);
        paises.setAll(new PaisDAO().pegarTodos());
        hoteis.setAll(new HotelDAO().pegarTodos());
        tcHotel.setCellValueFactory((TableColumn.CellDataFeatures<Hotel, String> param) -> new SimpleStringProperty(param.getValue().getEmpresa().getNome()));
    }

    @FXML
    private void cbPaisActionEvent(ActionEvent actionEvent) {
        if (cbPais.getSelectionModel().getSelectedItem() != null) {
            cidades.setAll(new CidadeDAO().pegarPorPais(cbPais.getSelectionModel().getSelectedItem()));
            cbCidade.getSelectionModel().clearSelection();
        } else {
            cidades.clear();
        }
    }

    @FXML
    private void tvHotelMouseReleased(MouseEvent mouseEvent) {
        if (tvHotel.getSelectionModel().getSelectedItem() != null) {
            hotel = tvHotel.getSelectionModel().getSelectedItem();
            lbTitulo.setText(hotel.getEmpresa().getNome());
            lbCaracteristica.setText("Endereço: " + hotel.getEndereco() + "\n"
                    + "Telefone: " + hotel.getTelefone() + "\n");
            tipoQuartos.setAll(new TipoQuartoDAO().pegarTodos());
            tipoQuartos.removeIf((TipoQuarto t) -> (new OrcamentoDAO().pegarPorTipoQuartoHotel(t, hotel) == null));
            if (hotel.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(hotel.getFoto())));
            } else {
                ivFoto.setImage(null);
            }
            apDetalhe.setVisible(true);
        } else {
            apDetalhe.setVisible(false);
        }
    }

    @FXML
    private void cbTipoQuartoActionEvent(ActionEvent actionEvent) {
        if (cbTipoQuarto.getSelectionModel().getSelectedItem() != null) {
            lbDescricao.setText(cbTipoQuarto.getSelectionModel().getSelectedItem().getDescricao());
        } else {
            lbDescricao.setText("");
        }
    }

    @FXML
    private void btPesquisarActionEvent(ActionEvent actionEvent) {
        if (cbCidade.getSelectionModel().getSelectedItem() != null) {
            hoteis.setAll(new HotelDAO().pegarPorCidade(cbCidade.getSelectionModel().getSelectedItem()));
        } else {
            hoteis.setAll(new HotelDAO().pegarTodos());
        }
    }

    @FXML
    private void btReservaActionEvent(ActionEvent actionEvent) {
        Reserva reserva = new Reserva();
        reserva.setInicio(DateFormatter.toDate(dpInicio.getValue()));
        reserva.setFim(DateFormatter.toDate(dpFim.getValue()));
        reserva.setPessoa(Sessao.pessoa);
        if (reserva.getInicio() != null && reserva.getFim() != null) {
            Orcamento orcamento = new OrcamentoDAO().pegarPorTipoQuartoHotel(cbTipoQuarto.getSelectionModel().getSelectedItem(), hotel);
            if (orcamento != null) {
                for (Quarto quarto : new QuartoDAO().pegarPorOrcamento(orcamento)) {
                    if (new ReservaDAO().pegarPorQuartoInicioFim(quarto, reserva.getInicio(), reserva.getFim()).isEmpty()) {
                        reserva.setQuarto(quarto);
                        break;
                    }
                }
            }
            if (reserva.getQuarto() != null) {
                new ReservaDAO().cadastrar(reserva);
                Message.show("", ControlTranducao.traduzirPalavra("EVQUARTORESERVADO"), Alert.AlertType.INFORMATION);
                btLimparActionEvent(actionEvent);
            } else {
                Message.show("", "Não foi possível realizar a reserva porque não há mais quartos livre para esse Tipo de Quarto/Hotel", Alert.AlertType.INFORMATION);
            }
        } else {
            Message.show("", "Selecione as datas", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void btLimparActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel((ScrollPane) apPrincipal.getParent().getParent().getParent(), FxMananger.carregarComponente("ReservarQuarto"));
    }
}
