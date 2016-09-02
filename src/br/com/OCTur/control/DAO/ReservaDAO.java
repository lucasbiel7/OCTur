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
import br.com.OCTur.model.entity.Reserva;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ReservaDAO extends GenericaDAO<Reserva> {

    public List<Reserva> pegarPorQuartoInicioFim(Quarto quarto, Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.eq("quarto", quarto)).add(
                Restrictions.or(
                        Restrictions.and(Restrictions.le("inicio", inicio), Restrictions.ge("fim", inicio)),
                        Restrictions.between("inicio", inicio, fim),
                        Restrictions.between("fim", inicio, fim)
                )).list();
        closeSession();
        return entitys;
    }

    public List<Reserva> pegarPorHotelInicioFim(Hotel hotel, Date inicio, Date fim) {
        List<Quarto> quartos = new QuartoDAO().pegarPorHotel(hotel);
        if (quartos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.between("inicio", inicio, fim)).add(Restrictions.in("quarto", quartos)).list();
        closeSession();
        return entitys;
    }

    public List<Reserva> pegarPorOrcamentoInicioFim(Orcamento orcamento, Date inicio, Date fim) {
        List<Quarto> quartos = new QuartoDAO().pegarPorOrcamento(orcamento);
        if (quartos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.between("inicio", inicio, fim)).add(Restrictions.in("quarto", quartos)).list();
        closeSession();
        return entitys;
    }

}
