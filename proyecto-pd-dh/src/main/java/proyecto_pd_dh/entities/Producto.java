package proyecto_pd_dh.entities;


import jakarta.persistence.*;

import java.util.List;

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

    @Column(name="caracteristicas")
    private String caracteristicas;

    @Column(name="disponibilidad")
    private String disponibilidad;

    @Column(name="precio")
    private Double precio;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Recomendacion> recomendaciones;

    @ManyToOne
    private Catalogo catalogo;


    @OneToOne
    private Categoria categoria;

    @ManyToMany
    private List<Usuario> usuarios;


    public Producto(){}

    public Producto(Integer id, String titulo, String descripcion, String caracteristicas, String disponibilidad, Double precio, List<Recomendacion> recomendaciones, Catalogo catalogo, Categoria categoria, List<Usuario> usuarios) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.disponibilidad = disponibilidad;
        this.precio = precio;
        this.recomendaciones = recomendaciones;
        this.catalogo = catalogo;
        this.categoria = categoria;
        this.usuarios = usuarios;
    }

    public Producto(String titulo, String descripcion, String caracteristicas, String disponibilidad, Double precio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.disponibilidad = disponibilidad;
        this.precio = precio;
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

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
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

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
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
