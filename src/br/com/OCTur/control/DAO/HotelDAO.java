/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Empresa;
import br.com.OCTur.model.entity.Hotel;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class HotelDAO extends GenericaDAO<Hotel> {

    public List<Hotel> pegarPorCidade(Cidade cidade) {
        List<Empresa> empresas = new EmpresaDAO().pegarPorCidade(cidade);
        if (empresas.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("empresa", empresas)).list();
        closeSession();
        return entitys;
    }

    public Hotel pegarPorEmpresa(Empresa empresa) {
        entity = (Hotel) criteria.add(Restrictions.eq("empresa", empresa)).uniqueResult();
        closeSession();
        return entity;
    }

}
