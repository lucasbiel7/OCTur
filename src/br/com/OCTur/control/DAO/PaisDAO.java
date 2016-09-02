/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Pais;
import org.hibernate.criterion.Order;

/**
 *
 * @author OCTI01
 */
public class PaisDAO extends GenericaDAO<Pais> {

    public PaisDAO() {
        super();
        criteria.addOrder(Order.asc("nome"));
    }
    
}
