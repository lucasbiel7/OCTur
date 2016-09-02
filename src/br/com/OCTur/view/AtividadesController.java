/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.AviaoDAO;
import br.com.OCTur.control.DAO.CompanhiaDAO;
import br.com.OCTur.control.DAO.PassagemDAO;
import br.com.OCTur.control.DAO.VooDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.entity.Aviao;
import br.com.OCTur.model.entity.Companhia;
import br.com.OCTur.model.entity.Passagem;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AtividadesController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private ScrollPane spContainer;
    @FXML
    private Button btImprimir;

    private Companhia companhia;
    private SwingNode snRelatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btImprimir, new KeyCodeCombination(KeyCode.I, KeyCodeCombination.ALT_ANY)));

        });
        companhia = new CompanhiaDAO().pegarPorEmpresa(Sessao.pessoa.getEmpresa());
        snRelatorio = new SwingNode();
        spContainer.setContent(snRelatorio);
    }

    @FXML
    private void dpDataActionEvent(ActionEvent actionEvent) {
        if (companhia != null) {
            if (dpInicio.getValue() != null && dpFim.getValue() != null) {
                Map<String, Object> parametros = new HashMap<>();
                try {
                    parametros.put("companhia", companhia.toString());
                    parametros.put("logo", getClass().getResourceAsStream(FxMananger.VIEW + "image/octur-logo.png"));
                    String cabecalho = "<html>";
                    List<Aviao> avioes = new AviaoDAO().pegarPorCompanhia(companhia);
                    Date inicio = DateFormatter.toDate(dpInicio.getValue());
                    Date fim = DateFormatter.toDate(dpFim.getValue());
                    List<Passagem> passagem = new PassagemDAO().pegarPorCompanhia(companhia, inicio, fim);
                    int totalAssentos = avioes.stream().mapToInt(Aviao::getCapacidade).sum();
                    double receitaTotal = passagem.stream().mapToDouble(Passagem::getTotal).sum();
                    int passeiroTotal = passagem.stream().mapToInt(Passagem::getNumeropessoas).sum();
                    cabecalho = ControlTranducao.traduzirPalavra("de") + " " + DateFormatter.toDate(inicio) + " " + ControlTranducao.traduzirPalavra("a") + " " + DateFormatter.toDate(fim) + "<br>"
                            + "<b>" + ControlTranducao.traduzirPalavra("receitatotal") + "</b>" + ": " + NumberFormatter.toMoney(receitaTotal) + "<br/>"
                            + "<b>" + ControlTranducao.traduzirPalavra("nototalassentos") + "</b>" + ": " + totalAssentos + "<br/>"
                            + "<b>" + ControlTranducao.traduzirPalavra("nototalpassageiros") + "</b>" + ": " + passeiroTotal + "<br/>"
                            + "";
                    parametros.put("cabecalho", cabecalho);

                    DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new Object[]{"aviao", "aviagens", "receita", "assento"});
                    for (Aviao aviao : avioes) {
                        double receitaAviao = new PassagemDAO().pegarPorAviao(aviao, inicio, fim).stream().mapToDouble(Passagem::getTotal).sum();
                        defaultTableModel.addRow(new Object[]{
                            aviao.getNome(),
                            String.valueOf(new VooDAO().pegarPorAviao(aviao, inicio, fim).size()),
                            NumberFormatter.toMoney(receitaAviao) + " (" + NumberFormatter.duasCasas(receitaAviao * 100 / receitaTotal) + "% " + ControlTranducao.traduzirPalavra("dototal") + ")",
                            aviao.getCapacidade() + " (" + NumberFormatter.duasCasas(aviao.getCapacidade() * 100.0 / totalAssentos) + "% " + ControlTranducao.traduzirPalavra("dototal") + ")"
                        });
                    }
                    JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(FxMananger.VIEW + "report/atividades.jasper"), parametros, new JRTableModelDataSource(defaultTableModel));
                    JRViewer jRViewer = new JRViewer(jasperPrint);
                    snRelatorio.setContent(jRViewer);
                } catch (JRException e) {
                    System.err.println(e.getMessage());
                }
            }
        } else {
            Message.show("Nao é possível gerar", "Sua empresa não e uma companhia de avião\n"
                    + "por isso não é possivel abrir o relatorio!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btImprimirActionEvent(ActionEvent actionEvent) {
        Message.show("", ControlTranducao.traduzirPalavra("EVVOOSATIVIDADESIMPRIMINDO"), Alert.AlertType.INFORMATION);
    }
}
