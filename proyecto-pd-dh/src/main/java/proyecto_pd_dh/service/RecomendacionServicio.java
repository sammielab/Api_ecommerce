package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.dto.RecomendacionDTO;
import proyecto_pd_dh.dto.UsuarioDTO;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IProductoRepository;
import proyecto_pd_dh.repository.IRecomendacionRepository;
import proyecto_pd_dh.repository.IUsuarioRepository;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class RecomendacionServicio {

    @Autowired
    private IRecomendacionRepository recomendacionRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IProductoRepository productoRepository;


    public RecomendacionDTO save(Recomendacion recomendacion) throws ResourceNotFoundException {

        Producto producto = productoRepository.findById(recomendacion.getProducto().getId()).orElseThrow(()->new ResourceNotFoundException("Producto no encontrado"));
        Usuario usuario = usuarioRepository.findById(recomendacion.getUsuario().getId()).orElseThrow(()-> new ResourceNotFoundException("No se encontro el usuario"));

        Recomendacion recomendation = new Recomendacion();

        recomendation.setId((recomendacion.getId()));
        recomendation.setProducto(producto);
        recomendation.setUsuario(usuario);
        recomendation.setPuntaje_total(recomendacion.getPuntaje_total());
        recomendation.setFecha_publicacion(recomendacion.getFecha_publicacion());
        recomendation.setDescripcion(recomendacion.getDescripcion());

        recomendacionRepository.save(recomendation);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());

        RecomendacionDTO recomendacionDTO = new RecomendacionDTO();
        recomendacionDTO.setId(recomendation.getId());
        recomendacionDTO.setUsuario(usuarioDTO);
        recomendacionDTO.setProducto(productoDTO);
        recomendacionDTO.setDescripcion(recomendacion.getDescripcion());
        recomendacionDTO.setPuntaje_total(recomendacion.getPuntaje_total());
        recomendacionDTO.setFecha_publicacion(recomendacion.getFecha_publicacion());

        return recomendacionDTO;
    }

    public Optional<Recomendacion> findById(Integer id){
        return recomendacionRepository.findById(id);
    }

    public List<Recomendacion> findAll(){
        return recomendacionRepository.findAll();
    }

    public Recomendacion update(Recomendacion recomendacion){
        return recomendacionRepository.save(recomendacion);
    }

    public void delete(Integer id){
        recomendacionRepository.deleteById(id);
    }

}
