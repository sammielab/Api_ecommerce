package proyecto_pd_dh.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "catalogos")
public class Catalogo {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "catalogo_id")
    private Integer id;

    @OneToMany(mappedBy = "catalogo", cascade = CascadeType.ALL)
    private Set<Producto> productos;

    //Constructores
    public Catalogo(){}

    public Catalogo(Integer id, Set<Producto> productos) {
        this.id = id;
        this.productos = productos;
    }

    public Catalogo(Set<Producto> productos) {
        this.productos = productos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
}
