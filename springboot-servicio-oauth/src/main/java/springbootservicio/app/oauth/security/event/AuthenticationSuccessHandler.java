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

import brave.Tracer;
import feign.FeignException;
import springbootservicio.app.commons.usuarios.models.entity.Usuario;
import springbootservicio.app.oauth.services.IUsuarioService;


@Component
public class AuthenticationSuccessHandler implements AuthenticationEventPublisher {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private Tracer tracer;
	
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
		String mensaje = "Error Login: " + exception.getMessage();
		logger.error(mensaje);
		
		try {
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if(usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			logger.info("intentos: " + usuario.getIntentos());
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			logger.info("intentos actuales: " + usuario.getIntentos());
			
			errors.append(" -- Intentos del login: "+ usuario.getIntentos());
			tracer.currentSpan().tag("error.mensaje", mensaje.toString());
			
			if(usuario.getIntentos() >= 3) {
				String errorMaxIntentos = String.format("El usuario %s deshabilitado por máximos intentos.", usuario.getUserName());
				logger.info(errorMaxIntentos);
				errors.append(" -- "+errorMaxIntentos);
				tracer.currentSpan().tag("error.mensaje", errorMaxIntentos.toString());
				usuario.setEnabled(false);
			}
			
			usuarioService.update(usuario, usuario.getId());
			
			tracer.currentSpan().tag("error.mensaje", errors.toString());
			
		} catch(FeignException e) {
			String msgException = String.format("El usuario %s no existe en el sistema", authentication.getName());
			logger.error(msgException);
			tracer.currentSpan().tag("error.mensaje",msgException + ": \n" +  e.getMessage());
		}
		
		
	}

}
