/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Aviao;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Companhia;
import br.com.OCTur.model.entity.Passagem;
import br.com.OCTur.model.entity.Voo;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PassagemDAO extends GenericaDAO<Passagem> {

    public List<Passagem> pegarPorVoo(Voo t) {
        entitys = criteria.add(Restrictions.or(Restrictions.eq("vooida", t), Restrictions.eq("voovolta", t))).list();
        closeSession();
        return entitys;
    }

    public List<Passagem> pegarPorDestino(Aeroporto destino) {
        List<Voo> voos = new VooDAO().pegarPorDestino(destino);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }

    public List<Passagem> pegarPorCompanhia(Companhia companhia) {
        List<Voo> voos = new VooDAO().pegarPorCompanhia(companhia);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }

    public List<Passagem> pegarPorDestinoInicioFim(Aeroporto destino, Date inicio, Date fim) {
        List<Voo> voos = new VooDAO().pegarPorDestino(destino, inicio, fim);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }
    public List<Passagem> pegarPorDestinoInicioFim(Cidade destino, Date inicio, Date fim) {
        List<Voo> voos = new VooDAO().pegarPorDestino(destino, inicio, fim);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }

    public List<Passagem> pegarPorCompanhia(Companhia companhia, Date inicio, Date fim) {
        List<Voo> voos = new VooDAO().pegarPorCompanhia(companhia, inicio, fim);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }

    public List<Passagem> pegarPorAviao(Aviao aviao) {
        List<Voo> voos = new VooDAO().pegarPorAviao(aviao);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }

    public List<Passagem> pegarPorAviao(Aviao aviao, Date inicio, Date fim) {
        List<Voo> voos = new VooDAO().pegarPorAviao(aviao, inicio, fim);
        if (voos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.or(Restrictions.in("vooida", voos), Restrictions.in("voovolta", voos))).list();
        closeSession();
        return entitys;
    }

}
