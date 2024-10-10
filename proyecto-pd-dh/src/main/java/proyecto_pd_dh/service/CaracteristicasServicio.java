package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.entities.Caracteristica;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.repository.ICaracteristicasRepository;
import proyecto_pd_dh.repository.IProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CaracteristicasServicio {

    @Autowired
    private  ICaracteristicasRepository caracteristicasRepository;

    @Autowired
    private IProductoRepository productoRepository;

    public Caracteristica save(Caracteristica caracteristica) {
          return caracteristicasRepository.save(caracteristica);
    }

    public List<Caracteristica> findAll() {
    return caracteristicasRepository.findAll();
    }

    public Optional<Caracteristica> findById(Integer id) {
        return caracteristicasRepository.findById(id);
    }

    public Caracteristica update(Caracteristica caracteristica) {
        return caracteristicasRepository.save(caracteristica);
    }

    public void delete(Integer id) {

        Caracteristica caracteristica = caracteristicasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Característica no encontrada"));

        List<Producto> productos = productoRepository.findByCaracteristicas_Id(id);

        for (Producto producto : productos) {
            producto.getCaracteristicas().remove(caracteristica); // Remueve la característica del producto
            productoRepository.save(producto); // Guarda el producto actualizado
        }

        caracteristicasRepository.deleteById(id);
    }
}
