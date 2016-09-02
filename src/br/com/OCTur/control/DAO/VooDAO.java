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
import br.com.OCTur.model.entity.Trajeto;
import br.com.OCTur.model.entity.Voo;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class VooDAO extends GenericaDAO<Voo> {

    public List<Voo> pegarPorTrajeto(Trajeto trajeto) {
        entitys = criteria.add(Restrictions.eq("trajeto", trajeto)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorTrajeto(Trajeto trajeto, Date ida) {
        entitys = criteria.add(Restrictions.eq("trajeto", trajeto)).add(Restrictions.eq("datapartida", ida)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorOrigem(Cidade cidade) {
        List<Trajeto> trajetos = new TrajetoDAO().pegarPorOrigem(cidade);
        if (trajetos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("trajeto", trajetos)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorDestino(Cidade cidade) {
        List<Trajeto> trajetos = new TrajetoDAO().pegarPorDestino(cidade);
        if (trajetos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("trajeto", trajetos)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorOrigemPartida(Cidade cidade, Date partida) {
        List<Trajeto> trajetos = new TrajetoDAO().pegarPorOrigem(cidade);
        if (trajetos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("trajeto", trajetos)).add(Restrictions.eq("datapartida", partida)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorPartida(Date partida) {
        entitys = criteria.add(Restrictions.eq("datapartida", partida)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorDestino(Aeroporto destino) {
        List<Trajeto> trajetos = new TrajetoDAO().pegarPorDestino(destino);
        if (trajetos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("trajeto", trajetos)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorCompanhia(Companhia companhia) {
        List<Aviao> aviaos = new AviaoDAO().pegarPorCompanhia(companhia);
        if (aviaos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("aviao", aviaos)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorDestino(Aeroporto destino, Date inicio, Date fim) {
        List<Trajeto> trajetos = new TrajetoDAO().pegarPorDestino(destino);
        if (trajetos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("trajeto", trajetos)).add(Restrictions.between("datachegada", inicio, fim)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorDestino(Cidade destino, Date inicio, Date fim) {
        List<Trajeto> trajetos = new TrajetoDAO().pegarPorDestino(destino);
        if (trajetos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("trajeto", trajetos)).add(Restrictions.between("datachegada", inicio, fim)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorCompanhia(Companhia companhia, Date inicio, Date fim) {
        List<Aviao> aviaos = new AviaoDAO().pegarPorCompanhia(companhia);
        if (aviaos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("aviao", aviaos)).add(Restrictions.between("datachegada", inicio, fim)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorAviao(Aviao aviao) {
        entitys = criteria.add(Restrictions.eq("aviao", aviao)).list();
        closeSession();
        return entitys;
    }

    public List<Voo> pegarPorAviao(Aviao aviao, Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.eq("aviao", aviao)).add(Restrictions.between("datachegada", inicio, fim)).list();
        closeSession();
        return entitys;
    }
}
