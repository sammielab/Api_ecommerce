package proyecto_pd_dh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("proyecto_pd_dh.repository")
public class ProyectoPdDhApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoPdDhApplication.class, args);
	}

}
