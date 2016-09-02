/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import br.com.OCTur.control.DAO.TraducaoDAO;
import br.com.OCTur.model.entity.Traducao;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author OCTI01
 */
public class ControlTranducao {

    public static String traduzirPalavra(String palavra) {
        Traducao traducao = new TraducaoDAO().pegarPorIdiomaPalavra(Sessao.idioma, palavra.toUpperCase());
        if (traducao != null) {
            return traducao.getTraducao();
        }
        return palavra;
    }

    public static void traduzirComponentes(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof ScrollPane) {
                ScrollPane componente = (ScrollPane) node;
                traduzirComponentes(componente.getContent());
            } else if (node instanceof AnchorPane) {
                AnchorPane componente = (AnchorPane) node;
                traduzirComponentes(componente.getChildren());
            } else if (node instanceof GridPane) {
                GridPane componente = (GridPane) node;
                traduzirComponentes(componente.getChildren());
            } else if (node instanceof HBox) {
                HBox componente = (HBox) node;
                traduzirComponentes(componente.getChildren());
            } else if (node instanceof VBox) {
                VBox componente = (VBox) node;
                traduzirComponentes(componente.getChildren());
            } else if (node instanceof Label) {
                Label componente = (Label) node;
                if (node.getUserData() == null) {
                    node.setUserData(componente.getText());
                }
                componente.setText(traduzirPalavra((String) componente.getUserData()));
            } else if (node instanceof Button) {
                Button componente = (Button) node;
                if (node.getUserData() == null) {
                    node.setUserData(componente.getText());
                }
                componente.setText(traduzirPalavra((String) componente.getUserData()));
            } else if (node instanceof RadioButton) {
                RadioButton componente = (RadioButton) node;
                if (node.getUserData() == null) {
                    node.setUserData(componente.getText());
                }
                componente.setText(traduzirPalavra((String) componente.getUserData()));
            } else if (node instanceof TableView) {
                TableView componente = (TableView) node;
                for (Object column : componente.getColumns()) {
                    TableColumn tableColumn = (TableColumn) column;
                    if (tableColumn.getUserData() == null) {
                        tableColumn.setUserData(tableColumn.getText());
                    }
                    tableColumn.setText(traduzirPalavra((String) tableColumn.getUserData()));
                }
            } else if (node instanceof CheckBox) {
                CheckBox componente = (CheckBox) node;
                if (node.getUserData() == null) {
                    node.setUserData(componente.getText());
                }
                componente.setText(traduzirPalavra((String) componente.getUserData()));
            }
        }
    }

    public static void traduzirComponentes(List<Node> nodes) {
        traduzirComponentes(nodes.toArray(new Node[]{}));
    }

}
