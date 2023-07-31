package co.edu.iudigital.iudhelpme.repository;

import java.util.List;

import co.edu.iudigital.iudhelpme.dto.response.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.iudhelpme.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByUsername(String username);

    List<Usuario> findByNombre(String nombre);

    //Usuario findByApellidoAsc(String apellido);
}
