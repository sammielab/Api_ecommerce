package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.repository.IProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private IProductoRepository productoRepository;


    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public Optional<Producto> findById(Integer id){
        return productoRepository.findById(id);
    }

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto update(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Integer id){
        productoRepository.deleteById(id);
    }

}
