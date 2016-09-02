/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.CategoriaProduto;
import br.com.OCTur.model.entity.Fornecedor;
import br.com.OCTur.model.entity.Produto;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ProdutoDAO extends GenericaDAO<Produto> {

    public List<Produto> pegarPorNome(String text) {
        entitys = criteria.add(Restrictions.like("nome", text, MatchMode.ANYWHERE)).list();
        closeSession();
        return entitys;
    }

    public List<Produto> pegarPorNomeCategoria(String text, CategoriaProduto categoriaProduto) {
        entitys = criteria.add(Restrictions.like("nome", text, MatchMode.ANYWHERE)).add(Restrictions.eq("categoria", categoriaProduto)).list();
        closeSession();
        return entitys;
    }

    public List<Produto> pegarPorFornecedorCategoria(Fornecedor fornecedor, CategoriaProduto categoriaProduto) {
        entitys = criteria.add(Restrictions.eq("categoria", categoriaProduto)).add(Restrictions.eq("fornecedor", fornecedor)).list();
        closeSession();
        return entitys;
    }

    public List<Produto> pegarPorFornecedor(Fornecedor fornecedor) {
        entitys = criteria.add(Restrictions.eq("fornecedor", fornecedor)).list();
        closeSession();
        return entitys;
    }

}
