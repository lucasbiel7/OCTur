/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import java.text.DecimalFormat;

/**
 *
 * @author OCTI01
 */
public class NumberFormatter {

    public static String duasCasas(double value) {
        return new DecimalFormat("0.00").format(value);
    }

    public static String toMoney(double value) {
        return "R$ " + duasCasas(value);
    }
    
}
