package br.gov.caixaverso.projeto.resources;

import br.gov.caixaverso.projeto.domainmodel.Usuario;
import br.gov.caixaverso.projeto.dto.SignInRequest;
import br.gov.caixaverso.projeto.dto.SignInResponse;
import br.gov.caixaverso.projeto.dto.SignUpRequest;
import br.gov.caixaverso.projeto.security.JwtGenerator;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @ConfigProperty(name = "quarkus.smallrye-jwt.new-token.lifespan", defaultValue = "3600")
    Long tokenExpiration;

    private final JwtGenerator jwtGenerator;

    public AuthResource(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @POST
    @Path("/token")
    public Response signIn(SignInRequest request) {
        Optional<Usuario> usuarioBuscado = Usuario.find("email", request.email()).firstResultOptional();

        if(usuarioBuscado.isPresent() && BcryptUtil.matches(request.password(), usuarioBuscado.get().senha)) {
            String token = jwtGenerator.generateSignJwt(usuarioBuscado.get().nome, usuarioBuscado.get().role);
            return Response.ok(new SignInResponse(token, tokenExpiration)).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
