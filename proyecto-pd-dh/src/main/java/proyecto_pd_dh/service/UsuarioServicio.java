package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.UsuarioDTO;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IUsuarioRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio {

    @Autowired
    private  IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServicio(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    public UsuarioDTO save(UsuarioDTO usuarioDTO){
        //mappeo de dto
        Usuario usuarioEntity = new Usuario( usuarioDTO.getName(), usuarioDTO.getApellido(), usuarioDTO.getEmail(), usuarioDTO.getPassword(), usuarioDTO.getTipo_usuario());
        usuarioRepository.save(usuarioEntity);

        UsuarioDTO returnUsuarioDTO = new UsuarioDTO();
        returnUsuarioDTO.setId(usuarioEntity.getId());
        returnUsuarioDTO.setName(usuarioEntity.getName());
        returnUsuarioDTO.setApellido(usuarioEntity.getApellido());
        returnUsuarioDTO.setTipo_usuario(usuarioEntity.getRole());
        returnUsuarioDTO.setEmail(usuarioEntity.getEmail());

        return returnUsuarioDTO;
    }

    public Optional<UsuarioDTO> findById(Integer id){
       Optional<Usuario> usuarioFound =  usuarioRepository.findById(id);
        UsuarioDTO usuarioDTO = new UsuarioDTO();

       if(usuarioFound.isPresent()){

           usuarioDTO.setId(usuarioFound.get().getId());
           usuarioDTO.setTipo_usuario(usuarioFound.get().getRole());
           usuarioDTO.setName(usuarioFound.get().getName());
           usuarioDTO.setApellido(usuarioFound.get().getApellido());
           usuarioDTO.setEmail(usuarioFound.get().getEmail());
           usuarioDTO.setPassword(usuarioFound.get().getPassword());

       }
        return Optional.of(usuarioDTO);

    };

    public Optional<UsuarioDTO>findByEmail(String email) throws ResourceNotFoundException {
        Optional<Usuario> usuarioFound = usuarioRepository.findByEmail(email);
        Optional<UsuarioDTO> userDTO;

        if(usuarioFound.isPresent()){

            UsuarioDTO usuarioDTO = new UsuarioDTO();

            List<Integer> puntuacionesIds = usuarioFound.get().getPuntuaciones().stream()
                    .map(Recomendacion::getId)
                    .collect(Collectors.toList());


            usuarioDTO.setId(usuarioFound.get().getId());
            usuarioDTO.setEmail(usuarioFound.get().getEmail());
            usuarioDTO.setPassword(usuarioFound.get().getPassword());
            usuarioDTO.setName(usuarioFound.get().getName());
            usuarioDTO.setApellido(usuarioFound.get().getApellido());
            usuarioDTO.setProductosFavoritos(usuarioFound.get().getProductosFavoritos());
            usuarioDTO.setPuntuaciones(puntuacionesIds);

            userDTO = Optional.of(usuarioDTO);
            return userDTO;
        }else {
            throw  new ResourceNotFoundException("No se encontro el usuario con email" + email);
        }

    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario update(Usuario usuario){
        return usuarioRepository.save(usuario);
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
