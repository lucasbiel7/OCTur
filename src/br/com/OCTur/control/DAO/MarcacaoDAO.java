/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Evento;
import br.com.OCTur.model.entity.Marcacao;
import br.com.OCTur.model.entity.Pessoa;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class MarcacaoDAO extends GenericaDAO<Marcacao> {

    public List<Marcacao> pegarPorEventoVai(Evento evento, boolean vai) {
        entitys = criteria.add(Restrictions.eq("evento", evento)).add(Restrictions.eq("vai", vai)).list();
        closeSession();
        return entitys;
    }

    public Marcacao pegarPorPessoaEvento(Pessoa pessoa, Evento evento) {
        entity = (Marcacao) criteria.add(Restrictions.eq("pessoa", pessoa)).add(Restrictions.eq("evento", evento)).uniqueResult();
        closeSession();
        return entity;
    }

}
