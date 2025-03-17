package org.br.ct9backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token, null, this.getUserAuthorityByUid(token));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Handle token verification error
            }
        }
        filterChain.doFilter(request, response);
    }

    private List<GrantedAuthority> getUserAuthorityByUid(String uid) {
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(uid);

        if(aluno.isEmpty()){
            throw new RuntimeException("Aluno não encontrado");
        }

        String role = aluno.get().getCargo().name();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}