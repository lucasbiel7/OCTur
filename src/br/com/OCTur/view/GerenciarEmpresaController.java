/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.EmpresaDAO;
import br.com.OCTur.control.DAO.PapelDAO;
import br.com.OCTur.control.DAO.PessoaDAO;
import br.com.OCTur.control.util.ControlPermissao;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.entity.Empresa;
import br.com.OCTur.model.entity.Papel;
import br.com.OCTur.model.entity.Pessoa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class GerenciarEmpresaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Empresa> cbEmpresa;
    @FXML
    private TableView<Pessoa> tvFuncionarios;
    @FXML
    private TableColumn<Pessoa, String> tcNome;
    @FXML
    private TableColumn<Pessoa, Papel> tcPapel;
    @FXML
    private TableView<Pessoa> tvNaoFuncionarios;
    @FXML
    private TableColumn<Pessoa, String> tcNomeNaoFuncionario;
    @FXML
    private Button btDesligar;
    @FXML
    private Button btLigar;
    @FXML
    private ComboBox<Papel> cbPapel;

    private ObservableList<Empresa> empresas = FXCollections.observableArrayList();
    private ObservableList<Pessoa> funcionarios = FXCollections.observableArrayList();
    private ObservableList<Pessoa> naoFuncionarios = FXCollections.observableArrayList();
    private ObservableList<Papel> papeis = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEmpresa.setPromptText(ControlTranducao.traduzirPalavra("Todos"));
        cbEmpresa.setItems(empresas);
        tvFuncionarios.setItems(funcionarios);
        tvNaoFuncionarios.setItems(naoFuncionarios);
        cbEmpresa.setItems(empresas);
        cbPapel.setItems(papeis);
        //Carregando dados
        papeis.setAll(new PapelDAO().pegarTodos());
        empresas.setAll(new EmpresaDAO().pegarTodos());
        cbEmpresa.getSelectionModel().select(Sessao.pessoa.getEmpresa());
        funcionarios.setAll(new PessoaDAO().pegarPorEmpresa(cbEmpresa.getSelectionModel().getSelectedItem()));
        naoFuncionarios.setAll(new PessoaDAO().pegarSemEmpresa());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeNaoFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcPapel.setCellValueFactory(new PropertyValueFactory<>("papel"));
        //Permissao
        cbEmpresa.setDisable(!ControlPermissao.isValido("ADMINISTRADOR"));
    }

    @FXML
    private void cbEmpresaActionEvent(ActionEvent actionEvent) {
        if (cbEmpresa.getSelectionModel().getSelectedItem() != null) {
            funcionarios.setAll(new PessoaDAO().pegarPorEmpresa(cbEmpresa.getSelectionModel().getSelectedItem()));
            naoFuncionarios.setAll(new PessoaDAO().pegarSemEmpresa());
        } else {
            funcionarios.clear();
            naoFuncionarios.clear();
        }
    }

    @FXML
    private void tvFuncionarioMouseReleased(MouseEvent mouseEvent) {
        btLigar.setDisable(tvNaoFuncionarios.getSelectionModel().getSelectedItem() == null || cbPapel.getSelectionModel().getSelectedItem() == null);
        btDesligar.setDisable(tvFuncionarios.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void tvNaoFuncionarioMouseReleased(MouseEvent mouseEvent) {
        btLigar.setDisable(tvNaoFuncionarios.getSelectionModel().getSelectedItem() == null || cbPapel.getSelectionModel().getSelectedItem() == null);
        btDesligar.setDisable(tvFuncionarios.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void cbPapelActionEvent(ActionEvent actionEvent) {
        btLigar.setDisable(tvNaoFuncionarios.getSelectionModel().getSelectedItem() == null || cbPapel.getSelectionModel().getSelectedItem() == null);
        btDesligar.setDisable(tvFuncionarios.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void btLigarActionEvent(ActionEvent actionEvent) {
        Pessoa pessoa = tvNaoFuncionarios.getSelectionModel().getSelectedItem();
        pessoa.setEmpresa(cbEmpresa.getSelectionModel().getSelectedItem());
        pessoa.setPapel(cbPapel.getSelectionModel().getSelectedItem());
        new PessoaDAO().editar(pessoa);
        atualizar();

    }

    @FXML
    private void btDesligarActionEvent(ActionEvent actionEvent) {
        Pessoa pessoa = tvFuncionarios.getSelectionModel().getSelectedItem();
        pessoa.setEmpresa(null);
        pessoa.setPapel(null);
        new PessoaDAO().editar(pessoa);
        atualizar();
    }

    public void atualizar() {
        cbPapel.getSelectionModel().clearSelection();
        funcionarios.setAll(new PessoaDAO().pegarPorEmpresa(cbEmpresa.getSelectionModel().getSelectedItem()));
        naoFuncionarios.setAll(new PessoaDAO().pegarSemEmpresa());
        btLigar.setDisable(tvNaoFuncionarios.getSelectionModel().getSelectedItem() == null || cbPapel.getSelectionModel().getSelectedItem() == null);
        btDesligar.setDisable(tvFuncionarios.getSelectionModel().getSelectedItem() == null);
    }
}
