/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Pais;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class AeroportoDAO extends GenericaDAO<Aeroporto> {

    public List<Aeroporto> pegarPorPais(Pais pais) {
        List<Cidade> cidades = new CidadeDAO().pegarPorPais(pais);
        if (cidades.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("cidade", cidades)).list();
        closeSession();
        return entitys;
    }

    public List<Aeroporto> pegarPorCidade(Cidade cidade) {
        entitys = criteria.add(Restrictions.eq("cidade", cidade)).list();
        closeSession();
        return entitys;
    }

}
