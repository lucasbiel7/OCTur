/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import br.com.OCTur.control.DAO.IdiomaDAO;
import br.com.OCTur.model.entity.Idioma;
import br.com.OCTur.model.entity.Pessoa;

/**
 *
 * @author OCTI01
 */
public class Sessao {

    public static Pessoa pessoa;
    public static Idioma idioma=new IdiomaDAO().pegarPorId(1);
}
