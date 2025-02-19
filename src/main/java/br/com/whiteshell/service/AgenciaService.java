package br.com.whiteshell.service;

import br.com.whiteshell.domain.Agencia;
import br.com.whiteshell.domain.http.AgenciaHttp;
import br.com.whiteshell.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.whiteshell.repository.AgenciaRepository;
import br.com.whiteshell.service.http.SituacaoCadastralHttpService;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

import static br.com.whiteshell.domain.http.SituacaoCadastralEnum.ATIVO;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    @Inject
    AgenciaRepository agenciaRepository;

    @Inject
    MeterRegistry meterRegistry;

    public void cadastrar(Agencia agencia) {
        AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCNPJ(agencia.getCnpj());

        if (agenciaHttp != null && agenciaHttp.getSituacaoCadastral().equals(ATIVO)) {
            agenciaRepository.persist(agencia);
            Log.info("Agência com o CPNJ " + agencia.getCnpj() + " foi cadastrada com sucesso");
            meterRegistry.counter("agencia.cadastrada").increment();
        } else {
            Log.warn("Agência com o CPNJ " + agencia.getCnpj() + " não foi cadastrada");
            meterRegistry.counter("agencia.nao.cadastrada").increment();
            throw new AgenciaNaoAtivaOuNaoEncontradaException();
        }
    }

    public List<Agencia> buscarTodas () {
        return agenciaRepository.findAll().stream().toList();
    }

    public Agencia buscarPorId(long id) {
        return agenciaRepository.findById(id);
    }

    public void deletar(long id) {
        agenciaRepository.deleteById(id);
        Log.info("A agência com o id " + id + " foi deletada com sucesso");
    }

    public void alterar(long id, Agencia agencia) {
        Agencia agenciaAtual = buscarPorId(id);

        if(agenciaAtual == null) {
            throw new AgenciaNaoAtivaOuNaoEncontradaException();
        }

        agenciaAtual.setNome(agencia.getNome());
        agenciaAtual.setCnpj(agencia.getCnpj());
        agenciaAtual.setRazaoSocial(agencia.getRazaoSocial());
        agenciaAtual.setEndereco(agencia.getEndereco());

        //Another method to update
        //agenciaRepository.update("nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4", agencia.getNome(), agencia.getRazaoSocial(), agencia.getCnpj(), agencia.getId());
    }
}
