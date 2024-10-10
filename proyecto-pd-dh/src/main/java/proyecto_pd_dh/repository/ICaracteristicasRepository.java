package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto_pd_dh.entities.Caracteristica;

@Repository
public interface ICaracteristicasRepository extends JpaRepository<Caracteristica, Integer> {
}
