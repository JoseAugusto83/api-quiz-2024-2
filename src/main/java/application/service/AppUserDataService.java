package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import application.model.Usuario;
import application.repository.UsuarioRepository;

public class AppUserDataService implements UserDetailsService{

    @Autowired private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepo.findByNome(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(usuario.getNome())
        .password(usuario.getSenha())
        .roles("USER")
        .build();

        return userDetails;
    }
}
