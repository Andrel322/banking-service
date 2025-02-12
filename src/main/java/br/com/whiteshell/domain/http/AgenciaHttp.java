package br.com.whiteshell.domain.http;

import lombok.Data;

@Data
public class AgenciaHttp {
    private String nome;

    private String razaoSocial;

    private String cnpj;

    private SituacaoCadastralEnum situacaoCadastral;

}
