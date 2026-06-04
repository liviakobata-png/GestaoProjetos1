package model;
import java.util.ArrayList;
import java.util.List;
public class Equipe {
    private String nome;
    private String descricao;
    private List<Usuario> membros; // Agregação

    // Construtor
    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

    // Método para adicionar membro individualmente
    public void adicionarMembro(Usuario usuario) {
        this.membros.add(usuario);
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Usuario> getMembros() { return membros; }
}
