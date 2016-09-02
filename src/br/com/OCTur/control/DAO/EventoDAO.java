/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.CategoriaEvento;
import br.com.OCTur.model.entity.Evento;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class EventoDAO extends GenericaDAO<Evento> {

    public List<Evento> pegarPorNome(String nome) {
        entitys = criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE)).list();
        closeSession();
        return entitys;
    }

    public List<Evento> pegarPorNomeCategoriaEvento(String nome, CategoriaEvento categoria) {
        entitys = criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE)).add(Restrictions.eq("categoria", categoria)).list();
        closeSession();
        return entitys;
    }

}
