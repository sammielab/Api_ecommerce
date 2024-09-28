package proyecto_pd_dh.repository;

import proyecto_pd_dh.entities.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}
