/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.CategoriaProduto;
import br.com.OCTur.model.entity.CompraItem;
import br.com.OCTur.model.entity.Fornecedor;
import br.com.OCTur.model.entity.Item;
import br.com.OCTur.model.entity.Produto;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class CompraItemDAO extends GenericaDAO<CompraItem> {

    public List<CompraItem> pegarPorItem(Item item) {
        entitys = criteria.add(Restrictions.eq("item", item)).list();
        closeSession();
        return entitys;
    }

    public List<CompraItem> pegarPorFonecedorCategoria(Fornecedor fornecedor, CategoriaProduto categoriaProduto) {
        List<Produto> produtos = new ProdutoDAO().pegarPorFornecedorCategoria(fornecedor, categoriaProduto);
        if (produtos.isEmpty()) {
            closeSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("produto", produtos)).list();
        closeSession();
        return entitys;
    }

    public List<CompraItem> pegarPorProduto(Produto produto) {
        entitys = criteria.add(Restrictions.eq("produto", produto)).list();
        closeSession();
        return entitys;
    }

}
