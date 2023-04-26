package springbootservicio.app.commons.usuarios;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class SpringbootServicioUsuariosCommonsApplication {

	// No lleva main por que es un proyecto libreria
	/*
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuariosCommonsApplication.class, args);
	}*/

}
