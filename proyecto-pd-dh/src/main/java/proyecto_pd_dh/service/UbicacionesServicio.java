package proyecto_pd_dh.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.dto.UbicacionDTO;
import proyecto_pd_dh.entities.Ubicacion;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IUbicacionesRepository;
import proyecto_pd_dh.repository.IUsuarioRepository;

import java.util.List;
import java.util.Optional;



@RequiredArgsConstructor
@Service
public class UbicacionesServicio {

    @Autowired
    private IUbicacionesRepository ubicacionesRepository;


    public UbicacionDTO save(Ubicacion ubicacion) {
        ubicacionesRepository.save(ubicacion);

        UbicacionDTO ubicacionDTO = new UbicacionDTO();

        List<ProductoDTO> productosDTO = ubicacion.getProductos()
                .stream()
                .map(producto -> new ProductoDTO(
                                producto.getId(),
                                producto.getTitulo(),
                                producto.getDescripcion()))
                .toList();

        ubicacionDTO.setId(ubicacion.getId());
        ubicacionDTO.setCiudad(ubicacion.getCiudad());
        ubicacionDTO.setProductosDTO(productosDTO);

        return ubicacionDTO;

    }


    public List<UbicacionDTO> findAll() throws ResourceNotFoundException {
        Optional<List<Ubicacion>> ubicaciones = Optional.of(ubicacionesRepository.findAll());

        if(ubicaciones.isPresent()){

           List<UbicacionDTO> ubicacionesDTO =  ubicaciones.get()
                    .stream()
                    .map(ubicacion ->{
                        List<ProductoDTO> productoDTOS =  ubicacion.getProductos()
                                .stream()
                                .map(producto -> new ProductoDTO(
                                        producto.getId(),
                                        producto.getTitulo(),
                                        producto.getDescripcion(),
                                        producto.getPrecio()
                                ))
                                .toList();

                        return new UbicacionDTO(ubicacion.getId(), ubicacion.getCiudad(), productoDTOS);
                    })
                   .toList();

            return ubicacionesDTO;
        } else {
            throw new ResourceNotFoundException("No se encuentran ubicaciones");
        }
    }


    public List<String> findByCiudadPattern(String pattern){
       List<String> ciudades =  ubicacionesRepository.findByCiudadPattern(pattern);
       return ciudades;
    }
}
