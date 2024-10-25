package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import proyecto_pd_dh.entities.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer> {


    List<Producto> findAll();

    List<Producto> findByCaracteristicas_Id(Integer id);

    @Query("SELECT p FROM Producto p WHERE p.id NOT IN :ids")
    List<Producto> findProductsNotInIds(@Param("ids") Optional<List<Integer>> ids);

    @Query("""
    SELECT p FROM Producto p
    JOIN p.ubicacion u
    WHERE u.ciudad = :ciudad
    AND NOT EXISTS (
        SELECT r FROM Reserva r
        WHERE r.producto = p
        AND (
            (r.check_in <= :checkout AND r.check_out >= :checkin)
        )
    )
""")
    List<Producto> findAvailableProductos(@Param("ciudad") String ciudad,
                                          @Param("checkin") LocalDate checkin,
                                          @Param("checkout") LocalDate checkout);




}
