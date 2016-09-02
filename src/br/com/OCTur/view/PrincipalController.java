/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlPermissao;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.Sessao;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PrincipalController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbNome;
    @FXML
    private Label lbUltimaAutenticacao;
    @FXML
    private Button btConfiguracao;
    @FXML
    private Button btSair;
    @FXML
    private Button btComprarPassagem;

    @FXML
    private Button btComprarProdutos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btConfiguracao, new KeyCodeCombination(KeyCode.G, KeyCombination.ALT_ANY)));
            apPrincipal.getScene().addMnemonic(new Mnemonic(btComprarProdutos, new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_ANY)));
            apPrincipal.getScene().addMnemonic(new Mnemonic(btSair, new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_ANY)));
        });
        if (Sessao.pessoa.getFoto() != null) {
            ivFoto.setImage(new Image(new ByteArrayInputStream(Sessao.pessoa.getFoto())));
        } else {
            ivFoto.setImage(new Image(getClass().getResourceAsStream(FxMananger.VIEW + "image/padrao.jpg")));
        }
        btComprarPassagem.setDisable(!ControlPermissao.isValido("PERMISSAOGERENCIARVOOS"));

        lbNome.setText(Sessao.pessoa.getNome());
        lbUltimaAutenticacao.setText(ControlTranducao.traduzirPalavra("ULTIMAAUTENTICACAO") + ": " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    @FXML
    private void btConfiguracaoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("Configuracao"));
    }

    @FXML
    private void btSairActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
        FxMananger.showWindow(FxMananger.carregarComponente("Login"), "Login", FxMananger.Tipo.EXIT_ON_CLOSE).show();
    }

    @FXML
    private void btComprarPassagemActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("ComprarPassagem"));
    }

    @FXML
    private void btHotelActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("ReservarQuarto"));
    }

    @FXML
    private void btComprarProdutoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("ComprarProdutos"));
    }

    @FXML
    private void btBuscarEventoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("BuscarEventos"));
    }

    @FXML
    private void btPacotesActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("BuscarPacote"));
    }

    @FXML
    private void btGerenciarPacotesActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarPacotes"));
    }
}
