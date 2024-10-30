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
    private Integer categoria;
    private List<IMGDTO> imgdtos;
    private List<String> politicas;

    public ProductoDTO(){};

    public ProductoDTO(Integer id) {
        this.id = id;
    }

    public ProductoDTO(Integer id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public ProductoDTO(Integer id, String titulo, String descripcion, List<Caracteristica> caracteristicas, Double precio, List<RecomendacionDTO> recomendaciones, Integer categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.precio = precio;
        this.recomendaciones = recomendaciones;
        this.categoria = categoria;
    }

    public ProductoDTO(Integer id, String titulo, String descripcion, List<Caracteristica> caracteristicas, Double precio, List<RecomendacionDTO> recomendaciones, Integer categoria, List<IMGDTO> imgdtos, List<String> politicas) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.precio = precio;
        this.recomendaciones = recomendaciones;
        this.categoria = categoria;
        this.imgdtos = imgdtos;
        this.politicas = politicas;
    }

    public ProductoDTO(Integer id, String titulo, String descripcion, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
    }


    public List<String> getPoliticas() {
        return politicas;
    }

    public void setPoliticas(List<String> politicas) {
        this.politicas = politicas;
    }

    public List<IMGDTO> getImgdtos() {
        return imgdtos;
    }

    public void setImgdtos(List<IMGDTO> imgdtos) {
        this.imgdtos = imgdtos;
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

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }
}
