package proyecto_pd_dh.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "recomendaciones")
public class Recomendacion {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="puntaje")
    private double puntaje_total;

    @ManyToOne
    private Producto producto;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
