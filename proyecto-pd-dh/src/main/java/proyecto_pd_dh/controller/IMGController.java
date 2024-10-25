package proyecto_pd_dh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proyecto_pd_dh.dto.IMGDTO;
import proyecto_pd_dh.entities.Categoria;
import proyecto_pd_dh.entities.IMG;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.service.ImgServicio;
import proyecto_pd_dh.service.ProductoServicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imagenes")
@CrossOrigin(origins = "http://localhost:5173")
public class IMGController {

    private final ImgServicio imgServicio;
    private final ProductoServicio productoServicio;


    public IMGController(ImgServicio imgServicio, ProductoServicio productoServicio) {
        this.imgServicio = imgServicio;
        this.productoServicio = productoServicio;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam Producto producto ){
        try{
            IMGDTO saveImg = imgServicio.save(imagen, producto);
            return ResponseEntity.ok(saveImg);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar imagen " + e.getMessage());
        }
    }


    @GetMapping("/findByProductId/{id}")
    public ResponseEntity<?> findByIdProducto(@PathVariable Integer id){
        try{
            Optional<List<IMGDTO>> images = imgServicio.findByIdProducto(id);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
