package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.entities.Caracteristica;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private final CaracteristicasServicio caracteristicasServicio;

    public ProductoServicio(CaracteristicasServicio caracteristicasServicio) {
        this.caracteristicasServicio = caracteristicasServicio;
    }


    public Producto save(Producto producto){

            if(producto.getCaracteristicas() != null){
                List<Caracteristica> caracteristicas = new ArrayList<>();
                for(Caracteristica caracteristica : producto.getCaracteristicas()){
                    Caracteristica existingCaracteristica = caracteristicasServicio.findById(caracteristica.getId())
                            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
                    caracteristicas.add(existingCaracteristica);
                }
                producto.setCaracteristicas(caracteristicas);

            }
            return productoRepository.save(producto);

        }


    public Optional<ProductoDTO> findById(Integer id) throws ResourceNotFoundException {

        Optional<Producto> productoFound = productoRepository.findById(id);
        Optional<ProductoDTO> productoReturn;

        if(productoFound.isPresent()){
            ProductoDTO productoDTO = new ProductoDTO();

            productoDTO.setId(productoFound.get().getId());
            productoDTO.setTitulo(productoFound.get().getTitulo());
            productoDTO.setPrecio(productoFound.get().getPrecio());
            productoDTO.setCaracteristicas(productoFound.get().getCaracteristicas());
            productoDTO.setDescripcion(productoFound.get().getDescripcion());
            productoDTO.setRecomendaciones(productoFound.get().getRecomendaciones());
            productoDTO.setCategoria(productoFound.get().getCategoria());

            productoReturn = Optional.of(productoDTO);
            return productoReturn;
        }else{
            throw  new ResourceNotFoundException("No se encontro el producto con id " + id);
        }
    }

    public List<Producto> findAll(){
        return productoRepository.findAll();
    };

    public List<Producto> getAll(int page, int limit){

        List<Producto> productos = productoRepository.findAll();
        int total = productos.size();

        // Validar y calcular índices
        if (page < 0 || limit <= 0) {
            throw new IllegalArgumentException("Página y límite deben ser mayores que 0");
        }

        int startIndex = page * limit;
        if (startIndex >= total) {
            return List.of(); // Devuelve una lista vacía si no hay más elementos
        }

        int endIndex = Math.min(startIndex + limit, total);
        return productos.subList(startIndex, endIndex);
    }

    public Producto update(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Integer id){
        productoRepository.deleteById(id);
    }

}
