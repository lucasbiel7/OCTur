/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control;

import br.com.OCTur.model.entity.Idioma;
import java.io.ByteArrayInputStream;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 *
 * @author OCTI01
 */
public class IdiomaCellFactory implements Callback<ListView<Idioma>, ListCell<Idioma>> {

    @Override
    public ListCell<Idioma> call(ListView<Idioma> param) {
        return new ListCell<Idioma>() {
            @Override
            protected void updateItem(Idioma item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                if (empty) {
                    setGraphic(null);
                    setText("");
                } else {
                    ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(item.getFoto())));
                    setText(item.getNome());
                    setGraphic(imageView);
                }
            }
        };
    }
}
