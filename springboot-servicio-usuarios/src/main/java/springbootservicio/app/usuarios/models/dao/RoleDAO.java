package springbootservicio.app.usuarios.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import springbootservicio.app.usuarios.models.entity.Role;

@RepositoryRestResource(path="roles")
public interface RoleDAO extends PagingAndSortingRepository<Role, Long> {

	
	
}
