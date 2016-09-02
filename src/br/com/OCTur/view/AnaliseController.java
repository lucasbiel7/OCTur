/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlPermissao;
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
public class AnaliseController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private ScrollPane spContainer;
    @FXML
    private Button btContigente;
    @FXML
    private Button btClassificacao;
    @FXML
    private Button btAtividade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btContigente.setDisable(!ControlPermissao.isValido("PERMISSAOGERENCIARVOOS"));
        btAtividade.setDisable(!ControlPermissao.isValido("PERMISSAOGERENCIARVOOS"));
    }

    @FXML
    private void btContigenteActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Contigente"));
    }

    @FXML
    private void btClassificacaoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Classificacao"));
    }

    @FXML
    private void btAtividadesActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Atividades"));
    }

}
