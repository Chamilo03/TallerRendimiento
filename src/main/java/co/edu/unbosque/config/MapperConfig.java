package co.edu.unbosque.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private final ModelMapper modelMapper = new ModelMapper();
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    public <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }
}
