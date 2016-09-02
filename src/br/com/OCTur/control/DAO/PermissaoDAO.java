/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.DAO;

import br.com.OCTur.model.GenericaDAO;
import br.com.OCTur.model.entity.Papel;
import br.com.OCTur.model.entity.Permissao;
import br.com.OCTur.model.entity.TipoPermissao;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PermissaoDAO extends GenericaDAO<Permissao> {

    public List<Permissao> pegarPorPapel(Papel papel) {
        entitys = criteria.add(Restrictions.eq("papel", papel)).list();
        closeSession();
        return entitys;
    }

    public Permissao pegarPorPapelTipoPermissao(Papel papel, TipoPermissao tipoPermissao) {
        entity = (Permissao) criteria.add(Restrictions.eq("papel", papel)).add(Restrictions.eq("tipopermissao", tipoPermissao)).uniqueResult();
        closeSession();
        return entity;
    }
    
}
