package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_pd_dh.entities.Catalogo;

public interface ICatologoRepository extends JpaRepository<Catalogo, Integer> {
}
