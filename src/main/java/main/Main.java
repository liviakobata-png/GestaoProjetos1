package main;

import model.*;
import service.GerenciadorService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static GerenciadorService service = new GerenciadorService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Carga inicial de dados para facilitar testes do professor
        inicializarDadosFicticios();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE GESTÃO DE PROJETOS E EQUIPES ");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Cadastrar Projeto");
            System.out.println("3 - Cadastrar Equipe e Alocar Membros");
            System.out.println("4 - Relatório de Projetos");
            System.out.println("5 - Relatório de Equipes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: menuCadastrarUsuario(); break;
                    case 2: menuCadastrarProjeto(); break;
                    case 3: menuCadastrarEquipe(); break;
                    case 4: relatorioProjetos(); break;
                    case 5: relatorioEquipes(); break;
                    case 0: System.out.println("Encerrando o sistema..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private static void menuCadastrarUsuario() {

        System.out.println("\n--- CADASTRO DE USUÁRIO ---");
        System.out.print("Nome Completo: "); String nome = scanner.nextLine();
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("E-mail: "); String email = scanner.nextLine();
        System.out.print("Cargo: "); String cargo = scanner.nextLine();
        System.out.print("Login: "); String login = scanner.nextLine();
        System.out.print("Senha: "); String senha = scanner.nextLine();

        System.out.println("Perfil: 1 - Administrador | 2 - Gerente | 3 - Colaborador");
        System.out.print("Escolha: ");
        int perfilOpcao = Integer.parseInt(scanner.nextLine());
        PerfilUsuario perfil = PerfilUsuario.COLABORADOR;
        if (perfilOpcao == 1) perfil = PerfilUsuario.ADMINISTRADOR;
        else if (perfilOpcao == 2) perfil = PerfilUsuario.GERENTE;

        Usuario novoUsuario = new Usuario(nome, cpf, email, cargo, login, senha, perfil);
        service.cadastrarUsuario(novoUsuario);
        System.out.println("✅ Usuário cadastrado com sucesso!");
    }

    private static void menuCadastrarProjeto() {
        System.out.println("\n--- CADASTRO DE PROJETO ---");
        List<Usuario> gerentes = service.listarGerentes();

        if (gerentes.isEmpty()) {
            System.out.println("❌ Não é possível criar um projeto. Cadastre ao menos um GERENTE primeiro.");
            return;
        }

        System.out.print("Nome do Projeto: "); String nome = scanner.nextLine();
        System.out.print("Descrição: "); String descricao = scanner.nextLine();
        System.out.print("Data de Início (DD/MM/AAAA): "); String inicio = scanner.nextLine();
        System.out.print("Data de Término Prevista (DD/MM/AAAA): "); String termino = scanner.nextLine();

        System.out.println("Status: 1-Planejado | 2-Em Andamento | 3-Concluído | 4-Cancelado");
        System.out.print("Escolha: ");
        int statusOp = Integer.parseInt(scanner.nextLine());
        StatusProjeto status = StatusProjeto.PLANEJADO;
        if (statusOp == 2) status = StatusProjeto.EM_ANDAMENTO;
        else if (statusOp == 3) status = StatusProjeto.CONCLUIDO;
        else if (statusOp == 4) status = StatusProjeto.CANCELADO;

        System.out.println("Selecione o Gerente Responsável:");
        for (int i = 0; i < gerentes.size(); i++) {
            System.out.println(i + " - " + gerentes.get(i).getNomeCompleto());
        }
        System.out.print("Escolha o número do gerente: ");
        int gerenteIndex = Integer.parseInt(scanner.nextLine());
        Usuario gerente = gerentes.get(gerenteIndex);

        Projeto novoProjeto = new Projeto(nome, descricao, inicio, termino, status, gerente);
        service.cadastrarProjeto(novoProjeto);
        System.out.println("✅ Projeto cadastrado com sucesso!");
    }

    private static void menuCadastrarEquipe() {
        System.out.println("\n--- CADASTRO DE EQUIPE ---");
        List<Usuario> todosUsuarios = service.listarUsuarios();

        if (todosUsuarios.isEmpty()) {
            System.out.println("❌ Cadastre usuários antes de criar uma equipe.");
            return;
        }

        System.out.print("Nome da Equipe: "); String nome = scanner.nextLine();
        System.out.print("Descrição da Equipe: "); String descricao = scanner.nextLine();

        Equipe novaEquipe = new Equipe(nome, descricao);

        System.out.println("--- Alocação de Membros (Digite -1 para encerrar a seleção) ---");
        while (true) {
            System.out.println("Usuários disponíveis:");
            for (int i = 0; i < todosUsuarios.size(); i++) {
                System.out.println(i + " - " + todosUsuarios.get(i).getNomeCompleto() + " (" + todosUsuarios.get(i).getPerfil() + ")");
            }
            // LINHA NOVA: Adiciona a opção visual para o usuário saber o que fazer
            System.out.println("-1 - Finalizar alocação e voltar");

            System.out.print("Digite a opção desejada: ");

            try {
                int id = Integer.parseInt(scanner.nextLine());

                if (id == -1) {
                    break;
                }

                if (id >= 0 && id < todosUsuarios.size()) {
                    Usuario selecionado = todosUsuarios.get(id);

                    if (novaEquipe.getMembros().contains(selecionado)) {
                        System.out.println("⚠️ Este usuário já foi adicionado a esta equipe!");
                    } else {
                        novaEquipe.adicionarMembro(selecionado);
                        System.out.println("➕ " + selecionado.getNomeCompleto() + " adicionado com sucesso!");
                    }
                } else {
                    System.out.println("❌ ID inválido! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite um número de ID válido ou -1.");
            }
            System.out.println();
        }

        service.cadastrarEquipe(novaEquipe);
        System.out.println("✅ Equipe registrada com sucesso!");
    }

    private static void relatorioProjetos() {
        System.out.println("\n=======================================================");
        System.out.println("            RELATÓRIO DE ACOMPANHAMENTO                 ");
        System.out.println("=======================================================");
        List<Projeto> projetos = service.listarProjetos();

        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
            return;
        }

        for (Projeto p : projetos) {
            System.out.printf("Projeto: %s\n", p.getNome());
            System.out.printf("Descrição: %s\n", p.getDescricao());
            System.out.printf("Período: %s até %s\n", p.getDataInicio(), p.getDataTerminoPrevista());
            System.out.printf("Status: %s\n", p.getStatus());
            System.out.printf("Gerente Responsável: %s\n", p.getGerenteResponsavel().getNomeCompleto());
            System.out.println("-------------------------------------------------------");
        }
    }

    private static void relatorioEquipes() {
        System.out.println("\n=======================================================");
        System.out.println("            RELATÓRIO E COMPOSIÇÃO DE EQUIPES          ");
        System.out.println("=======================================================");
        List<Equipe> equipes = service.listarEquipes();

        if (equipes.isEmpty()) {
            System.out.println("Nenhuma equipe cadastrada.");
            return;
        }

        for (Equipe e : equipes) {
            System.out.printf("Equipe: %s (%s)\n", e.getNome(), e.getDescricao());
            System.out.println("Membros alocados:");
            if (e.getMembros().isEmpty()) {
                System.out.println("  [Nenhum membro alocado nesta equipe]");
            } else {
                for (Usuario m : e.getMembros()) {
                    System.out.printf("  - %s | Cargo: %s (%s)\n", m.getNomeCompleto(), m.getCargo(), m.getPerfil());
                }
            }
            System.out.println("-------------------------------------------------------");
        }
    }

    private static void inicializarDadosFicticios() {
        // Gerentes
        Usuario g1 = new Usuario("Ana Costa", "111", "ana@empresa.com", "Gerente de TI", "ana.costa", "123", PerfilUsuario.GERENTE);
        Usuario g2 = new Usuario("Carlos Silva", "222", "carlos@empresa.com", "Gerente de Projetos", "carlos.s", "123", PerfilUsuario.GERENTE);
        // Colaboradores
        Usuario c1 = new Usuario("Livia Angelozzi", "333", "livia@empresa.com", "Desenvolvedora Java Junior", "livia.dev", "123", PerfilUsuario.COLABORADOR);
        Usuario c2 = new Usuario("Bruno Rocha", "444", "bruno@empresa.com", "Designer UX", "bruno.ux", "123", PerfilUsuario.COLABORADOR);

        service.cadastrarUsuario(g1);
        service.cadastrarUsuario(g2);
        service.cadastrarUsuario(c1);
        service.cadastrarUsuario(c2);

        // Projetos Iniciais
        service.cadastrarProjeto(new Projeto("Sistema de Gestão", "Desenvolvimento de MVP de software corporativo", "01/06/2026", "20/12/2026", StatusProjeto.EM_ANDAMENTO, g1));

        // Equipes Iniciais
        Equipe e1 = new Equipe("Alpha Squad", "Squad focada no desenvolvimento core");
        e1.adicionarMembro(c1);
        e1.adicionarMembro(c2);
        service.cadastrarEquipe(e1);
    }
}
