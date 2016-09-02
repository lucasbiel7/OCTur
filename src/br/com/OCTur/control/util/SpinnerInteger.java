/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author OCTI01
 */
public class SpinnerInteger extends SpinnerValueFactory<Integer> {

    private int menor;
    private int maior;

    public SpinnerInteger(int menor, int maior) {
        this.menor = menor;
        this.maior = maior;
        setValue(menor);
    }

    @Override
    public void decrement(int steps) {
        if (getValue() - steps >= menor) {
            setValue(getValue() - steps);
        }
    }

    @Override
    public void increment(int steps) {
        if (getValue() + steps <= maior) {
            setValue(getValue() + steps);
        }
    }

}
