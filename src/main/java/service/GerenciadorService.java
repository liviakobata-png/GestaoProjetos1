package service;
import model.Usuario;
import model.Projeto;
import model.Equipe;

import java.util.ArrayList;
import java.util.List;
public class GerenciadorService {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Projeto> projetos = new ArrayList<>();
    private List<Equipe> equipes = new ArrayList<>();

    // Métodos de Usuário
    public void cadastrarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public List<Usuario> listarGerentes() {
        List<Usuario> gerentes = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getPerfil() == model.PerfilUsuario.GERENTE) {
                gerentes.add(u);
            }
        }
        return gerentes;
    }

    // Métodos de Projeto
    public void cadastrarProjeto(Projeto p) {
        projetos.add(p);
    }

    public List<Projeto> listarProjetos() {
        return projetos;
    }

    // Métodos de Equipe
    public void cadastrarEquipe(Equipe e) {
        equipes.add(e);
    }

    public List<Equipe> listarEquipes() {
        return equipes;
    }
}
