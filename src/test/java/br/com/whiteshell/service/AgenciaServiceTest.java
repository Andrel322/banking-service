package br.com.whiteshell.service;

import br.com.whiteshell.domain.Agencia;
import br.com.whiteshell.domain.Endereco;
import br.com.whiteshell.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.whiteshell.repository.AgenciaRepository;
import br.com.whiteshell.service.http.SituacaoCadastralHttpService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class AgenciaServiceTest {

    @InjectMock
    AgenciaRepository agenciaRepository;

    @InjectMock
    @RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    @Inject
    AgenciaService agenciaService;

    @Test
    public void deveNaoCadastrarAgenciaQuandoClientRetornarNull() {
        Agencia agencia = getMockAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCNPJ("123")).thenReturn(null);

        Assertions.assertThrows(AgenciaNaoAtivaOuNaoEncontradaException.class,() -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    private Agencia getMockAgencia() {
        Endereco endereco = Endereco.builder()
                .id(1)
                .rua("test")
                .logradouro("test")
                .complemento("test")
                .numero(1)
                .build();

        return Agencia.builder()
                .id(1)
                .endereco(endereco)
                .cnpj("123")
                .nome("test")
                .razaoSocial("test")
                .build();
    }
}
