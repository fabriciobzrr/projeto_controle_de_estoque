package services;

import entities.Produto;
import exception.EstoqueException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstoqueService {
    private List<Produto> produtos;

    public EstoqueService() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        if(produto.getPreco() <= 0) {
            throw new EstoqueException("O preço não pode ser negativo!");
        }

        if(produto.getQuantidade() < 0) {
            throw new EstoqueException("Quantidade não pode ser negativa!");
        }

        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new EstoqueException("O nome do produto não pode estar vazio!");
        }

        if (produto.getFornecedor() == null || produto.getFornecedor().trim().isEmpty()) {
            throw new EstoqueException("O nome do fornecedor não pode estar vazio!");
        }

        produtos.add(produto);
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public List<Produto> buscarProdutoPorNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
    }

    public List<Produto> buscarProdutoPorFornecedor(String fornecedor) {
        return produtos.stream()
                .filter(p -> p.getFornecedor().toUpperCase().contains(fornecedor.toUpperCase()))
                .collect(Collectors.toList());
    }

    public void entradaEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new EstoqueException("A quantidade deve ser positiva!");
        }

        Produto produto = buscarPorId(id);
        produto.adicionarEstoque(quantidade);
    }

    public void saidaEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new EstoqueException("A quantidade deve ser positiva!");
        }

        Produto produto = buscarPorId(id);
        if (quantidade > produto.getQuantidade()) {
            throw new EstoqueException("A quantidade no estoque deve ser maior que quantidade de saída!");
        }

        produto.removerEstoque(quantidade);
    }

    public List<Produto> estoqueBaixo(int limite) {
        return produtos.stream()
                .filter(p -> p.getQuantidade() < limite)
                .collect(Collectors.toList());
    }

    public double valorTotalEstoque() {
        return produtos.stream()
                .mapToDouble(p -> p.calcularEstoque())
                .sum();
    }

    public List<Produto> produtosMaisCaros() {
        return produtos.stream()
                .sorted((p1, p2) -> Double.compare(p2.getPreco(), p1.getPreco()))
                .collect(Collectors.toList());
    }

    private Produto buscarPorId(int id) {
        return produtos.stream()
                .filter(p1 -> p1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EstoqueException("Produto com ID: " + id + " não encontrado!"));
    }
}
