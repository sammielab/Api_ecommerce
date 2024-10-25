package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import proyecto_pd_dh.entities.Catalogo;
import proyecto_pd_dh.entities.Categoria;
import proyecto_pd_dh.repository.ICategoriaRepository;
import proyecto_pd_dh.repository.ICatologoRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicio {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    public Categoria save(MultipartFile imagen, String titulo) throws Exception {
        try{

        Categoria categoria = new Categoria();
        categoria.setTitulo(titulo);

        String imagenBase64 =  encodeImageToBase64(imagen);
        categoria.setImagen(imagenBase64);

        return categoriaRepository.save(categoria);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String encodeImageToBase64(MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        return Base64.getEncoder().encodeToString(imageData);
    }


    public Optional<Categoria> findById(Integer id){
        return categoriaRepository.findById(id);
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria update(Categoria catalogo){
        return categoriaRepository.save(catalogo);
    }

    public void delete(Integer id){
        categoriaRepository.deleteById(id);
    }
}
