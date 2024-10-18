package proyecto_pd_dh.repository;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_pd_dh.entities.Reserva;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT r.producto.id FROM Reserva r WHERE " +
            "(:startDate <= r.check_out AND :endDate >= r.check_in)"
    )
    Optional<List<Integer>> findUnavaibleProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    Optional<List<Reserva>> findByUsuarioId(Integer id);
    Optional<List<Reserva>> findByProductoId(Integer productoId);
}
