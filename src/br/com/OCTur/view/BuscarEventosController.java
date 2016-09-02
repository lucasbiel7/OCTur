/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CategoriaEventoDAO;
import br.com.OCTur.control.DAO.ComentarioDAO;
import br.com.OCTur.control.DAO.ConviteDAO;
import br.com.OCTur.control.DAO.EventoDAO;
import br.com.OCTur.control.DAO.MarcacaoDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.entity.CategoriaEvento;
import br.com.OCTur.model.entity.Comentario;
import br.com.OCTur.model.entity.Evento;
import br.com.OCTur.model.entity.Marcacao;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class BuscarEventosController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private AnchorPane apDetalhes;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private ComboBox<CategoriaEvento> cbCategoriaEvento;
    @FXML
    private RadioButton rbTodos;
    @FXML
    private RadioButton rbMeConvidaram;
    @FXML
    private RadioButton rbCriadosPorMim;
    @FXML
    private GridPane gpEventos;
    @FXML
    private Label lbEvento;
    @FXML
    private Label lbVoceVai;
    @FXML
    private Button btSim;
    @FXML
    private Button btNao;
    @FXML
    private Button btDesmarcar;
    @FXML
    private Text tDescricao;
    @FXML
    private VBox vbComentarios;
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextArea taComentario;
    @FXML
    private Rating raNota;
    @FXML
    private Button btLimpar;

    private Evento evento;
    private ObservableList<CategoriaEvento> categoriaEventos = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btLimpar, new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_ANY)));
        });
        carregarEventos(new EventoDAO().pegarTodos());
        categoriaEventos.setAll(new CategoriaEventoDAO().pegarTodos());
        cbCategoriaEvento.setItems(categoriaEventos);
        apDetalhes.setVisible(false);
    }

    @FXML
    private void btPesquisarActionEvent(ActionEvent actionEvent) {
        List<Evento> eventos;
        if (cbCategoriaEvento.getSelectionModel().getSelectedItem() != null) {
            eventos = new EventoDAO().pegarPorNomeCategoriaEvento(tfPesquisa.getText(), cbCategoriaEvento.getSelectionModel().getSelectedItem());
        } else {
            eventos = new EventoDAO().pegarPorNome(tfPesquisa.getText());
        }
        if (rbCriadosPorMim.isSelected()) {
            eventos.removeIf((Evento t) -> !t.getCriador().equals(Sessao.pessoa));
        } else if (rbMeConvidaram.isSelected()) {
            eventos.removeIf((Evento t) -> new ConviteDAO().pegarPorEventoConvidado(t, Sessao.pessoa) == null);
        }
        carregarEventos(eventos);
    }

    @FXML
    private void btLimparActionEvent(ActionEvent actionEvent) {
        rbTodos.setSelected(true);
        cbCategoriaEvento.getSelectionModel().clearSelection();
        tfPesquisa.clear();
    }

    @FXML
    private void btAdicionarComentarioActionEvent(ActionEvent actionEvent) {
        Comentario comentario = new Comentario();
        comentario.setComentario(taComentario.getText());
        comentario.setNota((int) raNota.getRating() - 1);
        comentario.setData(new Date());
        comentario.setHora(new Date());
        comentario.setPessoa(Sessao.pessoa);
        comentario.setEvento(evento);
        new ComentarioDAO().cadastrar(comentario);
        carregarComentarios();
        btLimparComentarioActionEvent(null);
    }

    @FXML
    private void btSimActionEvent(ActionEvent actionEvent) {
        Marcacao marcacao = new Marcacao();
        marcacao.setEvento(evento);
        marcacao.setPessoa(Sessao.pessoa);
        marcacao.setVai(true);
        new MarcacaoDAO().cadastrar(marcacao);
        carregarMarcacao();
        btPesquisarActionEvent(null);
    }

    @FXML
    private void btNaoActionEvent(ActionEvent actionEvent) {
        Marcacao marcacao = new Marcacao();
        marcacao.setEvento(evento);
        marcacao.setPessoa(Sessao.pessoa);
        marcacao.setVai(false);
        new MarcacaoDAO().cadastrar(marcacao);
        carregarMarcacao();
        btPesquisarActionEvent(null);
    }

    @FXML
    private void btDesmarcarActionEvent(ActionEvent actionEvent) {
        Marcacao marcacao = new MarcacaoDAO().pegarPorPessoaEvento(Sessao.pessoa, evento);
        new MarcacaoDAO().excluir(marcacao);
        carregarMarcacao();
        btPesquisarActionEvent(null);
    }

    @FXML
    private void btLimparComentarioActionEvent(ActionEvent actionEvent) {
        taComentario.clear();
        raNota.setRating(1);
    }

    private void carregarEventos(List<Evento> eventos) {
        gpEventos.getChildren().clear();
        int linha = 0;
        int coluna = 0;
        for (Evento evento : eventos) {
            Parent parent = FxMananger.carregarComponente("VisualizarEvento", evento);
            parent.setOnMouseReleased((MouseEvent event) -> {
                this.evento = evento;
                carregarEvento();
            });
            gpEventos.add(parent, coluna, linha);
            coluna++;
            if (coluna > 2) {
                coluna = 0;
                linha++;
            }
        }
    }

    private void carregarEvento() {
        lbEvento.setText(evento.getNome());
        tDescricao.setText("Periodo: " + DateFormatter.toDate(evento.getInicio()) + " " + DateFormatter.toDate(evento.getFim()) + "\n" + evento.getDescricao());
        if (evento.getFoto() != null) {
            ivFoto.setImage(new Image(new ByteArrayInputStream(evento.getFoto())));
        }
        carregarMarcacao();
        carregarComentarios();
        btLimparComentarioActionEvent(null);
        apDetalhes.setVisible(true);
    }

    private void carregarMarcacao() {
        Marcacao marcacao = new MarcacaoDAO().pegarPorPessoaEvento(Sessao.pessoa, evento);
        btSim.setVisible(marcacao == null);
        btNao.setVisible(marcacao == null);
        btSim.setDisable(marcacao != null);
        btNao.setDisable(marcacao != null);
        btDesmarcar.setVisible(marcacao != null);
        btDesmarcar.setDisable(marcacao == null);
        if (marcacao != null) {
            btSim.setVisible(marcacao.getVai());
            btNao.setVisible(!marcacao.getVai());
        }
    }

    private void carregarComentarios() {
        List<Comentario> comentarios = new ComentarioDAO().pegarPorEvento(evento);
        vbComentarios.getChildren().clear();
        for (Comentario comentario : comentarios) {
            vbComentarios.getChildren().add(FxMananger.carregarComponente("VisualizarComentario", comentario));
        }
    }
}
