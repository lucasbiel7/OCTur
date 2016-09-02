/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.AeroportoDAO;
import br.com.OCTur.control.DAO.AviaoDAO;
import br.com.OCTur.control.DAO.CompanhiaDAO;
import br.com.OCTur.control.DAO.PassagemDAO;
import br.com.OCTur.control.DAO.VooDAO;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.EntidadeGrafico;
import br.com.OCTur.model.entity.Aeroporto;
import br.com.OCTur.model.entity.Aviao;
import br.com.OCTur.model.entity.Companhia;
import br.com.OCTur.model.entity.Passagem;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ContigenteController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;
    @FXML
    private RadioButton rbAeroporto;
    @FXML
    private RadioButton rbCompanhia;
    @FXML
    private RadioButton rbAviao;

    @FXML
    private RadioButton rbTudo;
    @FXML
    private RadioButton rbEspecifico;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;

    private SwingNode snGraficos;

    private List<EntidadeGrafico<Aeroporto>> aeroportos;
    private List<EntidadeGrafico<Companhia>> companhias;
    private List<EntidadeGrafico<Aviao>> aviaos;
    private Companhia companhia;

    private int inicio = 0;
    private double MAX;
    private double MIN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        snGraficos = new SwingNode();
        spContainer.setContent(snGraficos);
        companhia = new CompanhiaDAO().pegarPorEmpresa(Sessao.pessoa.getEmpresa());
        carregarDados(null);
    }

    @FXML
    private void btProximoActionEvent(ActionEvent actionEvent) {
        inicio += 10;
        eCarregarGraficosActionEvent(actionEvent);
    }

    @FXML
    private void btAnteriorActionEvent(ActionEvent actionEvent) {
        inicio -= 10;
        if (inicio < 0) {
            inicio = 0;
        }
        eCarregarGraficosActionEvent(actionEvent);
    }

    @FXML
    private void eCarregarGraficosActionEvent(ActionEvent actionEvent) {
        DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
        if (rbAeroporto.isSelected()) {
            if (inicio >= aeroportos.size() && !aeroportos.isEmpty()) {
                inicio = aeroportos.size() - 10;
            }
            for (EntidadeGrafico<Aeroporto> entidadegrafico : aeroportos.subList(inicio, inicio + 10 > aeroportos.size() ? aeroportos.size() : inicio + 10)) {
                if (entidadegrafico.getValue() >= MAX) {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas/Maior", entidadegrafico.toString());
                } else if (entidadegrafico.getValue() <= MIN) {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas/Menor", entidadegrafico.toString());
                } else {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas", entidadegrafico.toString());
                }
            }
        } else if (rbCompanhia.isSelected()) {
            if (inicio >= companhias.size() && !companhias.isEmpty()) {
                inicio = companhias.size() - 10;
            }
            for (EntidadeGrafico<Companhia> entidadegrafico : companhias.subList(inicio, inicio + 10 > companhias.size() ? companhias.size() : inicio + 10)) {
                if (entidadegrafico.getValue() >= MAX) {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas/Maior", entidadegrafico.toString());
                } else if (entidadegrafico.getValue() <= MIN) {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas/Menor", entidadegrafico.toString());
                } else {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas", entidadegrafico.toString());
                }
            }
        } else {
            if (inicio >= aviaos.size() && !aviaos.isEmpty()) {
                inicio = aviaos.size() - 10;
            }
            for (EntidadeGrafico<Aviao> entidadegrafico : aviaos.subList(inicio, inicio + 10 > aviaos.size() ? aviaos.size() : inicio + 10)) {
                if (entidadegrafico.getValue() >= MAX) {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas/Maior", entidadegrafico.toString());
                } else if (entidadegrafico.getValue() <= MIN) {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas/Menor", entidadegrafico.toString());
                } else {
                    dcdDados.setValue(entidadegrafico.getValue(), "Pessoas", entidadegrafico.toString());
                }
            }
        }
        JFreeChart jFreeChart = ChartFactory.createBarChart("", "", "", dcdDados, PlotOrientation.VERTICAL, false, false, false);
        if (rbAviao.isSelected()) {
            jFreeChart.getCategoryPlot().getRangeAxis().setRange(new Range(0, 100));
            jFreeChart.getCategoryPlot().getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}%", NumberFormat.getInstance()));
            jFreeChart.getCategoryPlot().getRenderer().setBaseItemLabelsVisible(true);
        } else {
            jFreeChart.getCategoryPlot().getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getInstance()));
            jFreeChart.getCategoryPlot().getRenderer().setBaseItemLabelsVisible(true);
        }
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        snGraficos.setContent(chartPanel);
        snGraficos.getContent().repaint();
    }

    @FXML
    public void carregarDados(ActionEvent ae) {
        inicio = 0;
        aeroportos = new ArrayList<>();
        companhias = new ArrayList<>();
        aviaos = new ArrayList<>();
        if (rbAeroporto.isSelected()) {
            for (Aeroporto aeroporto : new AeroportoDAO().pegarTodos()) {
                if (rbTudo.isSelected()) {
                    aeroportos.add(new EntidadeGrafico<>(aeroporto, new PassagemDAO().pegarPorDestino(aeroporto).stream().mapToInt(Passagem::getNumeropessoas).sum()));
                } else {
                    aeroportos.add(new EntidadeGrafico<>(aeroporto, new PassagemDAO().pegarPorDestinoInicioFim(aeroporto, DateFormatter.toDate(dpInicio.getValue()), DateFormatter.toDate(dpFim.getValue())).stream().mapToInt(Passagem::getNumeropessoas).sum()));
                }
            }
            MAX = aeroportos.stream().mapToDouble(EntidadeGrafico::getValue).max().orElse(0);
            MIN = aeroportos.stream().mapToDouble(EntidadeGrafico::getValue).min().orElse(0);
            aeroportos.sort((EntidadeGrafico<Aeroporto> o1, EntidadeGrafico<Aeroporto> o2) -> Double.compare(o1.getValue(), o2.getValue()));
        } else if (rbAviao.isSelected()) {
            for (Aviao aviao : new AviaoDAO().pegarPorCompanhia(companhia)) {
                double total = 0;
                if (rbTudo.isSelected()) {
                    total = (new PassagemDAO().pegarPorAviao(aviao).stream().mapToDouble(Passagem::getNumeropessoas).sum() / new VooDAO().pegarPorAviao(aviao).size()) * 100 / aviao.getCapacidade();
                } else {
                    total = (new PassagemDAO().pegarPorAviao(aviao, DateFormatter.toDate(dpInicio.getValue()), DateFormatter.toDate(dpFim.getValue())).stream().mapToDouble(Passagem::getNumeropessoas).sum() / new VooDAO().pegarPorAviao(aviao, DateFormatter.toDate(dpInicio.getValue()), DateFormatter.toDate(dpFim.getValue())).size()) * 100 / aviao.getCapacidade();
                }
                aviaos.add(new EntidadeGrafico<>(aviao, total));
            }
            MAX = aviaos.stream().mapToDouble(EntidadeGrafico::getValue).max().orElse(0);
            MIN = aviaos.stream().mapToDouble(EntidadeGrafico::getValue).min().orElse(0);
            aviaos.sort((EntidadeGrafico<Aviao> o1, EntidadeGrafico<Aviao> o2) -> Double.compare(o1.getValue(), o2.getValue()));
        } else {
            for (Companhia companhia : new CompanhiaDAO().pegarTodos()) {
                if (rbTudo.isSelected()) {
                    companhias.add(new EntidadeGrafico<>(companhia, new PassagemDAO().pegarPorCompanhia(companhia).stream().mapToInt(Passagem::getNumeropessoas).sum()));
                } else {
                    companhias.add(new EntidadeGrafico<>(companhia, new PassagemDAO().pegarPorCompanhia(companhia, DateFormatter.toDate(dpInicio.getValue()), DateFormatter.toDate(dpFim.getValue())).stream().mapToInt(Passagem::getNumeropessoas).sum()));
                }
            }
            MAX = companhias.stream().mapToDouble(EntidadeGrafico::getValue).max().orElse(0);
            MIN = companhias.stream().mapToDouble(EntidadeGrafico::getValue).min().orElse(0);
            companhias.sort((EntidadeGrafico<Companhia> o1, EntidadeGrafico<Companhia> o2) -> Double.compare(o1.getValue(), o2.getValue()));
        }
        eCarregarGraficosActionEvent(ae);
    }
}
