package com.example.moviecrud.business;

import com.example.moviecrud.business.entities.Pelicula;
import com.example.moviecrud.business.entities.Usuario;
import com.example.moviecrud.business.exceptions.InformacionInvalida;
import com.example.moviecrud.business.exceptions.NoExiste;
import com.example.moviecrud.business.exceptions.YaExiste;
import com.example.moviecrud.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class UsuarioMgr {

    @Autowired
    UsuarioRepository usuarioRepository;

    // Create user
    // @PostMapping("/Usuario")
    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    //Edit usuario by id
    // @PutMapping("/usuario/{id}")
    public void update (@PathVariable("id") Long id, Usuario usuario){
        usuario.setId(id);
        usuarioRepository.save(usuario);
    }

    //Get all users
    // @GetMapping("/users")
    public List<Usuario> getAllUsuarios(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    // Get a Single user by id
    // @GetMapping("/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable(value = "id") Long usuarioId) {
        return usuarioRepository.findById(usuarioId).get();
    }

    public Usuario getUsuarioByName(String nUsuario) {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        int l = usuarios.size();
        Usuario temp = null;
        for (int i = 0; i <l ; i++) {
            if (usuarios.get(i).getUsername().equals(nUsuario)) {
                temp = usuarios.get(i);
            }
        }
        return temp;
    }

    // Delete a Usuario by id
    //  @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable(value = "id") Long usuarioId) {
        Usuario usuario= usuarioRepository.findById(usuarioId).get();
        usuarioRepository.delete(usuario);
        return ResponseEntity.ok().build();
    }

    public void addUsuario(String username, String password, String email) throws InformacionInvalida, YaExiste, IOException {
        if(username == null || "".equals(username) || password == null || "".equals(password) || email == null || "".equals(email)  ){

            throw new InformacionInvalida("Algun dato ingresado no es correcto");

        }



        // Ahora hay que ver si el usuario existe ya
        // if (peliculaRepository.findById(peliculaI)
        // problema para hacer el get de un id que se autogenera

        Usuario usuario = new Usuario(username,password,email);

        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario (String username)  throws InformacionInvalida, NoExiste {
        for (Usuario usuario: getAllUsuarios()) {
            if (usuario.getUsername().equals(username)){
                Long id = usuario.getId();
                deleteUsuario(id);
            }
        }

    }

    public void editarUsuario (String usernameViejo, String usernameNuevo, String password, String email) throws InformacionInvalida, NoExiste {
        if(usernameNuevo == null || "".equals(usernameNuevo) || usernameViejo == null || "".equals(usernameViejo)  || password == null || "".equals(password) || email == null || "".equals(email) ){

            throw new InformacionInvalida("Algun dato ingresado no es correcto");

        }

        for (Usuario usuario: getAllUsuarios()) {
            if (usuario.getUsername().equals(usernameViejo)){
                Long id = usuario.getId();
                Usuario usuarioActualizado = new Usuario(usernameNuevo,password ,email);
                update(id,usuarioActualizado);
            }
        }
    }
}