/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Estado;
import br.com.OCTur.model.entity.Pais;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class CidadeDAO extends GenericaDAO<Cidade> {

    public List<Cidade> pegarPorEstado(Estado estado) {
        entitys = criteria.add(Restrictions.eq("estado", estado)).list();
        closeSession();
        return entitys;

    }

    public List<Cidade> pegarPorPais(Pais pais) {
        List<Estado> estados = new EstadoDAO().pegarPorPais(pais);
        if (estados.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("estado", estados)).list();
        closeSession();
        return entitys;
    }

}
