package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_pd_dh.entities.Reserva;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
}
