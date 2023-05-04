package springbootservicio.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import feign.FeignException;
import springbootservicio.app.commons.usuarios.models.entity.Usuario;
import springbootservicio.app.oauth.services.IUsuarioService;


@Component
public class AuthenticationSuccessHandler implements AuthenticationEventPublisher {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		logger.info("Success Login: " + user.getUsername());
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		logger.error("Error Login: " + exception.getMessage());
		
		try {
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if(usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			logger.info("intentos: " + usuario.getIntentos());
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			logger.info("intentos actuales: " + usuario.getIntentos());
			
			if(usuario.getIntentos() >= 3) {
				logger.info(String.format("El usuario %s deshabilitado por máximos intentos.", usuario.getUserName()));
				usuario.setEnabled(false);
			}
			
			usuarioService.update(usuario, usuario.getId());
			
		} catch(FeignException e) {
			logger.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
		
	}

}
