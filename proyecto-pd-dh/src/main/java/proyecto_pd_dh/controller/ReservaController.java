package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.dto.ReservaDTO;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Reserva;
import proyecto_pd_dh.service.ProductoServicio;
import proyecto_pd_dh.service.ReservaServicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {


    private final ReservaServicio reservaServicio;

    @Autowired
    public ReservaController(ReservaServicio reservaServicio) {
        this.reservaServicio = reservaServicio;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Reserva reserva ){
        try{
            Reserva savedReserva = reservaServicio.save(reserva);
            return ResponseEntity.ok(savedReserva);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("asd " + e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductoDTO>> findById(@RequestParam LocalDate checkin, @RequestParam LocalDate checkout){
        try{
            Optional<List<ProductoDTO>> reserva = reservaServicio.findByDate(checkin, checkout);

            if(reserva.isPresent()){
                return  ResponseEntity.ok(reserva.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/findUser/{id}")
    public ResponseEntity<Optional<List<ReservaDTO>>> findByUsuarioId(@PathVariable Integer id){
        try{
            Optional<List<ReservaDTO>> reservas = reservaServicio.findByUsuarioId(id);
            if(reservas.isPresent()){
                return  ResponseEntity.ok(Optional.of(reservas.get()));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Optional.empty());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Reserva>> findAll(){
        return ResponseEntity.ok(reservaServicio.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Reserva reserva){
        try{
            Optional<Reserva> reservaFound = reservaServicio.findById(reserva.getId());
            if(reservaFound.isPresent()){
                reservaServicio.update(reserva);
                return ResponseEntity.ok(reserva);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Optional<Reserva> reservaFound = reservaServicio.findById(id);
            if(reservaFound.isPresent()){
                reservaServicio.delete(id);
                return ResponseEntity.ok(reservaFound);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }
}
