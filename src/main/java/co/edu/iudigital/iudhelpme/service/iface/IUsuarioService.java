package co.edu.iudigital.iudhelpme.service.iface;

import java.util.List;

import co.edu.iudigital.iudhelpme.dto.request.UsuarioDTORequest;
import co.edu.iudigital.iudhelpme.dto.response.UsuarioDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;

public interface IUsuarioService {

    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO consultaPorId(Long id);

    UsuarioDTO consultaPorNombre(String nombre);

    UsuarioDTO buscarPorUsername(String username);

    UsuarioDTO guardarUsuario(UsuarioDTORequest usuarioDTORequest) throws RestException;

    boolean eliminarPorId(Long id);
}
