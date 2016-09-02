/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CidadeDAO;
import br.com.OCTur.control.DAO.EventoDAO;
import br.com.OCTur.control.DAO.HotelDAO;
import br.com.OCTur.control.DAO.IdiomaDAO;
import br.com.OCTur.control.DAO.PessoaDAO;
import br.com.OCTur.control.DAO.ProdutoDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Evento;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Idioma;
import br.com.OCTur.model.entity.Pessoa;
import br.com.OCTur.model.entity.Produto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author OCTI01
 */
public class Running extends Application {

    @Override
    public void start(Stage primaryStage) {
//        carregarFotosIdioma();
//        carregarFotosPessoas();
//        carregarFotosCidade();
//        carregarFotosHoteis();
//        carregarFotosProdutos();
//        carregarFotosEventos();
//Modo desenvolvedor
//Administrador
//        Sessao.pessoa = new PessoaDAO().pegarPorUsuario("admin");
//Dona de companhia
//        Sessao.pessoa = new PessoaDAO().pegarPorUsuario("raf-ae@l");
//Dono do hotel
//        Sessao.pessoa = new PessoaDAO().pegarPorUsuario("m@arcelo");
//Fornecedor
//        Sessao.pessoa = new PessoaDAO().pegarPorUsuario("paula");
//        FxMananger.showWindow(FxMananger.carregarComponente("Principal"), "Principal", FxMananger.Tipo.EXIT_ON_CLOSE, FxMananger.Tipo.MAXIMIZADO).show();
//Modo instalador
        FxMananger.showWindow(FxMananger.carregarComponente("Login"), "Login", FxMananger.Tipo.EXIT_ON_CLOSE).show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void carregarFotosIdioma() {
        for (Idioma idioma : new IdiomaDAO().pegarTodos()) {
            try {
                idioma.setFoto(Files.readAllBytes(Paths.get("idiomas/" + idioma.getFigura())));
                new IdiomaDAO().editar(idioma);
            } catch (IOException ex) {
                Logger.getLogger(Running.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void carregarFotosPessoas() {
        for (Pessoa pessoa : new PessoaDAO().pegarTodos()) {
            try {
                pessoa.setFoto(Files.readAllBytes(Paths.get("pessoas/" + pessoa.getId() + ".jpg")));
                new PessoaDAO().editar(pessoa);
            } catch (IOException ex) {
                Logger.getLogger(Running.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void carregarFotosCidade() {
        for (Cidade cidade : new CidadeDAO().pegarTodos()) {
            try {
                cidade.setFoto(Files.readAllBytes(Paths.get("cidades/" + cidade.getId() + ".jpg")));
                new CidadeDAO().editar(cidade);
            } catch (IOException ex) {
                Logger.getLogger(Running.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void carregarFotosHoteis() {
        for (Hotel hotel : new HotelDAO().pegarTodos()) {
            try {
                hotel.setFoto(Files.readAllBytes(Paths.get("hoteis/" + hotel.getId() + ".jpg")));
                new HotelDAO().editar(hotel);
            } catch (IOException ex) {
                Logger.getLogger(Running.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void carregarFotosProdutos() {
        for (Produto produto : new ProdutoDAO().pegarTodos()) {
            try {
                produto.setFoto(Files.readAllBytes(Paths.get("produtos/" + produto.getId() + ".jpg")));
                new ProdutoDAO().editar(produto);
            } catch (IOException ex) {
                Logger.getLogger(Running.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void carregarFotosEventos() {
        for (Evento evento : new EventoDAO().pegarTodos()) {
            try {
                evento.setFoto(Files.readAllBytes(Paths.get("eventos/" + evento.getId() + ".jpg")));
                new EventoDAO().editar(evento);
            } catch (IOException ex) {
                Logger.getLogger(Running.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
