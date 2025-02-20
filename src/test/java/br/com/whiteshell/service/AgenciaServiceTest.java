package br.com.whiteshell.service;

import br.com.whiteshell.domain.Agencia;
import br.com.whiteshell.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.whiteshell.repository.AgenciaRepository;
import br.com.whiteshell.service.http.SituacaoCadastralHttpService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static br.com.whiteshell.domain.http.SituacaoCadastralEnum.ATIVO;
import static br.com.whiteshell.domain.http.SituacaoCadastralEnum.INATIVO;
import static br.com.whiteshell.utils.AgenciaFixture.getMockAgencia;
import static br.com.whiteshell.utils.AgenciaFixture.getMockAgenciaHttp;

@QuarkusTest
public class AgenciaServiceTest {

    @InjectMock
    AgenciaRepository agenciaRepository;

    @InjectMock
    @RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    @Inject
    AgenciaService agenciaService;

    @BeforeEach
    public void setUp() {
        Mockito.doNothing().when(agenciaRepository).persist(Mockito.any(Agencia.class));
    }

    @Test
    public void deveNaoCadastrarAgenciaQuandoClientRetornarNull() {
        Agencia agencia = getMockAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCNPJ("123")).thenReturn(null);

        Assertions.assertThrows(AgenciaNaoAtivaOuNaoEncontradaException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    public void deveNaoCadastrarAgenciaQuandoClientRetornarAgenciaInativa() {
        Agencia agencia = getMockAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCNPJ("123")).thenReturn(getMockAgenciaHttp(INATIVO));

        Assertions.assertThrows(AgenciaNaoAtivaOuNaoEncontradaException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }


    @Test
    public void deveCadastrarQuandoClientRetornarSituacaoCadastralAtiva() {
        Agencia agencia = getMockAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCNPJ("123")).thenReturn(getMockAgenciaHttp(ATIVO));

        agenciaService.cadastrar(agencia);

        Mockito.verify(agenciaRepository).persist(agencia);
    }
}
