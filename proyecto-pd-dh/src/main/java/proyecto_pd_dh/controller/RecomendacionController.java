package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.dto.RecomendacionDTO;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.entities.Reserva;
import proyecto_pd_dh.service.RecomendacionServicio;
import proyecto_pd_dh.service.ReservaServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recomendaciones")
public class RecomendacionController {

    private final RecomendacionServicio recomendacionServicio;

    @Autowired
    public RecomendacionController(RecomendacionServicio recomendacionServicio) {
        this.recomendacionServicio = recomendacionServicio;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Recomendacion recomendacion ){
        try{
            RecomendacionDTO savedRecomendacion = recomendacionServicio.save(recomendacion);
            return ResponseEntity.ok(savedRecomendacion);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("asd " + e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
            Optional<Recomendacion> recomendacionFound = recomendacionServicio.findById(id);
            if(recomendacionFound.isPresent()){
                return  ResponseEntity.ok(recomendacionFound.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Recomendacion>> findAll(){
        return ResponseEntity.ok(recomendacionServicio.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Recomendacion recomendacion){
        try{
            Optional<Recomendacion> recomendacionFound = recomendacionServicio.findById(recomendacion.getId());
            if(recomendacionFound.isPresent()){
                recomendacionServicio.update(recomendacion);
                return ResponseEntity.ok(recomendacion);
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
            Optional<Recomendacion> recomendacionFound = recomendacionServicio.findById(id);
            if(recomendacionFound.isPresent()){
                recomendacionServicio.delete(id);
                return ResponseEntity.ok(recomendacionFound);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recomendacion no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }
}
