/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Trajeto;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class TrajetoDAO extends GenericaDAO<Trajeto> {

    public Trajeto pegarPorOrigemDestino(Aeroporto origem, Aeroporto destino) {
        entity = (Trajeto) criteria.add(Restrictions.eq("origem", origem)).add(Restrictions.eq("destino", destino)).uniqueResult();
        closeSession();
        return entity;
    }

    public Trajeto pegarPorOrigemDestino(Cidade origemCidade, Cidade destinoCidade) {
        Aeroporto origem = new AeroportoDAO().pegarPorCidade(origemCidade).get(0);
        Aeroporto destino = new AeroportoDAO().pegarPorCidade(destinoCidade).get(0);
        entity = (Trajeto) criteria.add(Restrictions.eq("origem", origem)).add(Restrictions.eq("destino", destino)).uniqueResult();
        closeSession();
        return entity;
    }

    public List<Trajeto> pegarPorOrigem(Aeroporto origem) {
        entitys = criteria.add(Restrictions.eq("origem", origem)).list();
        closeSession();
        return entitys;
    }

    public List<Trajeto> pegarPorDestino(Aeroporto destino) {
        entitys = criteria.add(Restrictions.eq("destino", destino)).list();
        closeSession();
        return entitys;
    }

    public List<Trajeto> pegarPorOrigem(Cidade cidade) {
        List<Aeroporto> aeroportos = new AeroportoDAO().pegarPorCidade(cidade);
        if (aeroportos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("origem", aeroportos)).list();
        closeSession();
        return entitys;
    }

    public List<Trajeto> pegarPorDestino(Cidade cidade) {
        List<Aeroporto> aeroportos = new AeroportoDAO().pegarPorCidade(cidade);
        if (aeroportos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("destino", aeroportos)).list();
        closeSession();
        return entitys;
    }

}
