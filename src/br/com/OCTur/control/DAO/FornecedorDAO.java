/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Empresa;
import br.com.OCTur.model.entity.Fornecedor;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class FornecedorDAO extends GenericaDAO<Fornecedor> {

    public Fornecedor pegarPorEmpresa(Empresa empresa) {
        entity = (Fornecedor) criteria.add(Restrictions.eq("empresa", empresa)).uniqueResult();
        closeSession();
        return entity;
    }

}
