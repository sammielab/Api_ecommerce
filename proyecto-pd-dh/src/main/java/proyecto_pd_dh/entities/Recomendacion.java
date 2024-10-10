package proyecto_pd_dh.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "recomendaciones")
public class Recomendacion {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="puntaje")
    private double puntaje_total;

    private Date fecha_publicacion;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @JoinColumn(name = "usuario_id")
    @ManyToOne
    private Producto producto;

    @JoinColumn(name = "producto_id")
    @ManyToOne
    private Usuario usuario;

    public Recomendacion(){}


    public Recomendacion(Double puntaje_total, Producto producto) {
        this.puntaje_total = puntaje_total;
        this.producto = producto;
    }

    public Recomendacion(Integer id, double puntaje_total, Producto producto, Usuario usuario) {
        this.id = id;
        this.puntaje_total = puntaje_total;
        this.producto = producto;
        this.usuario = usuario;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setPuntaje_total(double puntaje_total) {
        this.puntaje_total = puntaje_total;
    }

    public double getPuntaje_total() {
        return puntaje_total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
