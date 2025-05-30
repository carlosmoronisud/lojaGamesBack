package com.games.carlosgames.security.jwt;

import com.games.carlosgames.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter; // Importe OncePerRequestFilter

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService; // Precisamos de um UserDetailsService

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // Pega o cabeçalho Authorization
        final String jwt;
        final String userEmail; // Ou username, dependendo de como você extrai do token

        // 1. Verifica se o token está presente e no formato correto (Bearer)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continua a cadeia de filtros
            return;
        }

        // 2. Extrai o JWT (remove "Bearer ")
        jwt = authHeader.substring(7);

        // 3. Extrai o email/username do token usando o JwtService
        userEmail = jwtService.extractUsername(jwt);

        // 4. Se o email/username foi extraído e o usuário não está autenticado no contexto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. Carrega os detalhes do usuário (UserDetailsService)
            // IMPORTANTE: Para usuários de OAuth2 (Google), você pode precisar de um UserDetailsService que
            // saiba como carregar esses usuários, ou criar um usuário temporário no contexto.
            // Por enquanto, vamos assumir que userDetailsService pode encontrar o usuário pelo email.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Valida o token
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) { // Reutiliza userDetails.getUsername() para garantir consistência
                // 7. Se o token é válido, cria um objeto de autenticação
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credenciais nulas, pois já autenticamos via token
                        userDetails.getAuthorities() // Autoridades do usuário (ROLE_USER, ROLE_ADMIN, etc.)
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 8. Autentica o usuário no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }
}