/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.PapelDAO;
import br.com.OCTur.control.DAO.PermissaoDAO;
import br.com.OCTur.control.DAO.TipoPermissaoDAO;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.model.entity.Papel;
import br.com.OCTur.model.entity.Permissao;
import br.com.OCTur.model.entity.TipoPermissao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
public class GerenciarPermissaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Papel> cbPapel;
    @FXML
    private TableView<Permissao> tvPermissao;
    @FXML
    private TableColumn<Permissao, TipoPermissao> tcNome;
    @FXML
    private TableColumn<Permissao, Permissao> tcHabilitado;
    private ObservableList<Papel> papeis = FXCollections.observableArrayList();
    private ObservableList<Permissao> permissoes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbPapel.setPromptText(ControlTranducao.traduzirPalavra("NENHUM"));
        cbPapel.setItems(papeis);
        papeis.setAll(new PapelDAO().pegarTodos());
        tvPermissao.setItems(permissoes);
        new Thread(() -> {
            for (Papel papel : papeis) {
                for (TipoPermissao tipoPermissao : new TipoPermissaoDAO().pegarTodos()) {
                    Permissao permissao = new PermissaoDAO().pegarPorPapelTipoPermissao(papel, tipoPermissao);
                    if (permissao == null) {
                        permissao = new Permissao();
                        permissao.setHabilitado(false);
                        permissao.setPapel(papel);
                        permissao.setTipopermissao(tipoPermissao);
                        new PermissaoDAO().cadastrar(permissao);
                    }
                }
            }
        }).start();
        tcNome.setCellValueFactory(new PropertyValueFactory<>("tipopermissao"));
        tcHabilitado.setCellValueFactory((TableColumn.CellDataFeatures<Permissao, Permissao> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcHabilitado.setCellFactory((TableColumn<Permissao, Permissao> param) -> new TableCell<Permissao, Permissao>() {
            @Override
            protected void updateItem(Permissao item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox cbHabilitado = new CheckBox();
                    cbHabilitado.setSelected(item.isHabilitado());
                    cbHabilitado.setOnAction((ActionEvent) -> {
                        item.setHabilitado(cbHabilitado.isSelected());
                        new PermissaoDAO().editar(item);
                    });
                    setGraphic(cbHabilitado);
                }
            }
        });
    }

    @FXML
    private void cbPapelActionEvent(ActionEvent actionEvent) {
        if (cbPapel.getSelectionModel().getSelectedItem() != null) {
            permissoes.setAll(new PermissaoDAO().pegarPorPapel(cbPapel.getSelectionModel().getSelectedItem()));
        } else {
            permissoes.clear();
        }
    }
}
