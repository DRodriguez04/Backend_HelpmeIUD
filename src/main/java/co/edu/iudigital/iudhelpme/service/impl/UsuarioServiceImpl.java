package co.edu.iudigital.iudhelpme.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.iudhelpme.dto.request.UsuarioDTORequest;

import co.edu.iudigital.iudhelpme.dto.response.UsuarioDTO;

import co.edu.iudigital.iudhelpme.exceptions.BadRequestException;
import co.edu.iudigital.iudhelpme.exceptions.ErrorDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.model.Rol;
import co.edu.iudigital.iudhelpme.model.Usuario;
import co.edu.iudigital.iudhelpme.repository.IUsuarioRepository;
import co.edu.iudigital.iudhelpme.service.iface.IUsuarioService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = iUsuarioRepository.findAll();

        // Mapeamos la lista de Usuario a una lista de UsuarioDTO
        return usuarios.stream()
                .map(usuario -> UsuarioDTO.builder()
                        .username(usuario.getUsername())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .fechaNacimiento(usuario.getFechaNacimiento())
                        .enabled(usuario.getEnabled())
                        .image(usuario.getImage())
                        .roles(usuario.getRoles().stream()
                                .map(Rol::getNombre)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UsuarioDTO consultaPorId(Long id) {
        Optional<Usuario> usuarioOptional = iUsuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Obtener una lista de nombres de roles desde la colección de objetos Rol
            List<String> roles = usuario.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());

            return UsuarioDTO.builder()
                    .username(usuario.getUsername())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .fechaNacimiento(usuario.getFechaNacimiento())
                    .enabled(usuario.getEnabled())
                    .image(usuario.getImage())
                    .roles(roles) // Asignar la lista de nombres de roles
                    .build();
        }
        log.warn("No existe el usuario con ID {} " + id + " proporcionado");
        return null;
    }

    @Override
    public UsuarioDTO consultaPorNombre(String nombre) {
        List<Usuario> usuarioOptional = iUsuarioRepository.findByNombre(nombre);
        if (!usuarioOptional.isEmpty()) {
            Usuario usuario = usuarioOptional.get(0);

            // Obtener una lista de nombres de roles desde la colección de objetos Rol
            List<String> roles = usuario.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());

            return UsuarioDTO.builder()
                    .username(usuario.getUsername())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .fechaNacimiento(usuario.getFechaNacimiento())
                    .enabled(usuario.getEnabled())
                    .image(usuario.getImage())
                    .roles(roles) // Asignar la lista de nombres de roles
                    .build();
        }
        log.warn("No existe el usuario {}", nombre);
        return null;
    }

    @Override
    public UsuarioDTO buscarPorUsername(String username) {
        Usuario usuario = iUsuarioRepository.findByUsername(username);

        // convertimos  Usuario en UsuarioDTO
        if (usuario != null) {
            return UsuarioDTO.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .fechaNacimiento(usuario.getFechaNacimiento())
                    .enabled(usuario.getEnabled())
                    .image(usuario.getImage())
                    .roles(usuario.getRoles()
                            .stream().map(Rol::getNombre)
                            .collect(Collectors.toList()))
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public UsuarioDTO guardarUsuario(UsuarioDTORequest usuarioDTORequest) throws RestException {
        Usuario usuario;
        Rol rol = new Rol();
        rol.setId(2L);
        usuario = iUsuarioRepository.findByUsername(usuarioDTORequest.getUsername());
        if (usuario != null) {
            throw new BadRequestException(
                    ErrorDTO.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Este usuario ya existe")
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .date(LocalDateTime.now())
                            .build());
        }

        // Verificamos si los campos obligatorios no son nulos o vacíos
        if (usuarioDTORequest.getUsername() == null || usuarioDTORequest.getUsername().isEmpty()
                || usuarioDTORequest.getNombre() == null || usuarioDTORequest.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de usuario y el nombre son campos obligatorios");
        }
        // Validamos que  enabled no sea nulo
        boolean enabled = usuarioDTORequest.getEnabled() != null ? usuarioDTORequest.getEnabled() : true;

        usuario = new Usuario();
        // Convertir usuarioDTORequest a usuario
        usuario.setNombre(usuarioDTORequest.getNombre());
        usuario.setUsername(usuarioDTORequest.getUsername());
        usuario.setPassword(usuarioDTORequest.getPassword());
        usuario.setApellido(usuarioDTORequest.getApellido());
        usuario.setImage(usuarioDTORequest.getImage());
        usuario.setEnabled(true);
        usuario.setFechaNacimiento(usuarioDTORequest.getFechaNacimiento());
        usuario.setRoles(Collections.singletonList(rol));

        usuario = iUsuarioRepository.save(usuario);
        // Convertir de usuario a usuarioDTO

        if (usuario != null) {
            return UsuarioDTO.builder()
                    .username(usuario.getUsername())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .enabled(usuario.getEnabled())
                    .fechaNacimiento(usuario.getFechaNacimiento())
                    .image(usuario.getImage())
                    .roles(usuario.getRoles()
                            .stream().map(r -> r.getNombre())
                            .collect(Collectors.toList()))
                    .build();
        } else {
            log.warn("Usuario  nulo");
            return null;
        }
    }

    @Override
    public boolean eliminarPorId(Long id) {
        Optional<Usuario> usuarioOptional = iUsuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            iUsuarioRepository.deleteById(id);
            log.info("Usuario eliminado satisfactoriamente");
            return true; // Indica que el usuario se borró correctamente.
        } else {
            return false; // Indica que no se encontró ningún usuario con el ID proporcionado.
        }
    }

    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuarioRepository.findByUsername(username);
        if (usuario == null) {
            log.error("Error de login, no existe usuario: " + usuario);
            throw new UsernameNotFoundException("Error de login, no existe usuario: " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol role : usuario.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getNombre());
            log.info("Rol {}", authority.getAuthority());
            authorities.add(authority);
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
                authorities);
    }

    /*
     * @Override
     * public UsuarioDTO consultaPorUsername(String username) {
     * return null;
     * }
     */
}
