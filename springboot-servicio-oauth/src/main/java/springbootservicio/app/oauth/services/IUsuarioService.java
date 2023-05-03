package springbootservicio.app.oauth.services;

import springbootservicio.app.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);
}
