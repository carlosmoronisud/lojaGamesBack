package com.games.carlosgames.repository;

import com.games.carlosgames.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo email (username)
    Optional<Usuario> findByEmail(String email);

}