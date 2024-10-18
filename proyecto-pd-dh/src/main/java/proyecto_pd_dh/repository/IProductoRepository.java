package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import proyecto_pd_dh.entities.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer> {
    List<Producto> findByCaracteristicas_Id(Integer id);

    @Query("SELECT p FROM Producto p WHERE p.id NOT IN :ids")
    List<Producto> findProductsNotInIds(@Param("ids") Optional<List<Integer>> ids);


}
