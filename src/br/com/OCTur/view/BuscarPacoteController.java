/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CidadeDAO;
import br.com.OCTur.control.DAO.PacoteDAO;
import br.com.OCTur.control.DAO.PaisDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Pacote;
import br.com.OCTur.model.entity.Pais;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class BuscarPacoteController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private ComboBox<Cidade> cbCidade;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private RadioButton rbE;
    @FXML
    private RadioButton rbOu;
    @FXML
    private GridPane gpPacotes;

    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Cidade> cidades = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarPacotes(new PacoteDAO().pegarTodos());
        cbPais.setItems(paises);
        cbCidade.setItems(cidades);
        paises.setAll(new PaisDAO().pegarTodos());

    }

    @FXML
    private void cbPaisActionEvent(ActionEvent actionEvent) {
        if (cbPais.getSelectionModel().getSelectedItem() != null) {
            cidades.setAll(new CidadeDAO().pegarPorPais(cbPais.getSelectionModel().getSelectedItem()));
        } else {
            cbCidade.getSelectionModel().clearSelection();
            cidades.clear();
        }
    }

    @FXML
    private void btPesquisarActionEvent(ActionEvent actionEvent) {
        List<Pacote> pacotes;
        Date inicio = DateFormatter.toDate(dpInicio.getValue());
        Date fim = DateFormatter.toDate(dpFim.getValue());
        if (cbCidade.getSelectionModel().getSelectedItem() != null) {
            if (inicio != null && fim != null) {
                pacotes = new PacoteDAO().pegarPorCidade(cbCidade.getSelectionModel().getSelectedItem(), inicio, fim, rbE.isSelected());
            } else if (inicio != null) {
                pacotes = new PacoteDAO().pegarPorCidadeInicio(cbCidade.getSelectionModel().getSelectedItem(), inicio);
            } else if (fim != null) {
                pacotes = new PacoteDAO().pegarPorCidadeFim(cbCidade.getSelectionModel().getSelectedItem(), fim);
            } else {
                pacotes = new PacoteDAO().pegarPorCidade(cbCidade.getSelectionModel().getSelectedItem());
            }
        } else if (inicio != null && fim != null) {
            pacotes = new PacoteDAO().pegarPorInicioFim(inicio, fim, rbE.isSelected());
        } else if (inicio != null) {
            pacotes = new PacoteDAO().pegarPorInicio(inicio);
        } else if (fim != null) {
            pacotes = new PacoteDAO().pegarPorFim(fim);
        } else {
            pacotes = new PacoteDAO().pegarTodos();
        }
        carregarPacotes(pacotes);
    }

    public void carregarPacotes(List<Pacote> pacotes) {
        gpPacotes.getChildren().clear();
        int linha = 0;
        int coluna = 0;
        for (Pacote pacote : pacotes) {
            Parent parent = FxMananger.carregarComponente("VisualizarPacote", pacote);
            parent.setOnMouseReleased((MouseEvent event) -> {
                if (Message.showConfirm("Comprar pacote", "Você realmente deseja comprar o pacote?\n"
                        + "Valor: " + NumberFormatter.toMoney(pacote.getPreco()))) {
                    new PacoteDAO().excluir(pacote);
                    Message.show("", ControlTranducao.traduzirPalavra("EVPACOTECOMPRADO"), Alert.AlertType.INFORMATION);
                    btPesquisarActionEvent(null);
                }
            });
            gpPacotes.add(parent, coluna, linha);
            coluna++;
            if (coluna > 3) {
                coluna = 0;
                linha++;
            }
        }
    }
}
