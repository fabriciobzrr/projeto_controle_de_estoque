package application;

import entities.Produto;
import enums.OpcoesMenu;
import exception.EstoqueException;
import services.EstoqueService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static final Scanner input = new Scanner(System.in);
    private static final EstoqueService estoqueService = new EstoqueService();
    public static void main (String[] args) {
        IO.println("\n====== CONTROLE DE ESTOQUE ======");

        int opcao;
        do {
            mostrarMenu();
            opcao = input.nextInt();
            input.nextLine();

            if (opcao < 1 || opcao > OpcoesMenu.values().length) {
                IO.println("\nOpção inválida! Escolha uma opção (1 a " + OpcoesMenu.values().length + ")");
                return;
            }

            IO.println("Executando: " + OpcoesMenu.values()[opcao - 1].getDescricao() + "...");

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> entradaEstoque();
                case 3 -> saidaEstoque();
                case 4 -> buscaPorNome();
                case 5 -> buscaPorFornecedor();
                case 6 -> listarTodosProdutos();
                case 7 -> listarProdutosMaisCaros();
                case 8 -> listarProdutosComEstoqueBaixo();
                case 9 -> mostrarValorTotalEstoque();
                case 0 -> IO.println("\nEncerrando aplicação...\n---------------------------------------");
                default -> IO.println("\nOpção inválida!");
            }
        } while (opcao != 0);

    }

    public static void mostrarMenu() {
        IO.println("\n-------- Menu Principal --------");
        int count = 1;
        for (OpcoesMenu opcao : OpcoesMenu.values()) {
            IO.println(count + " - " + opcao.getDescricao());
            count++;
        }
        IO.println("0 - Sair");
        IO.println("--------------------------------");
        IO.print("\nSelecione: ");
    }

    public static Produto coletarDadosProduto() {
        IO.println("\n-------- Cadastrar Produto --------");

        // Captura nome
        IO.print("\nNome do Produto: ");
        String nome = input.nextLine().trim();

        if (nome.isEmpty()) {
            IO.println("\nNome do produto não pode estar vazio!");
            return null;
        }

        // Captura preço
        IO.print("Preço do Produto: R$ ");
        double preco = input.nextDouble();
        input.nextLine();

        if (preco <= 0) {
            IO.println("\nPreço deve ser maior que zero!");
            return null;
        }

        // Captura quantidade
        IO.print("Quantidade do Produto (ENTER para zero): ");
        String quantidadeTexto = input.nextLine();

        int quantidade;
        if (quantidadeTexto.isEmpty()) {
            quantidade = 0;
            IO.println("Quantidade: " + quantidade);
        } else {
            try {
                quantidade = Integer.parseInt(quantidadeTexto);
            } catch (NumberFormatException err) {
                IO.println("\nErro: quantidade inválida! Digite um número válido!");
                return null;
            }
        }

        if (quantidade < 0) {
            IO.println("\nQuantidade não pode ser negativa!");
            return null;
        }

        // Captura o fornecedor
        IO.print("Nome do Fornecedor: ");
        String fornecedor = input.nextLine();

        if (fornecedor.isEmpty()) {
            IO.println("\nNome do fornecedor não pode estar vazio!");
            return null;
        }

        // Instancia e retorna o produto
        return new Produto(nome, preco, quantidade, fornecedor);
    }
    public static boolean confirmacao(String mensagem) {
        IO.print(mensagem);
        String opcao = input.nextLine();

        if (opcao.isEmpty()) {
            IO.println("\nOpção inválida! Retornando ao menu principal!");
            return false;
        }

        char primeiraLetra = opcao.toUpperCase().charAt(0);
        return primeiraLetra == 'S';
    }
    public static void cadastrarProduto() {
        Produto produto = coletarDadosProduto();
        if (produto == null) return;

        IO.println("\n-------- Resumo do item --------");
        IO.println(produto.toString());

        if (!confirmacao("\nConfirmar cadastramento do item? (S/N): ")) {
            IO.println("\nCadastro cancelado!");
            return;
        }

        try {
            estoqueService.adicionarProduto(produto);
            IO.println("\nProduto cadastrado com sucesso!");
        } catch (EstoqueException err) {
            IO.println("\nErro: " + err.getMessage());
        }

        if(confirmacao("\nDeseja cadastrar um novo produto? (S/N): ")) {
            cadastrarProduto();
        } else {
            IO.println("\nRetornando ao menu principal!");
        }
    }

    public static void entradaEstoque() {
        IO.println("\n-------- Entrada de Estoque --------");

        try {
            IO.print("ID do produto: ");
            int id = input.nextInt();
            input.nextLine();

            List<Produto> produtos = estoqueService.listarProdutos();

            if (produtos.isEmpty()) {
                IO.println("\nNenhum produto cadastrado até o momento!");
                return;
            }

            Produto produtoEncontrado = null;
            for (Produto produto : produtos) {
                if (produto.getId() == id) {
                    produtoEncontrado = produto;
                    break;
                }
            }

            if (produtoEncontrado == null) {
                IO.println("\nProduto com ID: " + id + "não localizado!");
                return;
            }

            IO.println("\nProduto encontrado: " + produtoEncontrado.getNome());
            IO.println("Quantidade disponível: " +  produtoEncontrado.getQuantidade());

            IO.print("\nQuantidade a adicionar: ");
            int quantidade = input.nextInt();
            input.nextLine();

            if (quantidade <= 0) {
                IO.println("\nQuantidade deve ser positiva!");
                return;
            }

            if(!confirmacao("\nConfirmar entrada de " + quantidade + " unidades? (S/N) ")) {
                IO.println("\nOperação cancelada!");
                return;
            }

            produtoEncontrado.adicionarEstoque(quantidade);

            IO.println("\nEntrada realizada com sucesso!");

            IO.println("\nQuantidade disponível: " + produtoEncontrado.getQuantidade());
        } catch (EstoqueException err) {
            IO.println("\nErro: " + err.getMessage());
        } catch (InputMismatchException err) {
            IO.println("\nErro: digite um valor válido!");
        }
    }

    public static void saidaEstoque() {
        IO.println("\n-------- Saída de Estoque-------");

        try {
            IO.print("ID do produto: ");
            int id = input.nextInt();
            input.nextLine();

            List<Produto> produtos = estoqueService.listarProdutos();

            if (produtos.isEmpty()) {
                IO.println("\nNenhum produto cadastrado até o momento!");
                return;
            }

            Produto produtoEncontrado = null;
            for (Produto produto : produtos) {
                if (produto.getId() == id) {
                    produtoEncontrado = produto;
                    break;
                }
            }

            if (produtoEncontrado == null) {
                IO.println("\nProduto com ID: " + id + "não localizado!");
                return;
            }

            IO.println("\nProduto encontrado: " + produtoEncontrado.getNome());
            IO.println("Quantidade disponível: " +  produtoEncontrado.getQuantidade());

            IO.print("\nQuantidade a remover: ");
            int quantidade = input.nextInt();
            input.nextLine();

            if (quantidade <= 0) {
                IO.println("\nQuantidade deve ser positiva!");
                return;
            }

            if (quantidade > produtoEncontrado.getQuantidade()) {
                IO.println("\nEstoque insuficiente! Disponível: " + produtoEncontrado.getQuantidade());
                return;
            }

            if(!confirmacao("\nConfirmar saída de " + quantidade + " unidades? (S/N) ")) {
                IO.println("\nOperação cancelada!");
            }

            produtoEncontrado.removerEstoque(quantidade);

            IO.println("\nSaída realizada com sucesso!");

            IO.println("\nQuantidade disponível: " + produtoEncontrado.getQuantidade());
        } catch (EstoqueException err) {
            IO.println("\nErro: " + err.getMessage());
        } catch (InputMismatchException err) {
            IO.println("\nErro: digite um valor válido!");
        }
    }

    public static void buscaPorNome() {
        IO.println("\n-------- Buscar Produto Por Nome --------");

        IO.print("Nome do Produto: ");
        String nome = input.nextLine().trim();

        if (nome.isEmpty()) {
            IO.println("\nNome do produto não pode estar vazio!");
            return;
        }

        List<Produto> resultados = estoqueService.buscarProdutoPorNome(nome);

        if (resultados.isEmpty()) {
            IO.println("\nNenhum produto encontrado!");
            return;
        }

        IO.println("\n-------- Resultados Encontrados -------");
        for (Produto produto : resultados) {
            IO.println(produto.toString());
        }

        IO.println("\nRegistros: " + resultados.size());
    }

    public static void buscaPorFornecedor() {
        IO.println("\n-------- Buscar Produto Por Fornecedor --------");

        List<Produto> produtos = estoqueService.listarProdutos();

        if (produtos.isEmpty()) {
            IO.println("\nNenhum produto cadastrado até o momento!");
            return;
        }

        IO.print("Nome do Fornecedor: ");
        String fornecedor = input.nextLine().trim();
        if (fornecedor.isEmpty()) {
            IO.println("\nNome do fornecedor não pode estar vazio!");
            return;
        }

        List<Produto> resultados = estoqueService.buscarProdutoPorFornecedor(fornecedor);

        if (resultados.isEmpty()) {
            IO.println("\nNenhum produto cadastrado até o momento!");
            return;
        }

        IO.println("\n-------- Resultados Encontrados -------");
        for (Produto produto : resultados) {
            IO.println(produto.toString());
        }

        IO.println("\nRegistros: " + resultados.size());
    }

    public static void listarTodosProdutos() {
        IO.println("\n-------- Listar Todos os Produtos --------");

        List<Produto> produtos = estoqueService.listarProdutos();

        if (produtos.isEmpty()) {
            IO.println("\nNenhum produto cadastrado até o momento!");
            return;
        }

        for (Produto produto : produtos) {
            IO.println(produto.toString());
        }

        IO.println("\nRegistros: " + produtos.size());
    }

    public static void listarProdutosMaisCaros() {
        IO.println("\n-------- Listar Produtos Mais caros --------");

        List<Produto> produtos = estoqueService.produtosMaisCaros();

        if (produtos.isEmpty()) {
            IO.println("\nNenhum produto cadastrado até o momento!");
            return;
        }

        IO.println("\n-------- Resultados Mais caros --------");
        for (Produto produto : produtos) {
            IO.println(produto.toString());
        }

        IO.println("\nRegistros: " + produtos.size());
    }

    public static void listarProdutosComEstoqueBaixo() {
        IO.println("\n-------- Listar Produtos Com Estoque Baixo --------");
        try {
            IO.print("Estoque Minimo Limite: ");
            int limite = input.nextInt();
            input.nextLine();

            if (limite <= 0) {
                IO.println("\nLimite deve ser maior que zero!");
                return;
            }

            List<Produto> produtos = estoqueService.estoqueBaixo(limite);

            if (produtos.isEmpty()) {
                IO.println("\nNenhum produto com estoque abaixo de " + limite + "!");
                return;
            }

            IO.println("\n\uD83D\uDCE6 Produtos com estoque baixo:");
            for (Produto produto : produtos) {
                IO.println(produto.toString());
            }

            IO.println("\nRegistros: " + produtos.size());

        } catch (EstoqueException err) {
            IO.println("\nErro: " + err.getMessage());
        } catch (InputMismatchException err) {
            IO.println("\nErro: digite um valor válido!");
        }
    }

    public static void mostrarValorTotalEstoque() {
        IO.println("\n-------- Valor total do estoque -------");
        List<Produto> produtos = estoqueService.listarProdutos();

        if (produtos.isEmpty()) {
            IO.println("\nNenhum produto cadastrado até o momento!");
            return;
        }

        IO.println("\nTOTAL: R$ " + String.format("%.2f", estoqueService.valorTotalEstoque()));
        IO.println("\nRegistros: " + produtos.size());
    }
}
