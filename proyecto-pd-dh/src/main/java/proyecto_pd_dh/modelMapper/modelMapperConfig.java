package proyecto_pd_dh.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class modelMapperConfig {

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();

    }
}
