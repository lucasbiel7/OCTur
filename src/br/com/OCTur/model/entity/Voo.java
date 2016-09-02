/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author OCTI01
 */
@Entity
@Table(name = "voo")
@NamedQueries({
    @NamedQuery(name = "Voo.findAll", query = "SELECT v FROM Voo v")})
public class Voo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "datapartida")
    @Temporal(TemporalType.DATE)
    private Date datapartida;
    @Column(name = "datachegada")
    @Temporal(TemporalType.DATE)
    private Date datachegada;
    @Column(name = "horapartida")
    @Temporal(TemporalType.TIME)
    private Date horapartida;
    @Column(name = "horachegada")
    @Temporal(TemporalType.TIME)
    private Date horachegada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco")
    private Double preco;
    @OneToMany(mappedBy = "vooida",cascade = CascadeType.ALL)
    private List<Passagem> passagemList;
    @OneToMany(mappedBy = "voovolta",cascade = CascadeType.ALL)
    private List<Passagem> passagemList1;
    @JoinColumn(name = "aviao", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Aviao aviao;
    @JoinColumn(name = "trajeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Trajeto trajeto;
    @OneToMany(mappedBy = "voo")
    private List<Pacote> pacotes;

    public Voo() {
    }

    public Voo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatapartida() {
        return datapartida;
    }

    public void setDatapartida(Date datapartida) {
        this.datapartida = datapartida;
    }

    public Date getDatachegada() {
        return datachegada;
    }

    public void setDatachegada(Date datachegada) {
        this.datachegada = datachegada;
    }

    public Date getHorapartida() {
        return horapartida;
    }

    public void setHorapartida(Date horapartida) {
        this.horapartida = horapartida;
    }

    public Date getHorachegada() {
        return horachegada;
    }

    public void setHorachegada(Date horachegada) {
        this.horachegada = horachegada;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<Passagem> getPassagemList() {
        return passagemList;
    }

    public void setPassagemList(List<Passagem> passagemList) {
        this.passagemList = passagemList;
    }

    public List<Passagem> getPassagemList1() {
        return passagemList1;
    }

    public void setPassagemList1(List<Passagem> passagemList1) {
        this.passagemList1 = passagemList1;
    }

    public Aviao getAviao() {
        return aviao;
    }

    public void setAviao(Aviao aviao) {
        this.aviao = aviao;
    }

    public Trajeto getTrajeto() {
        return trajeto;
    }

    public void setTrajeto(Trajeto trajeto) {
        this.trajeto = trajeto;
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
        if (!(object instanceof Voo)) {
            return false;
        }
        Voo other = (Voo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getAviao().getCompanhia().toString();
    }
    
}
