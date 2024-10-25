package proyecto_pd_dh.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @Column(name = "email")
    private String email;

    @Column(name="password")
    private String password;

    @ManyToMany
    @JoinTable(name = "usuarios_productos", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productosFavoritos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reserva> reservas;


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Recomendacion> puntuaciones;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Usuario(String name, String apellido, String email, String password, Role role) {
        this.name = name;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public Usuario(Integer id,  List<Producto> productosFavoritos){
        setProductosFavoritos(productosFavoritos);
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", productosFavoritos=" + productosFavoritos +
                ", reservas=" + reservas +
                ", puntuaciones=" + puntuaciones +
                ", role=" + role +
                '}';
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
