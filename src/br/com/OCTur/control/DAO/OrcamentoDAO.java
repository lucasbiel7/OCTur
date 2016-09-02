/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Orcamento;
import br.com.OCTur.model.entity.TipoQuarto;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class OrcamentoDAO extends GenericaDAO<Orcamento> {

    public Orcamento pegarPorTipoQuartoHotel(TipoQuarto tipoquarto, Hotel hotel) {
        entity = (Orcamento) criteria.add(Restrictions.eq("tipoquarto", tipoquarto)).add(Restrictions.eq("hotel", hotel)).uniqueResult();
        closeSession();
        return entity;
    }

    public List<Orcamento> pegarPorHotel(Hotel hotel) {
        entitys = criteria.add(Restrictions.eq("hotel", hotel)).list();
        closeSession();
        return entitys;
        
    }

}
