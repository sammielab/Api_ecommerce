package proyecto_pd_dh.dto;

import jakarta.persistence.*;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.entities.Role;

import java.util.List;

public class UsuarioDTO {

    private Integer id;

    private String name;

    private String apellido;

    private String email;

    private String password;

    private List<ProductoDTO> productosFavoritos;

    private List<RecomendacionDTO> puntuaciones;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Role getTipo_usuario() {
        return role;
    }

    public void setTipo_usuario(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductoDTO> getProductosFavoritos() {
        return productosFavoritos;
    }

    public void setProductosFavoritos(List<ProductoDTO> productosFavoritos) {
        this.productosFavoritos = productosFavoritos;
    }

    public List<RecomendacionDTO> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(List<RecomendacionDTO> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
