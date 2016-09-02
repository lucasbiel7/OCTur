/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Empresa;
import br.com.OCTur.model.entity.Pessoa;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PessoaDAO extends GenericaDAO<Pessoa> {

    public Pessoa pegarPorUsuario(String text) {
        entity = (Pessoa) criteria.add(Restrictions.eq("usuario", text)).uniqueResult();
        closeSession();
        return entity;
    }

    public Pessoa pegarPorUsuarioEditar(String text) {
        entity = (Pessoa) criteria.add(Restrictions.eq("usuario", text)).add(Restrictions.not(Restrictions.eq("id", Sessao.pessoa.getId()))).uniqueResult();
        closeSession();
        return entity;
    }

    public List<Pessoa> pegarPorEmpresa(Empresa empresa) {
        entitys = criteria.add(Restrictions.eq("empresa", empresa)).list();
        closeSession();
        return entitys;
    }

    public List<Pessoa> pegarSemEmpresa() {
        entitys = criteria.add(Restrictions.isNull("empresa")).list();
        closeSession();
        return entitys;
    }

}
