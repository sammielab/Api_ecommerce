package proyecto_pd_dh.repository;

import proyecto_pd_dh.entities.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecomendacionRepository extends JpaRepository<Recomendacion, Integer> {
}
