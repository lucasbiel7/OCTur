/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CategoriaProdutoDAO;
import br.com.OCTur.control.DAO.CompraItemDAO;
import br.com.OCTur.control.DAO.FornecedorDAO;
import br.com.OCTur.control.DAO.ItemDAO;
import br.com.OCTur.control.DAO.ProdutoDAO;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.EntidadeGrafico;
import br.com.OCTur.model.entity.CategoriaProduto;
import br.com.OCTur.model.entity.CompraItem;
import br.com.OCTur.model.entity.Fornecedor;
import br.com.OCTur.model.entity.Item;
import br.com.OCTur.model.entity.Produto;
import java.awt.BasicStroke;
import java.awt.Color;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GraficoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spProdutosMaisAntigos;
    @FXML
    private ScrollPane spCategoriaMaisVendida;
    @FXML
    private ScrollPane spInteressePorArtesanato;
    @FXML
    private Slider slMeta;

    private SwingNode snProdutosMaisAntigos;
    private SwingNode snCategoriasMaisVendida;
    private SwingNode snInteressePorArtesanato;

    private Fornecedor fornecedor;
    private List<EntidadeGrafico<Produto>> produto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fornecedor = new FornecedorDAO().pegarPorEmpresa(Sessao.pessoa.getEmpresa());
        snCategoriasMaisVendida = new SwingNode();
        snInteressePorArtesanato = new SwingNode();
        snProdutosMaisAntigos = new SwingNode();
        spCategoriaMaisVendida.setContent(snCategoriasMaisVendida);
        spInteressePorArtesanato.setContent(snInteressePorArtesanato);
        spProdutosMaisAntigos.setContent(snProdutosMaisAntigos);
        DefaultPieDataset dpdDados = new DefaultPieDataset();
        for (CategoriaProduto categoriaProduto : new CategoriaProdutoDAO().pegarTodos()) {
            List<CompraItem> compraitem = new CompraItemDAO().pegarPorFonecedorCategoria(fornecedor, categoriaProduto);
            dpdDados.setValue(categoriaProduto.toString(), compraitem.size());
        }
        JFreeChart jFreeChart = ChartFactory.createPieChart3D(ControlTranducao.traduzirPalavra("CATEGORIASMAISVENDIDAS"), dpdDados, false, false, Locale.ROOT);
        PiePlot3D piePlot3D = (PiePlot3D) jFreeChart.getPlot();
        piePlot3D.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}\n{2}"));
        ChartPanel categoriaMaisVendida = new ChartPanel(jFreeChart);
        Platform.runLater(() -> {
            snCategoriasMaisVendida.setContent(categoriaMaisVendida);
        });
        DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
        for (int i = Calendar.getInstance().get(Calendar.YEAR) - 10; i < Calendar.getInstance().get(Calendar.YEAR); i++) {
            dcdDados.addValue(new Random().nextDouble() * 100000, "Interesse", String.valueOf(i));
        }
        jFreeChart = ChartFactory.createLineChart(ControlTranducao.traduzirPalavra("interesse") + " " + ControlTranducao.traduzirPalavra("artesanato"), "", "", dcdDados, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel interesseArtesanato = new ChartPanel(jFreeChart);
        Platform.runLater(() -> {
            snInteressePorArtesanato.setContent(interesseArtesanato);
        });
        produto = new ArrayList<>();
        for (Produto produto : new ProdutoDAO().pegarPorFornecedor(fornecedor)) {
            if (new CompraItemDAO().pegarPorProduto(produto).isEmpty()) {
                List<Item> itens = new ItemDAO().pegarPorProduto(produto);
                if (!itens.isEmpty()) {
                    Item item = itens.get(0);
                    long quantidade = (new Date().getTime() - item.getDatacadastro().getTime()) / 1000 / 60 / 60 / 24;
                    this.produto.add(new EntidadeGrafico<>(produto, quantidade));
                }
            }
        }
        slMeta.setMax(produto.stream().mapToDouble(EntidadeGrafico::getValue).max().orElse(0));
        slMeta.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            produtosMaisAntigos();
        });
        Platform.runLater(() -> {
            produtosMaisAntigos();
        });
    }

    private void produtosMaisAntigos() {
        
        DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
        for (EntidadeGrafico<Produto> entidadeGrafico : produto) {
            if (entidadeGrafico.getValue() >= slMeta.getValue()) {
                dcdDados.addValue(entidadeGrafico.getValue(), "Dias/Acima do esperado", entidadeGrafico.toString());
            } else {
                dcdDados.addValue(entidadeGrafico.getValue(), "Dias", entidadeGrafico.toString());
            }
        }
        JFreeChart jFreeChart = ChartFactory.createBarChart(ControlTranducao.traduzirPalavra("PRODUTOSMAISANTIGOS"), "", "", dcdDados, PlotOrientation.VERTICAL, false, false, false);
        jFreeChart.getCategoryPlot().getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{0}", NumberFormat.getCurrencyInstance()));
        jFreeChart.getCategoryPlot().addRangeMarker(new ValueMarker(slMeta.getValue(), Color.CYAN, new BasicStroke(1.0f)));
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        snProdutosMaisAntigos.setContent(chartPanel);
    }

}
