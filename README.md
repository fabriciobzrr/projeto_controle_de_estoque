# 📦 Controle de Estoque - Sistema Java

![Java Version](https://img.shields.io/badge/Java-25-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## 📌 Sobre o Projeto

Sistema de gerenciamento de estoque desenvolvido em Java, permitindo o cadastro de produtos, controle de entrada e saída, busca por nome/fornecedor, listagem ordenada por preço, alerta de estoque baixo e cálculo do valor total do estoque.

Este projeto é o segundo de uma série de 10 projetos que estou desenvolvendo para consolidar meus conhecimentos em Java, aplicando conceitos de Programação Orientada a Objetos (POO), Stream API, tratamento de exceções e boas práticas de organização de código.

## 🎯 Funcionalidades

- ✅ Cadastrar produtos (com validação de dados)
- 📥 Entrada de estoque (aumentar quantidade)
- 📤 Saída de estoque (diminuir quantidade com validação)
- 🔍 Buscar produto por nome (busca parcial, case insensitive)
- 🏭 Buscar produto por fornecedor (busca parcial, case insensitive)
- 📋 Listar todos os produtos
- 💰 Listar produtos do mais caro para o mais barato
- ⚠️ Alertar produtos com estoque abaixo do limite definido
- 💵 Calcular valor total do estoque (preço × quantidade)

## 🏗️ Estrutura do Projeto

```
projeto-controle-estoque/
│
├── src/
│   ├── application/
│   │   └── Program.java
│   ├── entities/
│   │   └── Produto.java
│   ├── services/
│   │   └── EstoqueService.java
│   ├── enums/
│   │   └── OpcoesMenu.java
│   └── exceptions/
│       └── EstoqueException.java
│
├── .gitignore
└── README.md
```


## 🛠️ Tecnologias Utilizadas

- ☕ Java 25 LTS
- 🛠️ IntelliJ IDEA

## 📚 Conceitos Aplicados

| Conceito | Como foi aplicado |
|----------|------------------|
| **Programação Orientada a Objetos** | Classes, objetos, encapsulamento, construtores |
| **Enum** | `OpcoesMenu` para organizar o menu principal |
| **Stream API** | `filter`, `sorted`, `mapToDouble`, `sum`, `collect` |
| **Busca parcial** | `toUpperCase()` + `contains()` para busca case insensitive |
| **Validação em camadas** | Program (validação de entrada) + Service (regras de negócio) |
| **Defesa profunda** | Service valida mesmo com Program validando |
| **Tratamento de exceções** | Exceção personalizada (`EstoqueException`) e `try-catch` |
| **Listas** | `ArrayList` com cópia defensiva (`new ArrayList<>(lista)`) |

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 21 ou superior instalado
- IntelliJ IDEA (ou qualquer IDE Java)

### Passos

1. Clone o repositório:
```bash git clone https://github.com/seu-usuario/Projeto02-Controle-Estoque.git```

2. Abra o projeto no IntelliJ IDEA

3. Execute a classe Program.java localizada no pacote application

4. O menu principal será exibido no console:

-------- Menu Principal --------
1 - Cadastrar Produto
2 - Entrada de Estoque
3 - Saída de Estoque
4 - Buscar Produto Por Nome
5 - Buscar Produto Por Fornecedor
6 - Listar Todos os Produtos
7 - Listar Produtos Mais Caros
8 - Produtos com Estoque Baixo
9 - Valor Total do Estoque
0 - Sair

## 📊 Exemplo de Uso

## Cadastrando um Produto
```
-------- Cadastrar Produto --------

Nome do Produto: Notebook Gamer
Preço do Produto: R$ 3500,00
Quantidade do Produto (ENTER para zero): 10
Nome do Fornecedor: TechFornecedor

-------- Resumo do item --------
| #1 | Notebook Gamer | R$ 3500,00 | Qtde: 10 | Fornecedor: TechFornecedor |

Confirmar cadastramento do item? (S/N): S

✅ Produto cadastrado com sucesso!
```

## Entrada no Estoque
```
---------- Entrada de Estoque --------
ID do produto: 1

Produto encontrado: Notebook Gamer
Quantidade disponível: 10

Quantidade a adicionar: 5

Confirmar entrada de 5 unidades? (S/N): S

✅ Entrada realizada com sucesso!
Quantidade disponível: 15
```

## Produtos com Estoque Baixo
```
-------- Listar Produtos Com Estoque Baixo --------
Estoque Mínimo Limite: 3

📦 Produtos com estoque baixo:
| #2 | Mouse | R$ 50,00 | Qtde: 2 | Fornecedor: PerifericosLtda |

Registros: 1
```

## 🔒 Validações Implementadas

### Camada de Apresentação (Program)
| Campo | Validação | Mensagem de erro |
|-------|-----------|------------------|
| Nome | Não pode estar vazio | "Nome do produto não pode estar vazio!" |
| Preço | Deve ser maior que zero | "Preço deve ser maior que zero!" |
| Quantidade | Não pode ser negativa | "Quantidade não pode ser negativa!" |
| Fornecedor | Não pode estar vazio | "Nome do fornecedor não pode estar vazio!" |
| ID do produto | Deve existir | "Produto com ID X não localizado!" |

### Camada de Negócio (Service)
| Regra | Validação | Mensagem de erro |
|-------|-----------|------------------|
| Preço do produto | Não pode ser negativo | "O preço não pode ser negativo!" |
| Quantidade em estoque | Não pode ser negativa | "Quantidade não pode ser negativa!" |
| Saída de estoque | Não pode ser maior que disponível | "Estoque insuficiente!" |

## 🛡️ Validação em Duas Camadas (Defesa Profunda)

O projeto implementa o conceito de **defesa profunda**:

| Camada | Responsabilidade | Como valida |
|--------|------------------|-------------|
| **Program** | Validar entrada do usuário | `if + IO.println + return` |
| **Service** | Validar regras de negócio | `throw new EstoqueException()` |

## 👨‍💻 Autor

**Fabrício Bezerra**

- LinkedIn: [LinkedIn](https://linkedin.com/in/fabriciobzrr)
- GitHub: [GitHub](https://github.com/fabriciobzrr)

## 🙏 Agradecimentos

- [Nélio Alves](https://www.udemy.com/course/java-curso-completo/?src=sac&kw=Java+Completo) - Curso Java COMPLETO Programação Orientada a Objetos + Projetos (Udemy)

## 📝 Licença

Este projeto está sob a licença MIT.

---

⭐ Se este projeto te ajudou, considere dar uma estrela no GitHub!
