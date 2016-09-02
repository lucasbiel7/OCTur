/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CidadeDAO;
import br.com.OCTur.control.DAO.PassagemDAO;
import br.com.OCTur.control.util.DateFormatter;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.model.EntidadeGrafico;
import br.com.OCTur.model.entity.Cidade;
import br.com.OCTur.model.entity.Pais;
import br.com.OCTur.model.entity.Passagem;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ClassificacaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private TableView<EntidadeGrafico<Cidade>> tvCidade;
    @FXML
    private TableColumn<EntidadeGrafico<Cidade>, Integer> tcClassificacao;
    @FXML
    private TableColumn<EntidadeGrafico<Cidade>, Double> tcNota;
    @FXML
    private TableColumn<EntidadeGrafico<Cidade>, Cidade> tcCidade;
    @FXML
    private TableColumn<EntidadeGrafico<Cidade>, Pais> tcPais;
    @FXML
    private ScrollPane spContainer;
    private SwingNode snGrafico;
    private ObservableList<EntidadeGrafico<Cidade>> cidades = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        snGrafico = new SwingNode();
        spContainer.setContent(snGrafico);
        tcCidade.setCellValueFactory((TableColumn.CellDataFeatures<EntidadeGrafico<Cidade>, Cidade> param) -> new SimpleObjectProperty<>(param.getValue().getEntidade()));
        tcClassificacao.setCellValueFactory((TableColumn.CellDataFeatures<EntidadeGrafico<Cidade>, Integer> param) -> new SimpleObjectProperty<>(cidades.indexOf(param.getValue()) + 1));
        tcPais.setCellValueFactory((TableColumn.CellDataFeatures<EntidadeGrafico<Cidade>, Pais> param) -> new SimpleObjectProperty<>(param.getValue().getEntidade().getEstado().getPais()));
        tcNota.setCellValueFactory(new PropertyValueFactory<>("value"));
        tcNota.setCellFactory((TableColumn<EntidadeGrafico<Cidade>, Double> param) -> new TableCell<EntidadeGrafico<Cidade>, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(NumberFormatter.duasCasas(item));
                }
            }
        });
        tvCidade.setItems(cidades);
    }

    @FXML
    private void dpDatasActionEvent(ActionEvent actionEvent) {
        if (dpInicio.getValue() != null && dpFim.getValue() != null) {
            Date inicio = DateFormatter.toDate(dpInicio.getValue());
            Date fim = DateFormatter.toDate(dpFim.getValue());
            cidades.clear();
            for (Cidade cidade : new CidadeDAO().pegarTodos()) {
                double total = new PassagemDAO().pegarPorDestinoInicioFim(cidade, inicio, fim).stream().mapToDouble(Passagem::getNumeropessoas).sum();
                cidades.add(new EntidadeGrafico<>(cidade, total));
            }
            double max = cidades.stream().mapToDouble(EntidadeGrafico::getValue).max().orElse(0);
            for (EntidadeGrafico<Cidade> cidade : cidades) {
                cidade.setValue(cidade.getValue() * 100 / max);
            }
            cidades.sort((EntidadeGrafico<Cidade> o1, EntidadeGrafico<Cidade> o2) -> Double.compare(o1.getValue(), o2.getValue()) * -1);
        }
    }

    @FXML
    private void tvCidadeMouseReleased(MouseEvent mouseEvent) {
        if (tvCidade.getSelectionModel().getSelectedItem() != null) {
            DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
            Date inicio = DateFormatter.toDate(dpInicio.getValue());
            Date fim = DateFormatter.toDate(dpFim.getValue());
            Calendar inicioCalendar = Calendar.getInstance();
            inicioCalendar.setTime(inicio);
            while (inicioCalendar.getTime().before(fim) || inicioCalendar.getTime().equals(fim)) {
                dcdDados.addValue(new PassagemDAO().pegarPorDestinoInicioFim(tvCidade.getSelectionModel().getSelectedItem().getEntidade(),
                        inicioCalendar.getTime(), inicioCalendar.getTime()).stream().mapToDouble(Passagem::getNumeropessoas).sum(), "Pessoas", DateFormatter.toDay(inicioCalendar.getTime()));
                inicioCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            JFreeChart jFreeChart = ChartFactory.createLineChart("", "", "", dcdDados, PlotOrientation.VERTICAL, false, false, false);
            ChartPanel chartPanel = new ChartPanel(jFreeChart);
            snGrafico.setContent(chartPanel);
        }
    }

}
