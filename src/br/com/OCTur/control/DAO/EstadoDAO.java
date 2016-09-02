/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Estado;
import br.com.OCTur.model.entity.Pais;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class EstadoDAO extends GenericaDAO<Estado> {

    public List<Estado> pegarPorPais(Pais pais) {
        entitys = criteria.add(Restrictions.eq("pais", pais)).list();
        closeSession();
        return entitys;
    }

}
