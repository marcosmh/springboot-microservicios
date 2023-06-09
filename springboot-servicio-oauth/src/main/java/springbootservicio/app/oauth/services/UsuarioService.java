package springbootservicio.app.oauth.services;

import java.util.ArrayList;
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

import brave.Tracer;
import feign.FeignException;
import springbootservicio.app.commons.usuarios.models.entity.Role;
import springbootservicio.app.commons.usuarios.models.entity.Usuario;
import springbootservicio.app.oauth.clients.UsuarioFeignClient;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Autowired
	private UsuarioFeignClient client;
	
	@Autowired
	private Tracer tracer;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("UsuarioService.loadUserByUsername [ini]");
		logger.info("username= "+username);
		
		try  {
			
			Usuario usuario = client.findByUserName(username);
			logger.info(usuario.toString());
			setRoles(usuario);
			
			logger.info("hacer authorities...");
			List<GrantedAuthority> authorities = usuario.getRoles()
					.stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(authority -> logger.info("Role: " + authority.getAuthority()))
					.collect(Collectors.toList());
			
			logger.info("Usuario autenticado: " +  usuario.getUserName());
			
			return new User(usuario.getUserName(), usuario.getPassword(), usuario.getEnabled(), 
					true, true, true, authorities);
			
		} catch( FeignException e) {
			String mensaje = "Error en el login, no existe el usuario "+username+" en el sistema";
			logger.error(mensaje);
			tracer.currentSpan().tag("error.mensaje",mensaje + ": \n" +  e.getMessage());
			throw new UsernameNotFoundException(mensaje);
		}
		
		
	}

	
	@Override
	public Usuario findByUsername(String username) {
		logger.info("UsuarioService.findByUsername [ini]");
		return client.findByUserName(username);
	}
	
	private void setRoles(Usuario usuario) {
		// solucion temporal
		if( usuario.getRoles() == null) {
			List<Role> list = new ArrayList<Role>();
			Role rol = new Role((long) 1,"ROLE_USER");
			Role rol2 = new Role((long) 2,"ROLE_ADMIN");
			list.add(rol);
			list.add(rol2);
			usuario.setRoles(list);
		}
	}


	@Override
	public Usuario update(Usuario usuario, Long id) {
		logger.info("UsuarioService.update [ini]");
		return client.update(usuario, id);
	}

}
