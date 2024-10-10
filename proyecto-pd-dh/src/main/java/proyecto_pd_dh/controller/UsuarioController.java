package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import proyecto_pd_dh.dto.UsuarioDTO;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.service.UsuarioServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    private final UsuarioServicio usuarioService;

    @Autowired
    public UsuarioController(UsuarioServicio usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/save")
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO usuarioDTO){
        ResponseEntity<UsuarioDTO> response;

           if(usuarioService.findById(usuarioDTO.getId()).isPresent()){
               response = ResponseEntity.ok(usuarioService.save(usuarioDTO));

           }else{
               response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

           }

        return response;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
           Optional<Usuario> foundedUsuario = usuarioService.findById(id);
           if(foundedUsuario.isPresent()){
               return  ResponseEntity.ok(foundedUsuario.get());
           }else{
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }

    }

    @GetMapping("/findByEmail")
    public ResponseEntity<?> findByEmail(@RequestParam String email){
        try{
            Optional<Usuario> userFound = Optional.ofNullable(usuarioService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario no encontrado")));
            return ResponseEntity.ok(userFound);
            }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        }


    @GetMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Usuario usuario){
        try{
            Optional<Usuario> userFound = usuarioService.findById(usuario.getId());
            if(userFound.isPresent()){
                usuarioService.update(usuario);
                return ResponseEntity.ok(usuario);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        usuarioService.delete(id);
        return ResponseEntity.ok("Se elimino el turno con id " + id);
    }

}
