package springbootservicio.app.commons.usuarios.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8243213244147854890L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name", unique = true, length = 20)
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "email", unique = true, length = 100)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "usuarios_roles", 
		joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns = @JoinColumn(name="role_id"),
		uniqueConstraints = { @UniqueConstraint(columnNames = {"usuario_id","role_id"})} )
		
	List<Role> roles;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", userName=" + userName + ", password=" + password + ", enabled=" + enabled
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", roles=" + roles + "]";
	}
	
	
	

}
