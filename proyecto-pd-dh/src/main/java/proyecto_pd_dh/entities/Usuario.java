package proyecto_pd_dh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="apellido")
    private String apellido;

    @Column(name="tipo_usuario")
    private String tipo_usuario;

    @Column(name = "email")
    private String email;

    @Column(name="password")
    private String password;

    @ManyToMany
    private List<Producto> productosFavoritos;

    @OneToMany(mappedBy = "usuario")
    private List<Recomendacion> puntuaciones;

    @Enumerated(EnumType.STRING)
    private Role role;



    public Usuario(String name, String apellido, String tipo_usuario, String email, String password) {
        this.name = name;
        this.apellido = apellido;
        this.tipo_usuario = tipo_usuario;
        this.email = email;
        this.password = password;
    }

    public Usuario(Integer id, String name, String apellido, String tipo_usuario, String email, String password, List<Producto> productosFavoritos, List<Recomendacion> puntuaciones) {
        this.id = id;
        this.name = name;
        this.apellido = apellido;
        this.tipo_usuario = tipo_usuario;
        this.email = email;
        this.password = password;
        this.productosFavoritos = productosFavoritos;
        this.puntuaciones = puntuaciones;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Producto> getProductosFavoritos() {
        return productosFavoritos;
    }

    public void setProductosFavoritos(List<Producto> productosFavoritos) {
        this.productosFavoritos = productosFavoritos;
    }

    public List<Recomendacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(List<Recomendacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }
}
