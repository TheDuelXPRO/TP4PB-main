package org.example.suporte.service;

import org.example.suporte.dto.ChamadoDTO;
import org.example.suporte.exception.ErroExternoException;
import org.example.suporte.exception.RecursoNaoEncontradoException;
import org.example.suporte.exception.ValidacaoException;
import org.example.suporte.model.Chamado;
import org.example.suporte.repository.ChamadoRepository;
import org.example.suporte.service.external.NotificacaoClient;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    private final ChamadoRepository repository;
    private final NotificacaoClient notificacaoClient;

    public ChamadoService(ChamadoRepository repository, NotificacaoClient notificacaoClient) {
        this.repository = repository;
        this.notificacaoClient = notificacaoClient;
    }

    public Chamado criarChamado(@Valid ChamadoDTO dto) {
        validarDados(dto);
        Chamado chamado = dtoToEntity(dto);
        chamado.setStatus("ABERTO");
        chamado.setDataAbertura(LocalDateTime.now());
        Chamado salvo = repository.save(chamado);
        try {
            notificacaoClient.enviarNotificacaoAbertura(salvo);
        } catch (IOException | TimeoutException e) {
            throw new ErroExternoException("Não foi possível notificar abertura do chamado", e);
        }
        return salvo;
    }

    public Chamado buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado não encontrado"));
    }

    public List<Chamado> listarTodos() {
        return repository.findAll();
    }

    public Chamado atualizar(Long id, @Valid ChamadoDTO dto) {
        validarDados(dto);
        Chamado existente = buscarPorId(id);
        existente.setTitulo(dto.getTitulo());
        existente.setDescricao(dto.getDescricao());
        existente.setPrioridade(dto.getPrioridade());
        existente.setClienteEmail(dto.getClienteEmail());
        existente.setStatus(dto.getStatus());
        if ("FECHADO".equals(dto.getStatus())) {
            existente.setDataFechamento(LocalDateTime.now());
        }
        return repository.save(existente);
    }

    public void excluir(Long id) {
        Chamado existente = buscarPorId(id);
        repository.delete(existente);
    }

    private void validarDados(ChamadoDTO dto) {
        if (dto.getTitulo() != null && dto.getTitulo().trim().isEmpty()) {
            throw new ValidacaoException("Título não pode ser vazio");
        }
    }

    private Chamado dtoToEntity(ChamadoDTO dto) {
        Chamado c = new Chamado();
        c.setTitulo(dto.getTitulo());
        c.setDescricao(dto.getDescricao());
        c.setPrioridade(dto.getPrioridade());
        c.setClienteEmail(dto.getClienteEmail());
        c.setStatus(dto.getStatus());
        return c;
    }
}
