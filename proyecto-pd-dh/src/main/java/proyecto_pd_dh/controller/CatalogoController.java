package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.entities.Catalogo;
import proyecto_pd_dh.entities.Categoria;
import proyecto_pd_dh.service.CatalogoServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalogos")
public class CatalogoController {

    private final CatalogoServicio catalogoServicio;

    @Autowired
    public CatalogoController(CatalogoServicio catalogoServicio) {
        this.catalogoServicio = catalogoServicio;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Catalogo catalogo ){
        try{
            Catalogo savedCatalogo = catalogoServicio.save(catalogo);
            return ResponseEntity.ok(savedCatalogo);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("asd " + e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
            Optional<Catalogo> catalogoFound = catalogoServicio.findById(id);
            if(catalogoFound.isPresent()){
                return  ResponseEntity.ok(catalogoFound.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Catalogo>> findAll(){
        return ResponseEntity.ok(catalogoServicio.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Catalogo catalogo){
        try{
            Optional<Catalogo> catalogoFound = catalogoServicio.findById(catalogo.getId());
            if(catalogoFound.isPresent()){
                catalogoServicio.update(catalogo);
                return ResponseEntity.ok(catalogo);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Catalogo no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Optional<Catalogo> catalogoFound = catalogoServicio.findById(id);
            if(catalogoFound.isPresent()){
                catalogoServicio.delete(id);
                return ResponseEntity.ok(catalogoFound);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Catalogo no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }
}
