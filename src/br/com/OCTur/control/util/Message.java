/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author OCTI01
 */
public class Message {

    public enum Tipo {
        WARNING, ERROR, INFORMATION;
    }

    public static void show(String title, String message, Alert.AlertType alertType) {
        //        Notifications notifications = Notifications.create();
        //        notifications.text(message);
        //        notifications.title(title);
        //        switch (tipo) {
        //            case ERROR:
        //                notifications.showError();
        //                break;
        //            case INFORMATION:
        //                notifications.showInformation();
        //                break;
        //            case WARNING:
        //                notifications.showWarning();
        //                break;
        //    }
        Alert alert = new Alert(alertType, message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static boolean showConfirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        return alert.showAndWait().get().equals(ButtonType.YES);
    }

}
