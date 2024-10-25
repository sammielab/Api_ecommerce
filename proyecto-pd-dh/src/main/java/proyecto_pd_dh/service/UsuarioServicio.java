package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class UsuarioServicio {

    @Autowired
    private  IUsuarioRepository usuarioRepository;
    @Autowired
    private final IRecomendacionRepository recomendacionRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final IProductoRepository productoRepository;

    public UsuarioDTO save(UsuarioDTO usuarioDTO){
        //mappeo de dto
        Usuario usuarioEntity = new Usuario( usuarioDTO.getName(), usuarioDTO.getApellido(), usuarioDTO.getEmail(), usuarioDTO.getPassword(), usuarioDTO.getRole());
        usuarioRepository.save(usuarioEntity);

        UsuarioDTO returnUsuarioDTO = new UsuarioDTO();

        returnUsuarioDTO.setId(usuarioEntity.getId());
        returnUsuarioDTO.setName(usuarioEntity.getName());
        returnUsuarioDTO.setApellido(usuarioEntity.getApellido());
        returnUsuarioDTO.setRole(usuarioEntity.getRole());
        returnUsuarioDTO.setEmail(usuarioEntity.getEmail());

        return returnUsuarioDTO;
    }

    public Optional<UsuarioDTO> findById(Integer id){
       Optional<Usuario> usuarioFound =  usuarioRepository.findById(id);
       UsuarioDTO usuarioDTO = new UsuarioDTO();
       List<RecomendacionDTO> puntuacionesDTO;

       if(usuarioFound.isPresent()){
           Optional<List<Recomendacion>> recomendaciones = recomendacionRepository.findByUsuario_id(usuarioFound.get().getId());
           Optional<List<Integer>> productosFavoritosUsuario = usuarioRepository.findProductoIdsByUsuarioId(usuarioFound.get().getId());


           //Setteo los valores basicos del usuario
           usuarioDTO.setId(usuarioFound.get().getId());
           usuarioDTO.setRole(usuarioFound.get().getRole());
           usuarioDTO.setName(usuarioFound.get().getName());
           usuarioDTO.setApellido(usuarioFound.get().getApellido());
           usuarioDTO.setEmail(usuarioFound.get().getEmail());
           usuarioDTO.setPassword(passwordEncoder.encode(usuarioFound.get().getPassword()));

           //Si tiene listado de puntuaciones asociadas, las traigo y las mappeo
           if(recomendaciones.isPresent()){
               puntuacionesDTO = recomendaciones.get().stream()
                       .map(recomendacion -> new RecomendacionDTO(
                               recomendacion.getId(),
                               recomendacion.getPuntaje_total(),
                               recomendacion.getFecha_publicacion(),
                               recomendacion.getDescripcion(),
                               new ProductoDTO(recomendacion.getProducto().getId()), // Asegúrate de mapear todos los atributos relevantes de Producto.
                               new UsuarioDTO(recomendacion.getUsuario().getId()) // Mapear el usuario
                       ))
                       .toList();

               usuarioDTO.setPuntuaciones(puntuacionesDTO);

           }

           //Si tiene id de productos favoritos asociados, los mappeo a DTO
           if(productosFavoritosUsuario.isPresent()){
                List<ProductoDTO> productoDTOSFavoritos = productosFavoritosUsuario.get().stream().map(idP -> productoRepository.findById(idP).map(producto -> new ProductoDTO(
                         producto.getId(),
                         producto.getTitulo(),
                         producto.getDescripcion(),
                         producto.getPrecio()
                         )).orElse(null))
                         .filter(Objects::nonNull)  // Filtra productos nulos, si es necesario
                         .toList();

                 usuarioDTO.setProductosFavoritos(productoDTOSFavoritos);
           }

       }
        return Optional.of(usuarioDTO);

    };

    public Optional<UsuarioDTO>findByEmail(String email) throws ResourceNotFoundException {
        Optional<Usuario> usuarioFound = usuarioRepository.findByEmail(email);

        Optional<UsuarioDTO> userDTO;
        List<RecomendacionDTO> puntuacionesDTO;

        if(usuarioFound.isPresent()){
            Optional<List<Recomendacion>> recomendaciones = recomendacionRepository.findByUsuario_id(usuarioFound.get().getId());
            Optional<List<Integer>> productosFavoritosUsuario = usuarioRepository.findProductoIdsByUsuarioId(usuarioFound.get().getId());

            UsuarioDTO usuarioDTO = new UsuarioDTO();

            usuarioDTO.setId(usuarioFound.get().getId());
            usuarioDTO.setEmail(usuarioFound.get().getEmail());
            usuarioDTO.setPassword(passwordEncoder.encode(usuarioFound.get().getPassword()));
            usuarioDTO.setName(usuarioFound.get().getName());
            usuarioDTO.setApellido(usuarioFound.get().getApellido());
            usuarioDTO.setRole(usuarioFound.get().getRole());

            //Si tiene listado de puntuaciones asociadas, las traigo y las mappeo
            if(recomendaciones.isPresent()){
                puntuacionesDTO = recomendaciones.get().stream()
                        .map(recomendacion -> new RecomendacionDTO(
                                recomendacion.getId(),
                                recomendacion.getPuntaje_total(),
                                recomendacion.getFecha_publicacion(),
                                recomendacion.getDescripcion(),
                                new ProductoDTO(recomendacion.getProducto().getId()),
                                new UsuarioDTO(recomendacion.getUsuario().getId())
                        ))
                        .toList();
                usuarioDTO.setPuntuaciones(puntuacionesDTO);

            }

            //Si tiene productos favoritos asociados, los traigo y los mappeo
            if(productosFavoritosUsuario.isPresent()){
                List<ProductoDTO> productoDTOSFavoritos = productosFavoritosUsuario.get().stream().map(idP -> productoRepository.findById(idP).map(producto -> new ProductoDTO(
                                producto.getId(),
                                producto.getTitulo(),
                                producto.getDescripcion(),
                                producto.getPrecio()
                        )).orElse(null))
                        .filter(Objects::nonNull)  // Filtra productos nulos, si es necesario
                        .toList();

                usuarioDTO.setProductosFavoritos(productoDTOSFavoritos);
            }


            return Optional.of(usuarioDTO);
        }else {
            throw  new ResourceNotFoundException("No se encontro el usuario con email" + email);
        }

    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }


    public UsuarioDTO update(Usuario usuario){

        usuarioRepository.save(usuario);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<RecomendacionDTO> puntuacionesDTO;

        List<ProductoDTO> productosFavoritos = new ArrayList<>();
        if(!usuario.getProductosFavoritos().isEmpty()) {
            productosFavoritos = usuario.getProductosFavoritos().stream().map(producto -> new ProductoDTO(producto.getId(), producto.getTitulo(), producto.getDescripcion())).toList();
        }

        if(!usuario.getPuntuaciones().isEmpty()){
            puntuacionesDTO = usuario.getPuntuaciones().stream()
                    .map(recomendacion -> new RecomendacionDTO(
                            recomendacion.getId(),
                            recomendacion.getPuntaje_total(),
                            recomendacion.getFecha_publicacion(),
                            recomendacion.getDescripcion(),
                            new ProductoDTO(recomendacion.getProducto().getId()), // Asegúrate de mapear todos los atributos relevantes de Producto.
                            new UsuarioDTO(recomendacion.getUsuario().getId()) // Mapear el usuario
                    ))
                    .toList();

            usuarioDTO.setPuntuaciones(puntuacionesDTO);

        }

            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setTipo_usuario(usuario.getRole());
            usuarioDTO.setName(usuario.getName());
            usuarioDTO.setApellido(usuario.getApellido());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setPassword(usuario.getPassword());
            usuarioDTO.setRole(usuario.getRole());
            usuarioDTO.setProductosFavoritos(productosFavoritos);


        return usuarioDTO;
    }

    public Optional<UsuarioDTO> delete(Integer id) throws ResourceNotFoundException {
         Optional<Usuario> usuarioToFind = usuarioRepository.findById(id);
         Optional<UsuarioDTO> userDTO;

         if(usuarioToFind.isPresent()){
             Usuario user = usuarioToFind.get();

             UsuarioDTO userToReturn = new UsuarioDTO();
             userToReturn.setId(user.getId());
             userToReturn.setName(user.getName());
             userToReturn.setApellido(user.getApellido());
             userToReturn.setEmail(user.getEmail());
             userToReturn.setPassword(user.getPassword());

             userDTO = Optional.of(userToReturn);
             return userDTO;

         } else{
             //lanzar la exception
                throw  new ResourceNotFoundException("No se encontro el turno con id" + id);
         }
    }
}
