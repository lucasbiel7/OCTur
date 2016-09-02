/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.model.entity.Comentario;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarComentarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbUsuario;
    @FXML
    private Label lbData;
    @FXML
    private TextArea taComentario;
    @FXML
    private Rating raNota;
    @FXML
    private ImageView ivUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Comentario comentario = (Comentario) apPrincipal.getUserData();
            raNota.setRating(comentario.getNota() + 1);
            lbUsuario.setText(comentario.getPessoa().getNome());
            if (comentario.getPessoa().getFoto() != null) {
                ivUsuario.setImage(new Image(new ByteArrayInputStream(comentario.getPessoa().getFoto())));
            }
            lbData.setText("Enviado em :\n"
                    + DateFormatter.toDate(comentario.getData()) + "\n"
                    + DateFormatter.toHour(comentario.getHora()));
            taComentario.setText(comentario.getComentario());
        });
    }

}
