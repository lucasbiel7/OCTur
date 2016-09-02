/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control;

import br.com.OCTur.control.util.ControlTranducao;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author OCTI01
 */
public class FxMananger {

    public enum Tipo {
        MAXIMIZADO, RESIZABLE, FULLSCREEN, EXIT_ON_CLOSE, MODAL;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public static final String VIEW = "/br.com.OCTur.control/".replace(".", "/").replace("control", "view");

    public static Parent carregarComponente(String FXML) {
        try {
            Parent parent = FXMLLoader.load(FxMananger.class.getResource(VIEW + FXML + ".fxml"));
            if (parent instanceof AnchorPane) {
                ControlTranducao.traduzirComponentes(((AnchorPane) parent).getChildren());
            }
            return parent;
        } catch (IOException ex) {
            System.out.println("Error ao carregar FXML");
            System.err.println(ex.getMessage());
            Logger.getLogger(FxMananger.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Parent carregarComponente(String FXML, Object objeto) {
        Parent parent = carregarComponente(FXML);
        parent.setUserData(objeto);
        return parent;
    }

    public static Stage showWindow(Parent parent, String title, Tipo... tipos) {
        Stage stage = new Stage();

        stage.setTitle(title);
        for (Tipo tipo : tipos) {
            switch (tipo) {
                case MAXIMIZADO:
                    stage.setMaximized(true);
                    break;
                case RESIZABLE:
                    stage.setResizable(true);
                    break;
                case FULLSCREEN:
                    stage.setFullScreen(true);
                    break;
                case EXIT_ON_CLOSE:
                    stage.setOnCloseRequest((WindowEvent event) -> {
                        Platform.exit();
                        System.exit(0);
                    });
                    break;
                case MODAL:
                    stage.initModality(Modality.APPLICATION_MODAL);
                    break;
            }
        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        return stage;
    }

    public static Stage showWindow(Parent parent, String title, Object data, Tipo... tipos) {
        Stage stage = showWindow(parent, title, tipos);
        parent.setUserData(data);
        return stage;
    }

    public static void inserirPainel(ScrollPane scrollPane, Parent parent) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(parent);
    }

}
