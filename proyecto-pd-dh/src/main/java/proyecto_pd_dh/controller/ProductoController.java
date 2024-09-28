package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.service.ProductoServicio;
import proyecto_pd_dh.service.UsuarioServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    private final ProductoServicio productoServicio;

    @Autowired
    public ProductoController(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Producto producto ){
        try{
            Producto savedProducto = productoServicio.save(producto);
            return ResponseEntity.ok(savedProducto);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("asd " + e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
            Optional<Producto> foundedProduct = productoServicio.findById(id);
            if(foundedProduct.isPresent()){
                return  ResponseEntity.ok(foundedProduct.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Producto>> findAll(){
        return ResponseEntity.ok(productoServicio.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Producto producto){
        try{
            Optional<Producto> productFound = productoServicio.findById(producto.getId());
            if(productFound.isPresent()){
                productoServicio.update(producto);
                return ResponseEntity.ok(producto);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Optional<Producto> producFound = productoServicio.findById(id);
            if(producFound.isPresent()){
                productoServicio.delete(id);
                return ResponseEntity.ok(producFound);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }
}