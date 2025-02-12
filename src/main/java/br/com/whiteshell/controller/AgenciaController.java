package br.com.whiteshell.controller;

import br.com.whiteshell.domain.Agencia;
import br.com.whiteshell.service.AgenciaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/agencias")
public class AgenciaController {

    @Inject
    AgenciaService agenciaService;

    @POST
    @Transactional
    public RestResponse<Void> cadastrar(Agencia agencia, @Context UriInfo uriInfo) {
        this.agenciaService.cadastrar(agencia);

        return RestResponse.created(uriInfo.getAbsolutePath());
    }

    @GET
    @Path("{id}")
    public RestResponse<Agencia> buscarPorId(long id) {
        Agencia agencia = this.agenciaService.buscarPorId(id);

        return RestResponse.ok(agencia);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public RestResponse<Void> deletar(long id) {
        this.agenciaService.deletar(id);

        return RestResponse.ok();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public RestResponse<Agencia> alterar(long id, Agencia agencia) {
        this.agenciaService.alterar(id, agencia);

        return RestResponse.ok(agencia);
    }
}
