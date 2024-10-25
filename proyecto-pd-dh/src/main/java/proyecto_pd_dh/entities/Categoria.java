package proyecto_pd_dh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "imagen", columnDefinition = "MEDIUMTEXT")
    private String imagen;


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();

    public Categoria(){}

    public Categoria(Integer id){}

    public Categoria(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Categoria(Integer id, String titulo, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public Categoria(String titulo) {
        this.titulo = titulo;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
