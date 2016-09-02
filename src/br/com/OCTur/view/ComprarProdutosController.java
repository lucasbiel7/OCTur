/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.view;

import br.com.OCTur.control.DAO.CategoriaProdutoDAO;
import br.com.OCTur.control.DAO.CompraDAO;
import br.com.OCTur.control.DAO.CompraItemDAO;
import br.com.OCTur.control.DAO.ItemDAO;
import br.com.OCTur.control.DAO.ProdutoDAO;
import br.com.OCTur.control.FxMananger;
import br.com.OCTur.control.util.ControlTranducao;
import br.com.OCTur.control.util.Message;
import br.com.OCTur.control.util.NumberFormatter;
import br.com.OCTur.control.util.Sessao;
import br.com.OCTur.control.util.SpinnerInteger;
import br.com.OCTur.model.EntidadeGrafico;
import br.com.OCTur.model.entity.CategoriaProduto;
import br.com.OCTur.model.entity.Compra;
import br.com.OCTur.model.entity.CompraItem;
import br.com.OCTur.model.entity.Item;
import br.com.OCTur.model.entity.Produto;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ComprarProdutosController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private ComboBox<CategoriaProduto> cbCategoriaProduto;
    @FXML
    private CheckBox cbFiltrarCategoria;
    @FXML
    private GridPane gpProdutos;
    @FXML
    private GridPane gpCarrinho;
    @FXML
    private Spinner<Integer> spQuantidade;
    @FXML
    private AnchorPane apDetalhe;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescricao;
    @FXML
    private Label lbQuantidadeRestante;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbPreco;
    @FXML
    private Button btAdicionarCarrinho;
    @FXML
    private Button btPesquisa;

    private ObservableList<CategoriaProduto> categoriaProdutos = FXCollections.observableArrayList();
    private Produto produto;
    private List<EntidadeGrafico<Produto>> carrinho;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btPesquisa, new KeyCodeCombination(KeyCode.P, KeyCodeCombination.ALT_ANY)));
        });
        cbCategoriaProduto.setItems(categoriaProdutos);
        carrinho = new ArrayList<>();
        categoriaProdutos.setAll(new CategoriaProdutoDAO().pegarTodos());
        carregarProdutos(new ProdutoDAO().pegarTodos());
        apDetalhe.setVisible(false);
    }

    @FXML
    private void btPesquisaActionEvent(ActionEvent actionEvent) {
        List<Produto> produtos;
        if (cbFiltrarCategoria.isSelected()) {
            produtos = new ProdutoDAO().pegarPorNomeCategoria(tfPesquisa.getText(), cbCategoriaProduto.getSelectionModel().getSelectedItem());
        } else {
            produtos = new ProdutoDAO().pegarPorNome(tfPesquisa.getText());
        }
        carregarProdutos(produtos);
        apDetalhe.setVisible(false);
        btAdicionarCarrinho.setDisable(true);
    }

    @FXML
    private void btFinalizarCompraActionEvent(ActionEvent actionEvent) {
        double total = 0;
        for (EntidadeGrafico<Produto> entidadeGrafico : carrinho) {
            total += (entidadeGrafico.getEntidade().getPreco() * entidadeGrafico.getValue());
        }
        if (Message.showConfirm("", "Total: " + NumberFormatter.toMoney(total)+"Deseja continuar?")) {
            Compra compra = new Compra();
            compra.setData(new Date());
            compra.setPessoa(Sessao.pessoa);
            new CompraDAO().cadastrar(compra);
            for (EntidadeGrafico<Produto> carrinhoCompra : carrinho) {
                for (int i = 0; i < carrinhoCompra.getValue(); i++) {
                    for (Item item : new ItemDAO().pegarPorProduto(carrinhoCompra.getEntidade())) {
                        if (new CompraItemDAO().pegarPorItem(item).isEmpty()) {
                            CompraItem compraItem = new CompraItem();
                            compraItem.setCompra(compra);
                            compraItem.setItem(item);
                            compraItem.setProduto(carrinhoCompra.getEntidade());
                            new CompraItemDAO().cadastrar(compraItem);
                            break;
                        }
                    }
                }
            }
            Message.show("", ControlTranducao.traduzirPalavra("EVCOMPRAREALIZADA"), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btLimparActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel((ScrollPane) apPrincipal.getParent().getParent().getParent(), FxMananger.carregarComponente("ComprarProdutos"));
    }

    @FXML
    private void btAdicionarCarrinhoActionEvent(ActionEvent actionEvent) {
        if (carrinho.contains(new EntidadeGrafico<>(produto, 0))) {
            EntidadeGrafico<Produto> produtoAdicionado = carrinho.get(carrinho.indexOf(new EntidadeGrafico<>(produto, 0)));
            produtoAdicionado.setValue(produtoAdicionado.getValue() + spQuantidade.getValue());
        } else {
            carrinho.add(new EntidadeGrafico<>(produto, spQuantidade.getValue()));
        }
        carregarCarrinho();
        apDetalhe.setVisible(false);
        btAdicionarCarrinho.setDisable(true);
        Message.show("", ControlTranducao.traduzirPalavra("EVADICIONADOAOCARRINHO"), Alert.AlertType.INFORMATION);
    }

    private void carregarProdutos(List<Produto> produtos) {
        gpProdutos.getChildren().clear();
        int linhas = 0;
        int colunas = 0;
        for (Produto produto : produtos) {
            Label label = new Label();
            label.setText(produto.getNome() + "\n"
                    + NumberFormatter.toMoney(produto.getPreco()));
            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);

            if (produto.getFoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(produto.getFoto())));
            }
            label.setContentDisplay(ContentDisplay.TOP);
            label.setMinWidth(110);
            label.setMinHeight(125);
            label.setGraphic(imageView);
            label.setOnMouseReleased((MouseEvent event) -> {
                this.produto = produto;
                carregarProduto();
            });
            gpProdutos.add(label, colunas, linhas);
            colunas++;
            if (colunas > 4) {
                colunas = 0;
                linhas++;
            }
        }

    }

    private void carregarCarrinho() {
        gpCarrinho.getChildren().clear();
        int linha = 0;
        int coluna = 0;
        for (EntidadeGrafico<Produto> produto : carrinho) {
            Label label = new Label();
            label.setText(produto.getEntidade().getNome() + "\n"
                    + NumberFormatter.toMoney(produto.getEntidade().getPreco()) + "\n"
                    + "Quantidade: " + ((int) produto.getValue()) + "\n"
                    + "Total: " + NumberFormatter.toMoney(produto.getEntidade().getPreco() * produto.getValue()));
            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            if (produto.getEntidade().getFoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(produto.getEntidade().getFoto())));
            }
            label.setContentDisplay(ContentDisplay.TOP);
            label.setMinWidth(110);
            label.setMinHeight(150);
            label.setGraphic(imageView);
            gpCarrinho.add(label, coluna, linha);
            coluna++;
            if (coluna > 4) {
                coluna = 0;
                linha++;
            }
        }
    }

    private void carregarProduto() {
        if (produto != null) {
            lbTitulo.setText(produto.getNome());
            lbDescricao.setText("Fornecedor: " + produto.getFornecedor() + "\n"
                    + "Categoria produto: " + produto.getCategoria() + "\n"
                    + "Preço: " + NumberFormatter.toMoney(produto.getPreco()) + "\n"
            );
            List<Item> itens = new ItemDAO().pegarPorProduto(produto);
            itens.removeIf((Item t) -> !new CompraItemDAO().pegarPorItem(t).isEmpty());
            int quantidade = itens.size();
            if (carrinho.contains(new EntidadeGrafico<>(produto, 0))) {
                quantidade -= carrinho.get(carrinho.indexOf(new EntidadeGrafico<>(produto, 0))).getValue();
            }
            spQuantidade.setValueFactory(new SpinnerInteger(0, quantidade));
            lbPreco.setText(NumberFormatter.toMoney(produto.getPreco() * spQuantidade.getValue()));
            spQuantidade.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                lbPreco.setText(NumberFormatter.toMoney(produto.getPreco() * newValue));
            });
            lbQuantidadeRestante.setText(ControlTranducao.traduzirPalavra("de") + " " + quantidade + " " + ControlTranducao.traduzirPalavra("ITENS"));
            if (produto.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(produto.getFoto())));
            }
            btAdicionarCarrinho.setDisable(false);
            apDetalhe.setVisible(true);
        }
    }
}
