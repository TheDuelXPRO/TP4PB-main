package org.example.suporte.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    private Long id;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    @Size(max = 255, message = "E-mail deve ter no máximo 255 caracteres")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
