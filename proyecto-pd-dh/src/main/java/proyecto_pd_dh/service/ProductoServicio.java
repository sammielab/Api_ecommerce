package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.*;
import proyecto_pd_dh.entities.*;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IProductoRepository;

import java.time.LocalDate;
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
    @Autowired
    private final CategoriaServicio categoriaServicio;
    @Autowired
    private final ImgServicio imgServicio;
    @Autowired
    private final UbicacionesServicio ubicacionesServicio;

    public ProductoServicio(CaracteristicasServicio caracteristicasServicio, CategoriaServicio categoriaServicio, ImgServicio imgServicio, UbicacionesServicio ubicacionesServicio) {
        this.caracteristicasServicio = caracteristicasServicio;
        this.categoriaServicio = categoriaServicio;
        this.imgServicio = imgServicio;
        this.ubicacionesServicio = ubicacionesServicio;
    }


    public Producto save(Producto producto) throws Exception {
        try{

        if (producto.getCategoria() != null) {
            Categoria categoria = categoriaServicio.findById(producto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

            if(producto.getCaracteristicas() != null){
                List<Caracteristica> caracteristicas = new ArrayList<>();
                for(Caracteristica caracteristica : producto.getCaracteristicas()){
                    Caracteristica existingCaracteristica = caracteristicasServicio.findById(caracteristica.getId())
                            .orElseThrow(() -> new RuntimeException("Caracteristica no encontrada"));
                    caracteristicas.add(existingCaracteristica);
                }
                producto.setCaracteristicas(caracteristicas);

            }

            if(producto.getUbicacion() != null){
                Ubicacion ubicacion = ubicacionesServicio.findById(producto.getUbicacion().getId());

                producto.setUbicacion(ubicacion);
            }



            return productoRepository.save(producto);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        }


    public Optional<ProductoDTO> findById(Integer id) throws Exception {

        Optional<Producto> productoFound = productoRepository.findById(id);
        Optional<ProductoDTO> productoReturn;

        if(productoFound.isPresent()){
            ProductoDTO productoDTO = new ProductoDTO();

            Optional<List<IMGDTO>> imgFound = imgServicio.findByIdProducto(id);
            if(imgFound.isPresent()){
                List<IMGDTO> imgdtos =  imgFound.get().stream()
                        .map(img -> new IMGDTO(img.getImagen()))
                        .toList();
                productoDTO.setImgdtos(imgdtos);
            }

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
            productoDTO.setCategoria(productoFound.get().getCategoria().getId());
            productoDTO.setPoliticas(productoFound.get().getPoliticas());

            productoReturn = Optional.of(productoDTO);
            return productoReturn;
        }else{
            throw  new ResourceNotFoundException("No se encontro el producto con id " + id);
        }
    }

    public List<ProductoDTO> findAll() throws Exception {
    try {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> productoDTOS = new ArrayList<>();


        if (!productos.isEmpty()) {
            for (Producto p : productos) {
                System.out.print(p);
                ProductoDTO pDTO = new ProductoDTO();

                Optional<List<IMGDTO>> imgFound = imgServicio.findByIdProducto(p.getId());
                if(imgFound.isPresent()){
                    List<IMGDTO> imgdtos =  imgFound.get().stream()
                            .map(img -> new IMGDTO(img.getImagen()))
                            .toList();
                    pDTO.setImgdtos(imgdtos);
                }

                for(IMGDTO img: pDTO.getImgdtos()){
                    System.out.print(img);
                }

                pDTO.setId(p.getId());
                pDTO.setTitulo(p.getTitulo());
                pDTO.setDescripcion(p.getDescripcion());
                pDTO.setCaracteristicas(p.getCaracteristicas());
                pDTO.setCategoria(p.getCategoria().getId());
                pDTO.setPrecio(p.getPrecio());


                productoDTOS.add(pDTO);

            }

            return productoDTOS;
        } else {
            throw new ResourceNotFoundException("No se encontraron productos disponibles");
        }
    }catch(Exception e){
        throw new Exception(e.getMessage());
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
                    productoDTO.setCategoria(producto.getCategoria().getId());
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

    public List<ProductoDTO> findProductoByCategoriaId (List<Integer> categorias) throws Exception {
        try{

            List<Producto> productosPorCategoria = productoRepository.findByCategoriaIdIn(categorias);
            System.out.print(productosPorCategoria);
            if(!productosPorCategoria.isEmpty()){
                List<ProductoDTO> productosPorCategoriaDTO = productosPorCategoria.stream()
                        .map(producto -> {
                            ProductoDTO productoDTO = new ProductoDTO();
                            productoDTO.setId(producto.getId());
                            productoDTO.setTitulo(producto.getTitulo());
                            productoDTO.setDescripcion(producto.getDescripcion());
                            productoDTO.setCaracteristicas(producto.getCaracteristicas());
                            productoDTO.setPrecio(producto.getPrecio());
                            productoDTO.setCategoria(producto.getCategoria().getId());
                            return productoDTO;
                        })
                        .toList();

                System.out.print(productosPorCategoriaDTO);
                return productosPorCategoriaDTO;
            }else{
                throw new ResourceNotFoundException("No se encontraron productos para las categorias");

            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<ProductoDTO> findFilteredProductos(String ciudad, LocalDate checkin, LocalDate checkout){
       try {
           List<Producto> productos = productoRepository.findAvailableProductos(ciudad, checkin, checkout);
           if(!productos.isEmpty()){
           List<ProductoDTO> productosDTO = productos.stream()
                   .map(producto -> {
                       ProductoDTO productoDTO = new ProductoDTO();
                       productoDTO.setId(producto.getId());
                       productoDTO.setTitulo(producto.getTitulo());
                       productoDTO.setPrecio(producto.getPrecio());
                       productoDTO.setCaracteristicas(producto.getCaracteristicas());
                       productoDTO.setDescripcion(producto.getDescripcion());
                       productoDTO.setCategoria(producto.getCategoria().getId());
                       return productoDTO;
                   })
                   .toList();

           return productosDTO;
           }else{
               throw new ResourceNotFoundException("No se encontraron productos disponibles");
           }
       }catch(Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }
}
