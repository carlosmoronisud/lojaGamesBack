package com.games.carlosgames.user; // Ou o pacote onde você o colocou

import com.games.carlosgames.model.Usuario; // Importe seu modelo de usuário
import com.games.carlosgames.repository.UsuarioRepository; // Importe seu repositório de usuário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Injete o repositório de usuário

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo email
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        // Se o usuário não for encontrado, lança uma exceção
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }

        // Retorna o objeto Usuario que implementa UserDetails
        return usuario.get();
    }
}