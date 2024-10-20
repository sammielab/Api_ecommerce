package proyecto_pd_dh.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyecto_pd_dh.entities.Ubicacion;

import java.util.List;

@Repository
public interface IUbicacionesRepository extends JpaRepository<Ubicacion, Integer> {

    @Query("SELECT u.ciudad FROM Ubicacion u WHERE u.ciudad LIKE %:pattern%")
    List<String> findByCiudadPattern(@Param("pattern") String pattern);
}
