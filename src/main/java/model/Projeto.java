package model;

public class Projeto {
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataTerminoPrevista;
    private StatusProjeto status;
    private Usuario gerenteResponsavel; // Associação Simples

    // Construtor
    public Projeto(String nome, String descricao, String dataInicio, String dataTerminoPrevista, StatusProjeto status, Usuario gerenteResponsavel) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerenteResponsavel = gerenteResponsavel;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataTerminoPrevista() { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(String dataTerminoPrevista) { this.dataTerminoPrevista = dataTerminoPrevista; }

    public StatusProjeto getStatus() { return status; }
    public void setStatus(StatusProjeto status) { this.status = status; }

    public Usuario getGerenteResponsavel() { return gerenteResponsavel; }
    public void setGerenteResponsavel(Usuario gerenteResponsavel) { this.gerenteResponsavel = gerenteResponsavel; }
}

