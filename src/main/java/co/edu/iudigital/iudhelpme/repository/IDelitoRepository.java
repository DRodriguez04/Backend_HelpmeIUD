package co.edu.iudigital.iudhelpme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.iudigital.iudhelpme.model.Delito;

@Repository
public interface IDelitoRepository extends JpaRepository<Delito, Long> {

    Optional<Delito> findById(Long id);
    
    List<Delito> findByNombre(String nombre);
}
