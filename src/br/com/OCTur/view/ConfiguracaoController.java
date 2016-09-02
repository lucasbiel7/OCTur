/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.FornecedorDAO;
import br.com.OCTur.control.DAO.HotelDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlPermissao;
import br.com.OCTur.control.util.Sessao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ConfiguracaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;
    @FXML
    private Button btEmpresa;
    @FXML
    private Button btPermissao;
    @FXML
    private Button btGrafico;
    @FXML
    private Button btInfografico;
    @FXML
    private Button btTrajeto;
    @FXML
    private Button btVoos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btVoos.setDisable(!ControlPermissao.isValido("PERMISSAOGERENCIARVOOS"));
        btTrajeto.setDisable(!ControlPermissao.isValido("PERMISSAOGERENCIARTRAJETOS"));
        btInfografico.setDisable(new HotelDAO().pegarPorEmpresa(Sessao.pessoa.getEmpresa()) == null);
        btGrafico.setDisable(new FornecedorDAO().pegarPorEmpresa(Sessao.pessoa.getEmpresa()) == null);
        btPermissao.setDisable(!ControlPermissao.isValido("ADMINISTRADOR"));
        btEmpresa.setDisable(!ControlPermissao.isValido("PERMISSAOGERENCIAREMPRESA"));
    }

    @FXML
    private void btDadosPessoaisActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("EditarUsuario"));
    }

    @FXML
    private void btPermissaoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarPermissao"));
    }

    @FXML
    private void btEmpresaActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarEmpresa"));
    }

    @FXML
    private void btInfograficoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Infografico"));
    }

    @FXML
    private void btGraficoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Grafico"));
    }

    @FXML
    private void btAnaliseActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Analise"));
    }

    @FXML
    private void btGerenciarVoosActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarVoos"));
    }

    @FXML
    private void btGerenciarTrajetoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarTrajeto"));
    }

}
