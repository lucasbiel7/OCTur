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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EditarUsuarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNome;
    @FXML
    private DatePicker dpDataNascimento;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private ComboBox<Idioma> cbIdioma;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Button btCadastrar;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btApagar;

    private FileChooser fcImagem;

    private Pessoa pessoa;

    private ObservableList<Idioma> idiomas = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btCadastrar, new KeyCodeCombination(KeyCode.S, KeyCodeCombination.ALT_ANY)));
            apPrincipal.getScene().addMnemonic(new Mnemonic(btCancelar, new KeyCodeCombination(KeyCode.E, KeyCodeCombination.ALT_ANY)));
            apPrincipal.getScene().addMnemonic(new Mnemonic(btApagar, new KeyCodeCombination(KeyCode.A, KeyCodeCombination.ALT_ANY)));
        });

        cbIdioma.setItems(idiomas);
        idiomas.setAll(new IdiomaDAO().pegarTodos());
        cbIdioma.setCellFactory(new IdiomaCellFactory());
        cbIdioma.getSelectionModel().select(Sessao.idioma);
        fcImagem = new FileChooser();
        fcImagem.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.gif"));
        pessoa = Sessao.pessoa;
        tfNome.setText(pessoa.getNome());
        tfUsuario.setText(pessoa.getUsuario());
        pfSenha.setText(pessoa.getSenha());
        cbIdioma.getSelectionModel().select(pessoa.getIdioma());
        dpDataNascimento.setValue(LocalDateTime.from(Instant.ofEpochMilli(pessoa.getNascimento().getTime()).atZone(ZoneId.systemDefault())).toLocalDate());
        validarCadastrar();
    }

    @FXML
    private void btAtualizarActionEvent(ActionEvent actionEvent) {
        File imagem = fcImagem.showOpenDialog(apPrincipal.getScene().getWindow());
        if (imagem != null) {
            try {
                pessoa.setFoto(Files.readAllBytes(Paths.get(imagem.getAbsolutePath())));
                ivFoto.setImage(new Image(new ByteArrayInputStream(pessoa.getFoto())));
            } catch (IOException ex) {
                Logger.getLogger(CadastrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btApagarActionEvent(ActionEvent actionEvent) {
        pessoa.setFoto(null);
        ivFoto.setImage(new Image(getClass().getResourceAsStream(FxMananger.VIEW + "image/padrao.jpg")));
    }

    @FXML
    private void btCadastrarActionEvent(ActionEvent actionEvent) {
        pessoa.setNome(tfNome.getText());
        pessoa.setIdioma(cbIdioma.getSelectionModel().getSelectedItem());
        pessoa.setSenha(pfSenha.getText());
        pessoa.setUsuario(tfUsuario.getText());
        pessoa.setNascimento(Date.from(dpDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        new PessoaDAO().editar(pessoa);
        Message.show("", ControlTranducao.traduzirPalavra("EVPESSOAATUALIZADA"), Alert.AlertType.INFORMATION);
        btCancelarActionEvent(actionEvent);
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel((ScrollPane) apPrincipal.getParent().getParent().getParent(), null);
    }

    @FXML
    private void cbIdiomaActionEvent(ActionEvent actionEvent) {
        Sessao.idioma = cbIdioma.getSelectionModel().getSelectedItem();
        ControlTranducao.traduzirComponentes(apPrincipal.getChildren());
    }

    @FXML
    private void tfValidarActionEvent(ActionEvent actionEvent) {
        validarCadastrar();
    }

    @FXML
    private void tfFieldKeyRelease(KeyEvent keyEvent) {
        validarCadastrar();
    }

    @FXML
    public void validarCadastrar() {
        String senha = pfSenha.getText();
        boolean usuarioValido = tfUsuario.getText().matches("^((\\w|\\.|@|-){1,})$") && new PessoaDAO().pegarPorUsuarioEditar(tfUsuario.getText()) == null;
        boolean senhaValido = senha.matches(".*([A-Z]{1,}).*") && senha.matches(".*([a-z]{1,}).*") && senha.matches(".*([0-9]{1,}).*");
        if (senhaValido) {
            pfSenha.getStyleClass().remove("error");
            pfSenha.getStyleClass().add("certo");
        } else {
            pfSenha.getStyleClass().remove("certo");
            pfSenha.getStyleClass().add("error");
        }
        if (usuarioValido) {
            tfUsuario.getStyleClass().remove("error");
            tfUsuario.getStyleClass().add("certo");
        } else {
            tfUsuario.getStyleClass().remove("certo");
            tfUsuario.getStyleClass().add("error");
        }
        tfUsuario.applyCss();
        pfSenha.applyCss();
        btCadastrar.setDisable(!senhaValido || !usuarioValido || tfNome.getText().isEmpty() || dpDataNascimento.getValue() == null || cbIdioma.getSelectionModel().getSelectedItem() == null);
    }

}
