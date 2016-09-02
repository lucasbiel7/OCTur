/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.MarcacaoDAO;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.model.entity.Evento;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarEventoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbQuantidade;
    @FXML
    private Label lbDescricao;
    @FXML
    private Label lbEvento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Evento evento = (Evento) apPrincipal.getUserData();
            if (evento.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(evento.getFoto())));
            }
            lbEvento.setText(evento.getNome());
            lbDescricao.setText(ControlTranducao.traduzirPalavra("DE") + " " + DateFormatter.toDate(evento.getInicio()) + "\n"
                    + ControlTranducao.traduzirPalavra("Ate") + " " + DateFormatter.toDate(evento.getFim()));
            lbQuantidade.setText(String.valueOf(new MarcacaoDAO().pegarPorEventoVai(evento, true).size()));
        });
    }

}
