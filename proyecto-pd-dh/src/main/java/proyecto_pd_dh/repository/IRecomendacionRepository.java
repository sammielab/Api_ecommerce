package proyecto_pd_dh.repository;

import proyecto_pd_dh.entities.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRecomendacionRepository extends JpaRepository<Recomendacion, Integer> {
 Optional<List<Recomendacion>> findByUsuario_id(Integer id);
}
