package proyecto_pd_dh.controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.dto.UbicacionDTO;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Ubicacion;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.service.ProductoServicio;
import proyecto_pd_dh.service.UbicacionesServicio;

import java.util.List;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@RestController
@RequestMapping("/ubicaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class UbicacionController {

    @Autowired
    private final UbicacionesServicio ubicacionesServicio;


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ubicacion ubicacion ){
        try{
            UbicacionDTO saveUbicacion = ubicacionesServicio.save(ubicacion);
            return ResponseEntity.ok(saveUbicacion);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la creacion de ubicacion" + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UbicacionDTO>> findAll() throws ResourceNotFoundException {
        try{
            List<UbicacionDTO> ubicacionesDTO = ubicacionesServicio.findAll();
            return ResponseEntity.ok(ubicacionesDTO);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/findByCity/{pattern}")
    public ResponseEntity<List<String>> findByCiudadPattern(@PathVariable String pattern){
        try{
            List<String> ciudades = ubicacionesServicio.findByCiudadPattern(pattern);
            return ResponseEntity.ok(ciudades);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
