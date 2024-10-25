package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proyecto_pd_dh.entities.Categoria;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.service.CategoriaServicio;
import proyecto_pd_dh.service.RecomendacionServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaServicio categoriaServicio;

    @Autowired
    public CategoriaController(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam String titulo ){
        try{
            Categoria savedCategoria = categoriaServicio.save(imagen, titulo);
            return ResponseEntity.ok(savedCategoria);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("asd " + e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
            Optional<Categoria> categoriaFound = categoriaServicio.findById(id);
            if(categoriaFound.isPresent()){
                return  ResponseEntity.ok(categoriaFound.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Categoria>> findAll(){
        return ResponseEntity.ok(categoriaServicio.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Categoria categoria){
        try{
            Optional<Categoria> categoriaFound = categoriaServicio.findById(categoria.getId());
            if(categoriaFound.isPresent()){
                categoriaServicio.update(categoria);
                return ResponseEntity.ok(categoria);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recomendacion no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Optional<Categoria> categoriaFound = categoriaServicio.findById(id);
            if(categoriaFound.isPresent()){
                categoriaServicio.delete(id);
                return ResponseEntity.ok(categoriaFound);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recomendacion no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }
}
