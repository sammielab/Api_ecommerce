package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.entities.Catalogo;
import proyecto_pd_dh.repository.ICatologoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogoServicio {


    @Autowired
    private ICatologoRepository catologoRepository;


    public Catalogo save(Catalogo catalogo){
        return catologoRepository.save(catalogo);
    }

    public Optional<Catalogo> findById(Integer id){
        return catologoRepository.findById(id);
    }

    public List<Catalogo> findAll(){
        return catologoRepository.findAll();
    }

    public Catalogo update(Catalogo catalogo){
        return catologoRepository.save(catalogo);
    }

    public void delete(Integer id){
        catologoRepository.deleteById(id);
    }
}
