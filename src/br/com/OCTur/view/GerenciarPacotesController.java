/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CidadeDAO;
import br.com.OCTur.control.DAO.HotelDAO;
import br.com.OCTur.control.DAO.OrcamentoDAO;
import br.com.OCTur.control.DAO.PacoteDAO;
import br.com.OCTur.control.DAO.PaisDAO;
import br.com.OCTur.control.DAO.TipoQuartoDAO;
import br.com.OCTur.control.DAO.TrajetoDAO;
import br.com.OCTur.control.DAO.VooDAO;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import static br.com.OCTur.control.util.DateFormatter.toDate;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.model.entity.Aviao;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Empresa;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Orcamento;
import br.com.OCTur.model.entity.Pacote;
import br.com.OCTur.model.entity.Pais;
import br.com.OCTur.model.entity.TipoQuarto;
import br.com.OCTur.model.entity.Trajeto;
import br.com.OCTur.model.entity.Voo;
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

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarPacotesController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Pais> cbPaisOrigem;
    @FXML
    private ComboBox<Pais> cbPaisDestino;
    @FXML
    private ComboBox<Cidade> cbOrigem;
    @FXML
    private ComboBox<Cidade> cbDestino;
    @FXML
    private ComboBox<TipoQuarto> cbTipoQuarto;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private TableView<Voo> tvVoos;
    @FXML
    private TableView<Hotel> tvHotel;
    @FXML
    private TableColumn<Voo, Aviao> tcVooNome;
    @FXML
    private TableColumn<Hotel, Empresa> tcHotelNome;
    @FXML
    private TextField tfPreco;
    @FXML
    private TableView<Object> tvServicosAdicionados;
    @FXML
    private TableColumn<Object, String> tcNomeServicos;
    @FXML
    private Label lbTotal;

    @FXML
    private Button btAdicionar;
    @FXML
    private Button btRemover;
    @FXML
    private Button btSalvar;
    private double total;
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Cidade> origem = FXCollections.observableArrayList();
    private ObservableList<Cidade> destino = FXCollections.observableArrayList();
    private ObservableList<Voo> voos = FXCollections.observableArrayList();
    private ObservableList<Hotel> hoteis = FXCollections.observableArrayList();
    private ObservableList<Object> servicos = FXCollections.observableArrayList();
    private ObservableList<TipoQuarto> tipoQuartos = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btSalvar, new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_ANY)));
        });
        cbPaisDestino.setItems(paises);
        cbPaisOrigem.setItems(paises);
        cbOrigem.setItems(origem);
        cbDestino.setItems(destino);
        tvVoos.setItems(voos);
        tvHotel.setItems(hoteis);
        tvServicosAdicionados.setItems(servicos);
        cbTipoQuarto.setItems(tipoQuartos);
        paises.setAll(new PaisDAO().pegarTodos());
        voos.setAll(new VooDAO().pegarTodos());
        hoteis.setAll(new HotelDAO().pegarTodos());
        tcHotelNome.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        tcVooNome.setCellValueFactory(new PropertyValueFactory<>("aviao"));
        tcNomeServicos.setCellValueFactory((TableColumn.CellDataFeatures<Object, String> param) -> {
            if (param.getValue() instanceof Hotel) {
                Hotel hotel = (Hotel) param.getValue();
                return new SimpleStringProperty(hotel.getEmpresa().toString());
            }
            if (param.getValue() instanceof Voo) {
                Voo voo = (Voo) param.getValue();
                return new SimpleStringProperty(voo.getAviao().toString());
            }
            return new SimpleStringProperty("Objeto nao identificado");
        });
        calcularPreco();
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
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        if (tvHotel.getSelectionModel().getSelectedItem() != null) {
            Hotel hotel = tvHotel.getSelectionModel().getSelectedItem();
            servicos.add(hotel);
            tvHotel.setDisable(true);
            tvHotel.getSelectionModel().clearSelection();
        }
        if (tvVoos.getSelectionModel().getSelectedItem() != null) {
            Voo voo = tvVoos.getSelectionModel().getSelectedItem();
            servicos.add(voo);
            tvVoos.setDisable(true);
            tvVoos.getSelectionModel().clearSelection();
        }
        calcularPreco();
        btAdicionar.setDisable(true);
    }

    @FXML
    private void btRemoverActionEvent(ActionEvent actionEvent) {
        if (tvServicosAdicionados.getSelectionModel().getSelectedItem() != null) {
            Object objeto = tvServicosAdicionados.getSelectionModel().getSelectedItem();
            servicos.remove(objeto);
            if (objeto instanceof Hotel) {
                tvHotel.setDisable(false);
            }
            if (objeto instanceof Voo) {
                tvVoos.setDisable(false);
            }
        }
        tvServicosAdicionados.getSelectionModel().clearSelection();
        calcularPreco();
        tvServicosMouseReleased(null);
    }

    @FXML
    private void tvEscolhaMouseReleased(MouseEvent mouseEvent) {
        btAdicionar.setDisable(tvHotel.getSelectionModel().getSelectedItem() == null && tvVoos.getSelectionModel().getSelectedItem() == null);
        tipoQuartos.clear();
        if (tvHotel.getSelectionModel().getSelectedItem() != null) {
            Hotel hotel = tvHotel.getSelectionModel().getSelectedItem();
            for (TipoQuarto tipoQuarto : new TipoQuartoDAO().pegarTodos()) {
                if (new OrcamentoDAO().pegarPorTipoQuartoHotel(tipoQuarto, hotel) != null) {
                    tipoQuartos.add(tipoQuarto);
                }
            }
        }
        cbTipoQuarto.getSelectionModel().selectFirst();
    }

    @FXML
    private void tvServicosMouseReleased(MouseEvent mouseEvent) {
        btRemover.setDisable(tvServicosAdicionados.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        Pacote pacote = new Pacote();
        pacote.setFim(toDate(dpFim.getValue()));
        pacote.setInicio(toDate(dpInicio.getValue()));
        pacote.setTipoQuarto(cbTipoQuarto.getSelectionModel().getSelectedItem());
        pacote.setPreco(Double.parseDouble(tfPreco.getText().replace(",", ".").replace("R$", "").replace(" ", "")));
        for (Object servico : servicos) {
            if (servico instanceof Hotel) {
                pacote.setHotel((Hotel) servico);
            }
            if (servico instanceof Voo) {
                pacote.setVoo((Voo) servico);
            }
        }
        if (pacote.getInicio() != null && pacote.getFim() != null && (pacote.getVoo() != null || pacote.getHotel() != null)) {
            new PacoteDAO().cadastrar(pacote);
            Message.show("", "Pacote foi adicionado com sucesso", Alert.AlertType.INFORMATION);
            btLimparActionEvent(actionEvent);
        } else {
            Message.show("", "É necessário seleciona as datas e um serviço no minimo!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btLimparActionEvent(ActionEvent actionEvent) {
        cbPaisOrigem.getSelectionModel().clearSelection();
        cbPaisDestino.getSelectionModel().clearSelection();
        cbOrigem.getSelectionModel().clearSelection();
        cbDestino.getSelectionModel().clearSelection();
        dpInicio.setValue(null);
        dpFim.setValue(null);
        servicos.clear();
        tvVoos.setDisable(false);
        tvHotel.setDisable(false);
        tipoQuartos.clear();
        calcularPreco();
    }

    @FXML
    private void btPesquisarActionEvent(ActionEvent actionEvent
    ) {
        if (cbDestino.getSelectionModel().getSelectedItem() != null) {
            hoteis.setAll(new HotelDAO().pegarPorCidade(cbDestino.getSelectionModel().getSelectedItem()));
            Trajeto trajeto = new TrajetoDAO().pegarPorOrigemDestino(cbOrigem.getSelectionModel().getSelectedItem(), cbDestino.getSelectionModel().getSelectedItem());
            voos.setAll(new VooDAO().pegarPorTrajeto(trajeto));
        } else {
            hoteis.setAll(new HotelDAO().pegarTodos());
            voos.setAll(new VooDAO().pegarTodos());
        }
    }

    @FXML
    private void calcularPreco() {
        total = 0;
        for (Object servico : servicos) {
            if (servico instanceof Hotel) {
                Hotel hotel = (Hotel) servico;
                if (cbTipoQuarto.getSelectionModel().getSelectedItem() != null) {
                    TipoQuarto tipoQuarto = cbTipoQuarto.getSelectionModel().getSelectedItem();
                    Orcamento orcamento = new OrcamentoDAO().pegarPorTipoQuartoHotel(tipoQuarto, hotel);
                    if (orcamento != null) {
                        long dias = 1;
                        if (dpInicio.getValue() != null && dpFim.getValue() != null) {
                            dias = (DateFormatter.toDate(dpFim.getValue()).getTime() - DateFormatter.toDate(dpInicio.getValue()).getTime()) / 1000 / 60 / 60 / 24;
                        }
                        total += (orcamento.getPreco() * dias);
                    }
                }
            }
            if (servico instanceof Voo) {
                Voo voo = (Voo) servico;
                total += voo.getPreco() + voo.getTrajeto().getOrigem().getTaxaembarque();
            }
        }
        lbTotal.setText(ControlTranducao.traduzirPalavra("total") + ": " + NumberFormatter.toMoney(total));
        tfPreco.setText(NumberFormatter.toMoney(total));
    }
}
