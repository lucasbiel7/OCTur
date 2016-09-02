/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.model.entity.Pacote;
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
public class VisualizarPacoteController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbCidade;
    @FXML
    private Label lbDescricao;
    @FXML
    private Label lbPreco;
    @FXML
    private Label lbPrecoDividido;

    private Pacote pacote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            pacote = (Pacote) apPrincipal.getUserData();
            if (pacote.getHotel() != null) {
                if (pacote.getHotel().getEmpresa().getCidade().getFoto() != null) {
                    ivFoto.setImage(new Image(new ByteArrayInputStream(pacote.getHotel().getEmpresa().getCidade().getFoto())));
                }
                lbCidade.setText(pacote.getHotel().getEmpresa().getCidade().getNome());
            } else if (pacote.getVoo() != null) {
                if (pacote.getVoo().getTrajeto().getDestino().getCidade().getFoto() != null) {
                    ivFoto.setImage(new Image(new ByteArrayInputStream(pacote.getVoo().getTrajeto().getDestino().getCidade().getFoto())));
                }
                lbCidade.setText(pacote.getVoo().getTrajeto().getDestino().getCidade().getNome());
            }
            lbDescricao.setText(""
                    + "Ida: " + DateFormatter.toDate(pacote.getInicio()) + "\n"
                    + "Volta: " + DateFormatter.toDate(pacote.getFim()) + "\n"
                    + (pacote.getHotel() != null ? "Hotel: " + pacote.getHotel() + "\n"
                            + "Tipo quarto: " + pacote.getTipoQuarto() + "\n" : "")
                    + (pacote.getVoo() != null ? "Voo: " + pacote.getVoo() + "\n" : "")
                    + "");
            lbPreco.setText(NumberFormatter.toMoney(pacote.getPreco()));
            lbPrecoDividido.setText("Até 10x de " + NumberFormatter.toMoney(pacote.getPreco() / 10));
        });
    }

}
