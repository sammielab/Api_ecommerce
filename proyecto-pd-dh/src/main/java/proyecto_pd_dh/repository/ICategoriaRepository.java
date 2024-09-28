package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_pd_dh.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
}
