/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author OCTI01
 */
@Entity
@Table(name = "aeroporto")
@NamedQueries({
    @NamedQuery(name = "Aeroporto.findAll", query = "SELECT a FROM Aeroporto a")})
public class Aeroporto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "taxaembarque")
    private Double taxaembarque;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destino")
    private List<Trajeto> trajetoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "origem")
    private List<Trajeto> trajetoList1;
    @JoinColumn(name = "cidade", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cidade cidade;

    public Aeroporto() {
    }

    public Aeroporto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTaxaembarque() {
        return taxaembarque;
    }

    public void setTaxaembarque(Double taxaembarque) {
        this.taxaembarque = taxaembarque;
    }

    public List<Trajeto> getTrajetoList() {
        return trajetoList;
    }

    public void setTrajetoList(List<Trajeto> trajetoList) {
        this.trajetoList = trajetoList;
    }

    public List<Trajeto> getTrajetoList1() {
        return trajetoList1;
    }

    public void setTrajetoList1(List<Trajeto> trajetoList1) {
        this.trajetoList1 = trajetoList1;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aeroporto)) {
            return false;
        }
        Aeroporto other = (Aeroporto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
