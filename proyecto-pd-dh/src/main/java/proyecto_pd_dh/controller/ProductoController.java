package proyecto_pd_dh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.entities.Caracteristica;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.exception.ResourceNotFoundException;
import proyecto_pd_dh.service.ProductoServicio;
import proyecto_pd_dh.service.UsuarioServicio;

import java.time.LocalDate;
import java.util.ArrayList;
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
        System.out.println("Producto" + producto);

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
            Optional<ProductoDTO> foundedProduct = productoServicio.findById(id);
            if(foundedProduct.isPresent()){
                return  ResponseEntity.ok(foundedProduct.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductoDTO>> getdAll(
            @RequestParam int page,
            @RequestParam int limit
    ){
        return ResponseEntity.ok(productoServicio.getAll(page, limit));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductoDTO>> findAll() throws ResourceNotFoundException {
        try{
            List<ProductoDTO> productoDTOS = productoServicio.findAll();
            return ResponseEntity.ok(productoDTOS);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Producto producto){
        try{
            Optional<ProductoDTO> productFound = productoServicio.findById(producto.getId());
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
            Optional<ProductoDTO> producFound = productoServicio.findById(id);
            if(producFound.isPresent()){
                productoServicio.delete(id);
                return ResponseEntity.ok("Producto eliminado correctamente");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }

    @PostMapping("/findByCategoryId")
    public ResponseEntity<?> findByCategoryId(@RequestBody List<Integer> categorias){

        try{
            List<ProductoDTO> productos = productoServicio.findProductoByCategoriaId(categorias);
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }


    @GetMapping("/findFilteredProducts")
    public ResponseEntity<?> findFilteredProducts(@RequestParam String ciudad,
                                                    @RequestParam LocalDate checkin,
                                                    @RequestParam LocalDate checkout
                                                  )
    {
        System.out.println("ciudad: " + ciudad);
        try{
            List<ProductoDTO> productos = productoServicio.findFilteredProductos(ciudad, checkin, checkout);
            return ResponseEntity.ok(productos);
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    };

}
