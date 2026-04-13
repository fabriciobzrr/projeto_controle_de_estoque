package entities;

import exception.EstoqueException;

public class Produto {
    private static int contadorId = 1;

    private int id;
    private String nome;
    private double preco;
    private int quantidade;
    private String fornecedor;

    public Produto(String nome, Double preco, Integer quantidade, String fornecedor) {
        this.id = contadorId++;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void adicionarEstoque(int quantidade) {
        this.quantidade += quantidade;
    }

    public void removerEstoque(int quantidade) {
        if (quantidade > this.quantidade) {
            throw new EstoqueException("Estoque insuficiente! Estoque disponível: " + this.quantidade);
        }

        this.quantidade -= quantidade;
    }

    public double calcularEstoque() {
        return quantidade * preco;
    }

    @Override
    public String toString() {
        return String.format("| #%d | %s | R$ %.2f | Qtde: %d | Fornecedor: %s |", id, nome, preco, quantidade, fornecedor);
    }
}
