/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Pacote;
import br.com.OCTur.model.entity.Voo;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PacoteDAO extends GenericaDAO<Pacote> {

    public List<Pacote> pegarPorCidade(Cidade cidade) {
        List<Hotel> hoteis = new HotelDAO().pegarPorCidade(cidade);
        List<Voo> voos = new VooDAO().pegarPorDestino(cidade);
        Criterion[] criterios = new Criterion[2];
        if (!hoteis.isEmpty()) {
            criterios[0] = Restrictions.in("hotel", hoteis);
        }
        if (!voos.isEmpty()) {
            criterios[1] = Restrictions.in("voo", voos);
        }
        if (hoteis.isEmpty() && voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(criterios)).list();
        closeSession();
        return entitys;
    }

    public List<Pacote> pegarPorCidade(Cidade cidade, Date inicio, Date fim, boolean e) {
        List<Hotel> hoteis = new HotelDAO().pegarPorCidade(cidade);
        List<Voo> voos = new VooDAO().pegarPorDestino(cidade);
        Criterion[] criterios = new Criterion[2];
        if (!hoteis.isEmpty()) {
            criterios[0] = Restrictions.in("hotel", hoteis);
        }
        if (!voos.isEmpty()) {
            criterios[1] = Restrictions.in("voo", voos);
        }
        if (hoteis.isEmpty() && voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        if (e) {
            criteria.add(Restrictions.and(Restrictions.eq("inicio", inicio), Restrictions.eq("fim", fim)));
        } else {
            criteria.add(Restrictions.or(Restrictions.eq("inicio", inicio), Restrictions.eq("fim", fim)));
        }
        entitys = criteria.add(Restrictions.or(criterios)).list();
        closeSession();
        return entitys;
    }

    public List<Pacote> pegarPorCidadeInicio(Cidade cidade, Date inicio) {
        List<Hotel> hoteis = new HotelDAO().pegarPorCidade(cidade);
        List<Voo> voos = new VooDAO().pegarPorDestino(cidade);
        Criterion[] criterios = new Criterion[2];
        if (!hoteis.isEmpty()) {
            criterios[0] = Restrictions.in("hotel", hoteis);
        }
        if (!voos.isEmpty()) {
            criterios[1] = Restrictions.in("voo", voos);
        }
        if (hoteis.isEmpty() && voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(criterios)).add(Restrictions.eq("inicio", inicio)).list();
        closeSession();
        return entitys;
    }

    public List<Pacote> pegarPorCidadeFim(Cidade cidade, Date fim) {
        List<Hotel> hoteis = new HotelDAO().pegarPorCidade(cidade);
        List<Voo> voos = new VooDAO().pegarPorDestino(cidade);
        Criterion[] criterios = new Criterion[2];
        if (!hoteis.isEmpty()) {
            criterios[0] = Restrictions.in("hotel", hoteis);
        }
        if (!voos.isEmpty()) {
            criterios[1] = Restrictions.in("voo", voos);
        }
        if (hoteis.isEmpty() && voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(criterios)).add(Restrictions.eq("fim", fim)).list();
        closeSession();
        return entitys;
    }

    public List<Pacote> pegarPorInicioFim(Date inicio, Date fim, boolean selected) {
        if (selected) {
            criteria.add(Restrictions.and(Restrictions.eq("inicio", inicio), Restrictions.eq("fim", fim)));
        } else {
            criteria.add(Restrictions.or(Restrictions.eq("inicio", inicio), Restrictions.eq("fim", fim)));
        }
        entitys = criteria.list();
        closeSession();
        return entitys;
    }

    public List<Pacote> pegarPorInicio(Date inicio) {
        entitys = criteria.add(Restrictions.eq("inicio", inicio)).list();
        closeSession();
        return entitys;
    }

    public List<Pacote> pegarPorFim(Date fim) {
        entitys = criteria.add(Restrictions.eq("fim", fim)).list();
        closeSession();
        return entitys;
    }

}
