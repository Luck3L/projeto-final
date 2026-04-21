package br.gov.caixaverso.projeto.domainmodel;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuarios")
@UserDefinition
public class Usuario extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Username
    public String nome;
    @Email
    public String email;
    @Password
    public String senha;
    @Roles
    public String role;

    @Transactional
    public static void add(String nome, String email, String senha, String role) {
        Usuario user = new Usuario();
        user.nome = nome;
        user.email = email;
        user.senha = BcryptUtil.bcryptHash(senha);
        user.role = role;
        user.persist();
    }
}
