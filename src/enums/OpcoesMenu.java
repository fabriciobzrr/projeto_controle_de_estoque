package enums;

public enum OpcoesMenu {
    CADASTRAR_PRODUTO("Cadastrar Produto"),
    ENTRADA_ESTOQUE("Entrada de Estoque"),
    SAIDA_ESTOQUE("Saída de Estoque"),
    BUSCAR_POR_NOME("Buscar Produto Por Nome"),
    BUSCAR_POR_FORNECEDOR("Buscar Produto Por Fornecedor"),
    LISTAR_PRODUTOS("Listar Todos os Produtos"),
    MAIS_CAROS("Listar Produtos Mais Caros"),
    ESTOQUE_BAIXO("Produtos com Estoque Baixo"),
    TOTAL_ESTOQUE("Valor Total do Estoque");

    private String descricao;

    OpcoesMenu(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
