package org.example.suporte.service;

import jakarta.validation.Valid;
import org.example.suporte.dto.ClienteDTO;
import org.example.suporte.exception.RecursoNaoEncontradoException;
import org.example.suporte.exception.ValidacaoException;
import org.example.suporte.model.Cliente;
import org.example.suporte.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente criar(@Valid ClienteDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ValidacaoException("E-mail já cadastrado para outro cliente");
        }
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());
        return repository.save(cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        repository.delete(cliente);
    }
}
