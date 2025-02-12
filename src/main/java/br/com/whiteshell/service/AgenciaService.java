package br.com.whiteshell.service;

import br.com.whiteshell.domain.Agencia;
import br.com.whiteshell.domain.http.AgenciaHttp;
import br.com.whiteshell.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.whiteshell.repository.AgenciaRepository;
import br.com.whiteshell.service.http.SituacaoCadastralHttpService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import static br.com.whiteshell.domain.http.SituacaoCadastralEnum.ATIVO;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    @Inject
    AgenciaRepository agenciaRepository;

    public void cadastrar(Agencia agencia) {
        AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCNPJ(agencia.getCnpj());

        if (agenciaHttp != null && agenciaHttp.getSituacaoCadastral().equals(ATIVO)) {
            agenciaRepository.persist(agencia);
        } else {
            throw new AgenciaNaoAtivaOuNaoEncontradaException();
        }
    }

    public Agencia buscarPorId(long id) {
        return agenciaRepository.findById(id);
    }

    public void deletar(long id) {
        agenciaRepository.deleteById(id);
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
