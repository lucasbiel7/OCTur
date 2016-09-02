/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Aviao;
import br.com.OCTur.model.entity.Companhia;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class AviaoDAO extends GenericaDAO<Aviao> {

    public List<Aviao> pegarPorCompanhia(Companhia companhia) {
        entitys = criteria.add(Restrictions.eq("companhia", companhia)).list();
        closeSession();
        return entitys;
    }

}
