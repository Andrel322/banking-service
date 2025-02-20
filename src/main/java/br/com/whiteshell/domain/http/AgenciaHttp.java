package br.com.whiteshell.domain.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgenciaHttp {
    private String nome;

    private String razaoSocial;

    private String cnpj;

    private SituacaoCadastralEnum situacaoCadastral;
}
