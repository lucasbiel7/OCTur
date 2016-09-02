/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Orcamento;
import br.com.OCTur.model.entity.Quarto;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class QuartoDAO extends GenericaDAO<Quarto> {

    public List<Quarto> pegarPorOrcamento(Orcamento orcamento) {
        entitys = criteria.add(Restrictions.eq("orcamento", orcamento)).list();
        closeSession();
        return entitys;
    }

    public List<Quarto> pegarPorHotel(Hotel hotel) {
        List<Orcamento> orcamentos = new OrcamentoDAO().pegarPorHotel(hotel);
        if (orcamentos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("orcamento", orcamentos)).list();
        closeSession();
        return entitys;
    }

   

}
