package proyecto_pd_dh.dto;

import jakarta.persistence.*;
import proyecto_pd_dh.entities.*;

import java.util.List;


public class ProductoDTO {

    private Integer id;
    private String titulo;
    private String descripcion;
    private List<Caracteristica> caracteristicas;
    private Double precio;
    private List<RecomendacionDTO> recomendaciones;
    private Categoria categoria;

    public ProductoDTO(){};

    public ProductoDTO(Integer id, String titulo, String descripcion, List<Caracteristica> caracteristicas, Double precio, List<RecomendacionDTO> recomendaciones, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.precio = precio;
        this.recomendaciones = recomendaciones;
        this.categoria = categoria;
    }

    public ProductoDTO(Integer id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
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

    public List<RecomendacionDTO> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<RecomendacionDTO> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
