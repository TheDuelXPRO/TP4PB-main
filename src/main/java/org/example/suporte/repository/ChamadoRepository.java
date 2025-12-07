package org.example.suporte.repository;

import org.example.suporte.model.Chamado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByStatus(String status);
}
