package co.edu.iudigital.iudhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.iudigital.iudhelpme.model.Caso;

@Repository
public interface ICasoRepository extends JpaRepository<Caso, Long> {
    
    @Query("UPDATE Caso c SET c.visibilidad=?1 WHERE id=?2") // 1 corresponde a visbilidad y 2 corresponde a id
    Boolean setVisibilidad(Boolean visibilidad, Long id);
}
