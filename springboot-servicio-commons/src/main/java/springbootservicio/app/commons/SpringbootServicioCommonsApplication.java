package springbootservicio.app.commons;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class SpringbootServicioCommonsApplication {

	// No se utiliza ya que es un proyecto de libreria
	/*
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioCommonsApplication.class, args);
	}*/

}
