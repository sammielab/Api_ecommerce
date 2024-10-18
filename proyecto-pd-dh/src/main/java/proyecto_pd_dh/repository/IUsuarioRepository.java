package proyecto_pd_dh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    @Query(value = "SELECT producto_id FROM usuarios_productos WHERE usuario_id = :userId", nativeQuery = true)
    Optional<List<Integer>> findProductoIdsByUsuarioId(@Param("userId") Integer userId);

}
