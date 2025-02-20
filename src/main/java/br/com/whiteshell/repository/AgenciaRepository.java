package br.com.whiteshell.repository;

import br.com.whiteshell.domain.Agencia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AgenciaRepository implements PanacheRepository<Agencia> {

}
