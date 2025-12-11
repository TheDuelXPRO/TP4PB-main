import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.suporte.dto.ClienteDTO;
import org.example.suporte.exception.RecursoNaoEncontradoException;
import org.example.suporte.exception.ValidacaoException;
import org.example.suporte.model.Cliente;
import org.example.suporte.repository.ClienteRepository;
import org.example.suporte.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository repository;

    @InjectMocks
    ClienteService service;

    @Test
    void deveListarTodosOsClientes() {
        Cliente primeiro = new Cliente();
        primeiro.setId(1L);
        primeiro.setEmail("um@teste.com");

        Cliente segundo = new Cliente();
        segundo.setId(2L);
        segundo.setEmail("dois@teste.com");

        when(repository.findAll()).thenReturn(Arrays.asList(primeiro, segundo));

        List<Cliente> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("um@teste.com", resultado.get(0).getEmail());
    }

    @Test
    void deveCriarClienteComSucesso() {
        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("novo@teste.com");

        when(repository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> {
            Cliente salvo = invocation.getArgument(0);
            salvo.setId(10L);
            return salvo;
        });

        Cliente criado = service.criar(dto);

        assertEquals(10L, criado.getId());
        assertEquals("novo@teste.com", criado.getEmail());
    }

    @Test
    void naoDeveCriarQuandoEmailJaExiste() {
        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("duplicado@teste.com");

        when(repository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(ValidacaoException.class, () -> service.criar(dto));
    }

    @Test
    void deveExcluirClienteExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(5L);
        cliente.setEmail("remover@teste.com");

        when(repository.findById(5L)).thenReturn(Optional.of(cliente));

        service.excluir(5L);

        verify(repository).delete(cliente);
    }

    @Test
    void deveLancarErroQuandoClienteNaoEncontradoNaExclusao() {
        when(repository.findById(eq(99L))).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.excluir(99L));
    }
}
