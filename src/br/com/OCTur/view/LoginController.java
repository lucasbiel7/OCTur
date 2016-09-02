/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.IdiomaDAO;
import br.com.OCTur.control.DAO.PessoaDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.IdiomaCellFactory;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.entity.Idioma;
import br.com.OCTur.model.entity.Pessoa;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private ComboBox<Idioma> cbIdioma;
    @FXML
    private ImageView ivFotos;

    private ObservableList<Idioma> idioma = FXCollections.observableArrayList();
    private List<Image> images;
    private int image = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControlTranducao.traduzirComponentes(apPrincipal.getChildren());
        cbIdioma.setItems(idioma);
        idioma.setAll(new IdiomaDAO().pegarTodos());
        cbIdioma.setCellFactory(new IdiomaCellFactory());
        cbIdioma.getSelectionModel().selectFirst();
        Sessao.idioma = cbIdioma.getSelectionModel().getSelectedItem();
        ControlTranducao.traduzirComponentes(apPrincipal.getChildren());
        images = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            images.add(new Image(getClass().getResourceAsStream(FxMananger.VIEW + "image/turismo" + i + ".jpg")));
        }
        ivFotos.setImage(images.get(0));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), (ActionEvent ae) -> {
            ivFotos.setImage(images.get(image));
            image++;
            if (image >= images.size()) {
                image = 0;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void btLoginActionEvent(ActionEvent actionEvent) {
        if (tfUsuario.getText().matches("^((\\w|\\.|@|-){1,})$")) {
            Pessoa pessoa = new PessoaDAO().pegarPorUsuario(tfUsuario.getText());
            if (pessoa != null) {
                if (pessoa.getSenha().equals(pfSenha.getText())) {
                    Sessao.pessoa = pessoa;
                    Sessao.idioma = pessoa.getIdioma();
                    ((Stage) apPrincipal.getScene().getWindow()).close();
                    FxMananger.showWindow(FxMananger.carregarComponente("Principal"), "Inicio", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.EXIT_ON_CLOSE).show();
                } else {
                    Message.show("Error", ControlTranducao.traduzirPalavra("eloginfalhou"), Alert.AlertType.ERROR);
                }
            } else {
                Message.show("Error", ControlTranducao.traduzirPalavra("eusuarioinexistente"), Alert.AlertType.ERROR);
            }
        } else {
            Message.show("Error", ControlTranducao.traduzirPalavra("eusuarioinvalido"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btNovoUsuarioActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("CadastrarUsuario"), "", FxMananger.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void cbIdiomaActionEvent(ActionEvent actionEvent) {
        Sessao.idioma = cbIdioma.getSelectionModel().getSelectedItem();
        ControlTranducao.traduzirComponentes(apPrincipal.getChildren());
    }
}
