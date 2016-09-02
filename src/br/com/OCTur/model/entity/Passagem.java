/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author OCTI01
 */
@Entity
@Table(name = "passagem")
@NamedQueries({
    @NamedQuery(name = "Passagem.findAll", query = "SELECT p FROM Passagem p")})
public class Passagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "numeropessoas")
    private Integer numeropessoas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "pessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoa;
    @JoinColumn(name = "vooida", referencedColumnName = "id")
    @ManyToOne
    private Voo vooida;
    @JoinColumn(name = "voovolta", referencedColumnName = "id")
    @ManyToOne
    private Voo voovolta;

    public Passagem() {
    }

    public Passagem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getNumeropessoas() {
        return numeropessoas;
    }

    public void setNumeropessoas(Integer numeropessoas) {
        this.numeropessoas = numeropessoas;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Voo getVooida() {
        return vooida;
    }

    public void setVooida(Voo vooida) {
        this.vooida = vooida;
    }

    public Voo getVoovolta() {
        return voovolta;
    }

    public void setVoovolta(Voo voovolta) {
        this.voovolta = voovolta;
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
        if (!(object instanceof Passagem)) {
            return false;
        }
        Passagem other = (Passagem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.OCTur.model.entity.Passagem[ id=" + id + " ]";
    }
    
}
