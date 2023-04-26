package springbootservicio.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import springbootservicio.app.commons.usuarios.models.entity.Usuario;

       

@RepositoryRestResource(path="usuarios")
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Long> {

	@RestResource(path = "buscar-username")
	Usuario findByUserName(@Param("nombre") String username);
	
	@Query("select u from Usuario u where u.userName=?1")
	Usuario obtenerPorUsername(String username);
	
	
}
