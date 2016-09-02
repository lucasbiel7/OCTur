/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.HotelDAO;
import br.com.OCTur.control.DAO.OrcamentoDAO;
import br.com.OCTur.control.DAO.ReservaDAO;
import br.com.OCTur.control.DAO.TipoQuartoDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.model.EntidadeGrafico;
import br.com.OCTur.model.entity.Hotel;
import br.com.OCTur.model.entity.Orcamento;
import br.com.OCTur.model.entity.Reserva;
import br.com.OCTur.model.entity.TipoQuarto;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class InfograficoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Spinner<Integer> spAno;
    @FXML
    private ScrollPane spContainer;
    private SwingNode snRelatorio;

    private Hotel hotel;

    private Date inicio;
    private Date fim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hotel = new HotelDAO().pegarPorEmpresa(Sessao.pessoa.getEmpresa());
        snRelatorio = new SwingNode();
        spContainer.setContent(snRelatorio);
        spAno.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                setValue(getValue() + 1);
            }
        });
        spAno.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            try {
                inicio = DateFormatter.toDate("01/01/" + spAno.getValue());
                fim = DateFormatter.toDate("01/01/" + (spAno.getValue() + 1));
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("hotel", hotel.getEmpresa().getNome());
                parametros.put("logo", getClass().getResourceAsStream(FxMananger.VIEW + "image/octur-logo.png"));
                parametros.put("occ", NumberFormatter.duasCasas(new Random().nextDouble() * 100) + "%");
                parametros.put("mpi", NumberFormatter.duasCasas(new Random().nextDouble() * 100) + "%");
                parametros.put("rgi", NumberFormatter.duasCasas(new Random().nextDouble() * 100) + "%");
                parametros.put("ari", NumberFormatter.duasCasas(new Random().nextDouble() * 100) + "%");
                parametros.put("revpar", NumberFormatter.duasCasas(new Random().nextDouble() * 100));
                parametros.put("adr", NumberFormatter.duasCasas(new Random().nextDouble() * 100));
                parametros.put("receitaMensal", receitaMensal());
                parametros.put("participacao", participacao());
                parametros.put("classificacaoHotel", classificacaoHotel());

                parametros.put("mes", hotel.getEmpresa().getNome() + " " + mes);
                JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(FxMananger.VIEW + "report/infografico.jasper"), parametros, new JREmptyDataSource());
                JRViewer jRViewer = new JRViewer(jasperPrint);
                snRelatorio.setContent(jRViewer);
            } catch (JRException e) {
                System.err.println(e.getMessage());
            }
        });
        spAno.getValueFactory().setValue(Calendar.getInstance().get(Calendar.YEAR));

    }

    @FXML
    private void btImprimirActionEvent(ActionEvent actionEvent) {
        Message.show("", ControlTranducao.traduzirPalavra("EVRELATORIOIMPRIMINDO"), Alert.AlertType.INFORMATION);
    }

    private String mes;
    private double MAIOR;
    private List<EntidadeGrafico<Hotel>> hoteis;

    private BufferedImage receitaMensal() {
        DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
        Calendar inicio = Calendar.getInstance();
        System.out.println(this.inicio);
        inicio.setTime(this.inicio);
        MAIOR = 0;
        while (inicio.getTime().before(fim)) {
            Calendar mes = Calendar.getInstance();
            mes.setTime(inicio.getTime());
            mes.add(Calendar.MONTH, 1);
            List<Reserva> reservas = new ReservaDAO().pegarPorHotelInicioFim(hotel, inicio.getTime(), mes.getTime());
            double total = 0;
            for (Reserva reserva : reservas) {
                long dias = (reserva.getFim().getTime() - reserva.getInicio().getTime()) / 1000 / 60 / 60 / 24;
                if (dias <= 0) {
                    dias = 1;
                }
                total += (reserva.getQuarto().getOrcamento().getPreco() * dias);
            }
            if (total > MAIOR) {
                MAIOR = total;
                this.mes = DateFormatter.toFullMonthName(inicio.getTime());
            }
            dcdDados.addValue(total, "Receita", DateFormatter.toMonthName(inicio.getTime()));
            inicio.add(Calendar.MONTH, 1);
        }
        JFreeChart jFreeChart = ChartFactory.createBarChart("", "", "", dcdDados, PlotOrientation.VERTICAL, false, false, false);
        return jFreeChart.createBufferedImage(555, 170);
    }

    private BufferedImage classificacaoHotel() {
        DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
        for (EntidadeGrafico<Hotel> hotel : hoteis.subList(0, hoteis.size() > 4 ? 4 : hoteis.size())) {
            for (TipoQuarto tipoQuarto : new TipoQuartoDAO().pegarTodos()) {
                Orcamento orcamento = new OrcamentoDAO().pegarPorTipoQuartoHotel(tipoQuarto, hotel.getEntidade());
                if (orcamento != null) {
                    List<Reserva> reservas = new ReservaDAO().pegarPorOrcamentoInicioFim(orcamento, inicio, fim);
                    double total = 0;
                    for (Reserva reserva : reservas) {
                        long dias = (reserva.getFim().getTime() - reserva.getInicio().getTime()) / 1000 / 60 / 60 / 24;
                        if (dias <= 0) {
                            dias = 1;
                        }
                        total += (reserva.getQuarto().getOrcamento().getPreco() * dias);
                    }
                    dcdDados.addValue(total * 100 / hotel.getValue(), tipoQuarto.toString(), hotel.toString());
                }
            }
        }

        JFreeChart jFreeChart = ChartFactory.createStackedBarChart("", "", "", dcdDados, PlotOrientation.HORIZONTAL, true, false, false);
        return jFreeChart.createBufferedImage(365, 251);
    }

    private BufferedImage participacao() {
        DefaultPieDataset dpdDados = new DefaultPieDataset();
        hoteis = new ArrayList<>();
        for (Hotel hotel : new HotelDAO().pegarTodos()) {
            List<Reserva> reservas = new ReservaDAO().pegarPorHotelInicioFim(hotel, inicio, fim);
            double total = 0;
            for (Reserva reserva : reservas) {
                long dias = (reserva.getFim().getTime() - reserva.getInicio().getTime()) / 1000 / 60 / 60 / 24;
                if (dias <= 0) {
                    dias = 1;
                }
                total += (reserva.getQuarto().getOrcamento().getPreco() * dias);
            }
            hoteis.add(new EntidadeGrafico<>(hotel, total));

        }
        hoteis.sort((EntidadeGrafico<Hotel> o1, EntidadeGrafico<Hotel> o2) -> Double.compare(o1.getValue(), o2.getValue()) * -1);
        for (EntidadeGrafico<Hotel> entidadeGrafico : hoteis.subList(0, hoteis.size() > 4 ? 4 : hoteis.size())) {
            dpdDados.setValue(entidadeGrafico.toString(), entidadeGrafico.getValue());
        }
        if (hoteis.size() > 4) {
            dpdDados.setValue("Outros", hoteis.subList(4, hoteis.size()).stream().mapToDouble(EntidadeGrafico::getValue).sum());
        }
        JFreeChart jFreeChart = ChartFactory.createRingChart("", dpdDados, true, false, Locale.ROOT);
        PiePlot piePlot = (PiePlot) jFreeChart.getPlot();
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
        return jFreeChart.createBufferedImage(175, 252);
    }

}
