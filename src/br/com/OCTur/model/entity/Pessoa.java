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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "pessoa")
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p")})
public class Pessoa implements Serializable {

    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @OneToMany(mappedBy = "pessoa")
    private List<Marcacao> marcacaoList;
    @OneToMany(mappedBy = "criador")
    private List<Evento> eventoList;
    @OneToMany(mappedBy = "pessoa")
    private List<Comentario> comentarioList;
    @OneToMany(mappedBy = "autor")
    private List<Convite> conviteList;
    @OneToMany(mappedBy = "convidado")
    private List<Convite> conviteList1;
    @OneToMany(mappedBy = "pessoa")
    private List<Compra> compraList;
    @OneToMany(mappedBy = "pessoa")
    private List<Reserva> reservaList;
    @OneToMany(mappedBy = "pessoa")
    private List<Passagem> passagemList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "nascimento")
    @Temporal(TemporalType.DATE)
    private Date nascimento;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "senha")
    private String senha;
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
    @JoinColumn(name = "idioma", referencedColumnName = "id")
    @ManyToOne
    private Idioma idioma;
    @JoinColumn(name = "papel", referencedColumnName = "id")
    @ManyToOne
    private Papel papel;

    public Pessoa() {
    }

    public Pessoa(Integer id) {
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

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
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
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }


    public List<Passagem> getPassagemList() {
        return passagemList;
    }

    public void setPassagemList(List<Passagem> passagemList) {
        this.passagemList = passagemList;
    }


    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }


    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public List<Marcacao> getMarcacaoList() {
        return marcacaoList;
    }

    public void setMarcacaoList(List<Marcacao> marcacaoList) {
        this.marcacaoList = marcacaoList;
    }

    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    public List<Convite> getConviteList() {
        return conviteList;
    }

    public void setConviteList(List<Convite> conviteList) {
        this.conviteList = conviteList;
    }

    public List<Convite> getConviteList1() {
        return conviteList1;
    }

    public void setConviteList1(List<Convite> conviteList1) {
        this.conviteList1 = conviteList1;
    }

}
