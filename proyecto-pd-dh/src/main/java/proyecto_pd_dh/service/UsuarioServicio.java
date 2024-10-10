package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.UsuarioDTO;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.repository.IUsuarioRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    public Optional<Usuario> findById(Integer id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario>findByEmail(String email){
        return usuarioRepository.findByEmail(email);
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
