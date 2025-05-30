package com.games.carlosgames.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O atributo Nome é obrigatório!")
    @Size(min = 3, max = 255, message = "O atributo Nome deve ter no mínimo 3 e no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "O atributo E-mail é obrigatório!")
    @Email(message = "O atributo E-mail deve ser um e-mail válido!")
    private String email;

    @NotBlank(message = "O atributo Senha é obrigatório!")
    @Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
    @JsonIgnore
    private String senha;

    private String foto;

    public Usuario() {}

    // Construtor para usuários OAuth2
    public Usuario(String nome, String email, String foto) {
        this.nome = nome;
        this.email = email;
        this.foto = foto;
        // DEFINIMOS UMA SENHA PADRÃO / PLACEHOLDER AQUI PARA PASSAR NA VALIDAÇÃO
        // Esta senha NÃO SERÁ USADA PARA LOGIN TRADICIONAL
        this.senha = "oauth2_generated_password_for_google_user_1234567890"; // Exemplo: uma string longa que passe o Size
    }

    // ... (restante dos getters e setters e métodos UserDetails)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}