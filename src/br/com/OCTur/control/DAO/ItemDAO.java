/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Item;
import br.com.OCTur.model.entity.Produto;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ItemDAO extends GenericaDAO<Item> {

    public List<Item> pegarPorProduto(Produto produto) {
        entitys = criteria.add(Restrictions.eq("produto", produto)).addOrder(Order.asc("datacadastro")).list();
        closeSession();
        return entitys;
    }
}
