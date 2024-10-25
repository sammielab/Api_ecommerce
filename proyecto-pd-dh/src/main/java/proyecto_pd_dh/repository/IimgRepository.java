package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_pd_dh.entities.IMG;

import java.util.List;
import java.util.Optional;

public interface IimgRepository extends JpaRepository<IMG, Integer> {

    @Query("SELECT i FROM IMG i WHERE i.producto.id = :idProducto")
    Optional<List<IMG>> findByProductoId(@Param("idProducto") Integer idProducto);
}
