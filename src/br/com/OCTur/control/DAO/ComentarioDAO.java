/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Comentario;
import br.com.OCTur.model.entity.Evento;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ComentarioDAO extends GenericaDAO<Comentario> {

    public List<Comentario> pegarPorEvento(Evento evento) {
        entitys = criteria.add(Restrictions.eq("evento", evento)).addOrder(Order.desc("data")).addOrder(Order.desc("hora")).list();
        closeSession();
        return entitys;
    }

}
