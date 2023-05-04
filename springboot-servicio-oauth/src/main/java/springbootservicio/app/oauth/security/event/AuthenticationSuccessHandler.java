package springbootservicio.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationSuccessHandler implements AuthenticationEventPublisher {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		logger.info("Success Login: " + user.getUsername());
		
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		logger.error("Error Login: " + exception.getMessage());
		
	}

}
