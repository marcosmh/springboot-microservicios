package springbootservicio.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import springbootservicio.app.commons.usuarios.models.entity.Usuario;
import springbootservicio.app.oauth.clients.UsuarioFeignClient;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Autowired
	private UsuarioFeignClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("UsuarioService.loadUserByUsername [ini]");
		Usuario usuario = client.findByUsername(username);
		
		if(usuario == null) {
			logger.info("username: ${username}");
			throw new UsernameNotFoundException("Error en el login, no existe el usuario "+username+" en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		logger.info("Usuario autenticado: " +  usuario.getUserName());
		
		return new User(usuario.getUserName(), usuario.getPassword(), usuario.getEnabled(), 
				true, true, true, authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		logger.info("UsuarioService.findByUsername [ini]");
		return client.findByUsername(username);
	}

}
