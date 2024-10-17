package proyecto_pd_dh.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @JoinTable(name = "producto_caracteristicas", joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
    private List<Caracteristica> caracteristicas;


    @Column(name="precio")
    private Double precio;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Recomendacion> recomendaciones;

    @ManyToMany(mappedBy = "productos", cascade = CascadeType.ALL)
    private List<Catalogo> catalogos;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @ManyToMany(mappedBy = "productosFavoritos")
    private List<Usuario> usuarios;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;




    public Producto(Integer id, String titulo, String descripcion, List<Caracteristica> caracteristicas, Double precio, List<Recomendacion> recomendaciones, Catalogo catalogo, Categoria categoria, List<Usuario> usuarios) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.precio = precio;
        this.recomendaciones = recomendaciones;
        this.categoria = categoria;
        this.usuarios = usuarios;
    }

    public Producto(String titulo, String descripcion, List<Caracteristica> caracteristicas, String disponibilidad, Double precio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.precio = precio;
    }

    public Producto(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }


    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<Recomendacion> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<Recomendacion> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }



}
