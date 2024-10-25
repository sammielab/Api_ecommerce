package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import proyecto_pd_dh.dto.IMGDTO;
import proyecto_pd_dh.entities.IMG;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.repository.IProductoRepository;
import proyecto_pd_dh.repository.IimgRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ImgServicio {

    @Autowired
    private IimgRepository imgRepository;
    @Autowired
    private IProductoRepository productoRepository;

    public IMGDTO save(MultipartFile imagen, Producto producto) throws Exception {
        try {
            Optional<Producto> productoToFind = Optional.of(productoRepository.findById(producto.getId())
                    .orElseThrow(() -> new RuntimeException("No se encuentra el producto ")));

            IMG img = new IMG();
            img.setProducto(productoToFind.get());

            String imagenBase64 =  encodeImageToBase64(imagen);
            img.setImagen(imagenBase64);

            imgRepository.save(img);

            IMGDTO imgdto = new IMGDTO(img.getImagen());
            return imgdto;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    };


    public String encodeImageToBase64(MultipartFile imagen) throws IOException {
        byte[] imageData = imagen.getBytes();
        return Base64.getEncoder().encodeToString(imageData);
    };


    public Optional<List<IMGDTO>> findByIdProducto(Integer id) throws Exception {
        Optional<List<IMG>> findImgsByProductoId = imgRepository.findByProductoId(id);

        if(findImgsByProductoId.isPresent()){

            List<IMGDTO> imgdtos = findImgsByProductoId.get().stream().map(imagen -> new IMGDTO(imagen.getImagen())).toList();

            return Optional.of(imgdtos);

        }else{
            throw new Exception("No se encontraron imagenes para el producto ");
        }



    }
}
