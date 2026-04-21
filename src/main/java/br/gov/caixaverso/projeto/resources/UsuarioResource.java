package br.gov.caixaverso.projeto.resources;

import br.gov.caixaverso.projeto.domainmodel.Usuario;
import br.gov.caixaverso.projeto.dto.MyInfoResponse;
import br.gov.caixaverso.projeto.dto.SignUpRequest;
import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Optional;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
public class UsuarioResource {

    private final JsonWebToken jwt;

    public UsuarioResource(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    @POST
    public Response criarUsuario(@Valid SignUpRequest request) {
        Optional<Usuario> usuarioBuscado = Usuario.find("email", request.email()).firstResultOptional();

        if(usuarioBuscado.isPresent())
            return Response.status(Response.Status.CONFLICT).build();

        Usuario.add(request.name(), request.email(), request.password(), "USER");
        return Response.ok().build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("/me")
    public Response getUsuarioLogado() {
        String nomeInformado = jwt.getClaim("sub");

        if (nomeInformado == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        Optional<Usuario> usuarioBuscado = Usuario.find("email", nomeInformado).firstResultOptional();

        if (usuarioBuscado.isPresent())
            return Response.ok(new MyInfoResponse(
                    usuarioBuscado.get().nome,
                    usuarioBuscado.get().email,
                    usuarioBuscado.get().role,
                    jwt.getClaim("exp")))
                    .build();
        else
            return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
