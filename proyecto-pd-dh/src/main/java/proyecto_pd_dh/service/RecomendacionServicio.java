package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.repository.IProductoRepository;
import proyecto_pd_dh.repository.IRecomendacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecomendacionServicio {

    @Autowired
    private IRecomendacionRepository recomendacionRepository;

    public Recomendacion save(Recomendacion recomendacion){
        return recomendacionRepository.save(recomendacion);
    }

    public Optional<Recomendacion> findById(Integer id){
        return recomendacionRepository.findById(id);
    }

    public List<Recomendacion> findAll(){
        return recomendacionRepository.findAll();
    }

    public Recomendacion update(Recomendacion recomendacion){
        return recomendacionRepository.save(recomendacion);
    }

    public void delete(Integer id){
        recomendacionRepository.deleteById(id);
    }

}
