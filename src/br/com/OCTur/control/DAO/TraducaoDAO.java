/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Idioma;
import br.com.OCTur.model.entity.Traducao;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class TraducaoDAO extends GenericaDAO<Traducao> {

    public Traducao pegarPorIdiomaPalavra(Idioma idioma, String palavra) {
        entity = (Traducao) criteria.add(Restrictions.eq("idioma", idioma)).add(Restrictions.eq("idtexto", palavra)).uniqueResult();
        closeSession();
        return entity;
    }

}
