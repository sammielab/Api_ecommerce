package proyecto_pd_dh.entities;

import jakarta.persistence.*;

@Entity
@Table(name="reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    private Producto producto;

    @Column(name = "check_in")
    private String check_in;

    @Column(name="check_out")
    private String check_out;

    @Column(name = "datos_registro")
    private String datos_registro;

    public Reserva(){}

    public Reserva(Integer id, Producto producto, String check_in, String check_out, String datos_registro) {
        this.id = id;
        this.producto = producto;
        this.check_in = check_in;
        this.check_out = check_out;
        this.datos_registro = datos_registro;
    }

    public Reserva(Producto producto, String check_in, String check_out, String datos_registro) {
        this.producto = producto;
        this.check_in = check_in;
        this.check_out = check_out;
        this.datos_registro = datos_registro;
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

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getDatos_registro() {
        return datos_registro;
    }

    public void setDatos_registro(String datos_registro) {
        this.datos_registro = datos_registro;
    }
}
