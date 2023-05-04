package springbootservicio.app.zuul.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
	
	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("ResourceServerConfig.configure");
		logger.info("Asigando los permisos.....");
		
		http.authorizeRequests()
			.antMatchers("/api/security/oauth/**").permitAll()
			.antMatchers(HttpMethod.GET, 
					"/api/productos/listar",
					"/api/items/listar",
					"/api/usuarios/usuarios").permitAll()
			.antMatchers(HttpMethod.GET, 
					"/api/productos/ver/{id}",
					"/api/items/ver/{id}/cantidad/{cantida]",
					"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN","USER")
			/*.antMatchers(HttpMethod.POST,
					"/api/productos/crear",
					"/api/items/crear",
					"/api/usuarios/usuarios").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT,
					"/api/productos/editar/{id}",
					"/api/items/editar{id}",
					"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,
					"/api/productos/eliminar/{id}",
					"/api/items/eliminar{id}",
					"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
					*/
			.antMatchers("/api/productos/**","/api/items/**","/api/usuarios/**").hasRole("ADMIN")
			.anyRequest().authenticated();
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);
		return tokenConverter;
	}
	
}
