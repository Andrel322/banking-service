package br.com.whiteshell.utils;

import br.com.whiteshell.domain.Agencia;
import br.com.whiteshell.domain.Endereco;
import br.com.whiteshell.domain.http.AgenciaHttp;
import br.com.whiteshell.domain.http.SituacaoCadastralEnum;

public class AgenciaFixture {

    public static Agencia getMockAgencia() {
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

    public static AgenciaHttp getMockAgenciaHttp(SituacaoCadastralEnum situacao) {
        return AgenciaHttp.builder()
                .cnpj("123")
                .nome("Agencia test")
                .razaoSocial("Razao test")
                .situacaoCadastral(situacao)
                .build();
    }
}
