/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.AeroportoDAO;
import br.com.OCTur.control.DAO.PaisDAO;
import br.com.OCTur.control.DAO.TrajetoDAO;
import br.com.OCTur.control.DAO.VooDAO;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Pais;
import br.com.OCTur.model.entity.Trajeto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarTrajetoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private ComboBox<Aeroporto> cbOrigem;
    @FXML
    private TableView<Aeroporto> tvDestino;
    @FXML
    private TableColumn<Aeroporto, Aeroporto> tcTrajeto;
    @FXML
    private TableColumn<Aeroporto, String> tcNome;
    @FXML
    private RadioButton rbTodos;
    @FXML
    private RadioButton rbApenasMarcados;
    @FXML
    private ComboBox<Pais> cbPais;

    private ObservableList<Aeroporto> origem = FXCollections.observableArrayList();
    private ObservableList<Aeroporto> destino = FXCollections.observableArrayList();
    private ObservableList<Pais> pais = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbOrigem.setItems(origem);
        tvDestino.setItems(destino);
        cbPais.setItems(pais);
        origem.setAll(new AeroportoDAO().pegarTodos());
        pais.setAll(new PaisDAO().pegarTodos());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTrajeto.setCellValueFactory((TableColumn.CellDataFeatures<Aeroporto, Aeroporto> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcTrajeto.setCellFactory((TableColumn<Aeroporto, Aeroporto> param) -> new TableCell<Aeroporto, Aeroporto>() {
            @Override
            protected void updateItem(Aeroporto item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                    setText("");
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(new TrajetoDAO().pegarPorOrigemDestino(cbOrigem.getSelectionModel().getSelectedItem(), item) != null);
                    checkBox.setOnAction((ActionEvent ae) -> {
                        Trajeto trajeto = new TrajetoDAO().pegarPorOrigemDestino(cbOrigem.getSelectionModel().getSelectedItem(), item);
                        if (trajeto == null) {
                            trajeto = new Trajeto();
                            trajeto.setOrigem(cbOrigem.getSelectionModel().getSelectedItem());
                            trajeto.setDestino(item);
                            new TrajetoDAO().cadastrar(trajeto);
                        } else if (new VooDAO().pegarPorTrajeto(trajeto).isEmpty()) {
                            new TrajetoDAO().excluir(trajeto);
                        } else if (Message.showConfirm("", ControlTranducao.traduzirPalavra("EREMOVERTRAJETOVOOSEXISTEM"))) {
                            new TrajetoDAO().excluir(trajeto);
                        }
                        cbFiltroActionEvent(ae);
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }

    @FXML
    private void cbFiltroActionEvent(ActionEvent actionEvent) {
        if (cbOrigem.getSelectionModel().getSelectedItem() != null) {
            List<Aeroporto> destino;
            if (cbPais.getSelectionModel().getSelectedItem() == null) {
                destino = new AeroportoDAO().pegarTodos();
            } else {
                destino = new AeroportoDAO().pegarPorPais(cbPais.getSelectionModel().getSelectedItem());
            }
            if (rbApenasMarcados.isSelected()) {
                destino.removeIf((Aeroporto t) -> new TrajetoDAO().pegarPorOrigemDestino(cbOrigem.getSelectionModel().getSelectedItem(), t) == null);
            }
            this.destino.setAll(destino);
        } else {
            destino.clear();
        }
    }

}
