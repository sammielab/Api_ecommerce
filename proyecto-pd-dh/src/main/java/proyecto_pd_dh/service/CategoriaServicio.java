package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.entities.Catalogo;
import proyecto_pd_dh.entities.Categoria;
import proyecto_pd_dh.repository.ICategoriaRepository;
import proyecto_pd_dh.repository.ICatologoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicio {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    public Categoria save(Categoria catalogo){
        return categoriaRepository.save(catalogo);
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
