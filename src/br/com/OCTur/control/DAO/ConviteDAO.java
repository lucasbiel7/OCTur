/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Convite;
import br.com.OCTur.model.entity.Evento;
import br.com.OCTur.model.entity.Pessoa;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ConviteDAO extends GenericaDAO<Convite> {

    public Convite pegarPorEventoConvidado(Evento t, Pessoa pessoa) {
        entity = (Convite) criteria.add(Restrictions.eq("evento", t)).add(Restrictions.eq("convidado", pessoa)).uniqueResult();
        closeSession();
        return entity;
    }

}
