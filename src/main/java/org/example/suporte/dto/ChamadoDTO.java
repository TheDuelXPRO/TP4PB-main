package org.example.suporte.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChamadoDTO {

    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotBlank(message = "Prioridade é obrigatória")
    private String prioridade;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String clienteEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }
}
