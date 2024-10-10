package proyecto_pd_dh.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.entities.Caracteristica;
import proyecto_pd_dh.entities.Categoria;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.service.CaracteristicasServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/caracteristicas")
@AllArgsConstructor
public class CaracteristicasController {

    @Autowired
    private final CaracteristicasServicio caracteristicasServicio;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Caracteristica caracteristica ){
        try{
            Caracteristica caracteristicaSaved = caracteristicasServicio.save(caracteristica);
            return ResponseEntity.ok(caracteristicaSaved);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("asd " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Caracteristica>> findAll(){
        return ResponseEntity.ok(caracteristicasServicio.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Caracteristica caracteristica){
        try{
            Optional<Caracteristica> caracteristicaFound = caracteristicasServicio.findById(caracteristica.getId());
            if(caracteristicaFound.isPresent()){
                caracteristicasServicio.update(caracteristica);
                return ResponseEntity.ok(caracteristica);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caracteristica no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        caracteristicasServicio.delete(id);
        return ResponseEntity.ok("Se elimino la caracterista con id " + id);
    }


}
