package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.dto.RecomendacionDTO;
import proyecto_pd_dh.dto.UsuarioDTO;
import proyecto_pd_dh.entities.Caracteristica;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

            List<RecomendacionDTO> recomendacionesMapp = productoFound.get().getRecomendaciones()
                    .stream()
                    .map(recomendacion -> {
                        UsuarioDTO usuarioDTO = new UsuarioDTO();
                        usuarioDTO.setId(recomendacion.getUsuario().getId());

                        ProductoDTO productoDTORec = new ProductoDTO();
                        productoDTORec.setId(recomendacion.getProducto().getId());



                        RecomendacionDTO rec = new RecomendacionDTO();
                        rec.setId(recomendacion.getId());
                        rec.setPuntaje_total(recomendacion.getPuntaje_total());
                        rec.setDescripcion(recomendacion.getDescripcion());
                        rec.setFecha_publicacion(recomendacion.getFecha_publicacion());
                        rec.setProducto(productoDTORec);
                        rec.setUsuario(usuarioDTO);
                        return rec;
                    })
                    .toList();

            productoDTO.setId(productoFound.get().getId());
            productoDTO.setTitulo(productoFound.get().getTitulo());
            productoDTO.setPrecio(productoFound.get().getPrecio());
            productoDTO.setCaracteristicas(productoFound.get().getCaracteristicas());
            productoDTO.setDescripcion(productoFound.get().getDescripcion());
            productoDTO.setRecomendaciones(recomendacionesMapp);
            productoDTO.setCategoria(productoFound.get().getCategoria());

            productoReturn = Optional.of(productoDTO);
            return productoReturn;
        }else{
            throw  new ResourceNotFoundException("No se encontro el producto con id " + id);
        }
    }

    public List<ProductoDTO> findAll() throws ResourceNotFoundException {

        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> productoDTOS = new ArrayList<>();
        if(!productos.isEmpty()){
            for(Producto p : productos){
                ProductoDTO pDTO = new ProductoDTO();

                pDTO.setId(p.getId());
                pDTO.setTitulo(p.getTitulo());
                pDTO.setDescripcion(p.getDescripcion());
                pDTO.setCaracteristicas(p.getCaracteristicas());
                pDTO.setCategoria(p.getCategoria());
                pDTO.setPrecio(p.getPrecio());

                productoDTOS.add(pDTO);

            }

            return productoDTOS;
        }else{
            throw new ResourceNotFoundException("No se encontraron productos disponibles");
        }
    };

    public List<ProductoDTO> getAll(int page, int limit){

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


        List<ProductoDTO> productosDTO = productos.subList(startIndex, endIndex).stream()
                .map(producto -> {
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setId(producto.getId());
                    productoDTO.setTitulo(producto.getTitulo());
                    productoDTO.setPrecio(producto.getPrecio());
                    productoDTO.setCaracteristicas(producto.getCaracteristicas());
                    productoDTO.setDescripcion(producto.getDescripcion());
                    productoDTO.setCategoria(producto.getCategoria());
                    return productoDTO;
                })
                .toList(); // Recolectamos en una lista

        return productosDTO;

    }

    public Producto update(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Integer id){
        productoRepository.deleteById(id);
    }

}
