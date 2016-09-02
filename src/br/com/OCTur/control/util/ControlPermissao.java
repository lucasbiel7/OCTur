/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import br.com.OCTur.control.DAO.PermissaoDAO;
import br.com.OCTur.model.entity.Permissao;

/**
 *
 * @author OCTI01
 */
public class ControlPermissao {

    public static boolean isValido(String nomePermissao) {
        if (Sessao.pessoa.getPapel() != null) {
            if (Sessao.pessoa.getPapel().getNome().equalsIgnoreCase(nomePermissao)) {
                return true;
            } else {
                for (Permissao permissao : new PermissaoDAO().pegarPorPapel(Sessao.pessoa.getPapel())) {
                    if (permissao.getTipopermissao().getCodigo().equalsIgnoreCase(nomePermissao)) {
                        return permissao.isHabilitado();
                    }
                }
                return false;
            }
        } else {
            return false;
        }
    }

}
