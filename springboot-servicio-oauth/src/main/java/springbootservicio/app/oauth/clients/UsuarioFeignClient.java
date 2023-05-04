package springbootservicio.app.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import springbootservicio.app.commons.usuarios.models.entity.Usuario;

@FeignClient(name="servicio-usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/usuarios/search/buscar-username")
	Usuario findByUserName(@RequestParam String username);
	
	@PutMapping("/usuarios/{id}")
	Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);
	
	
}
