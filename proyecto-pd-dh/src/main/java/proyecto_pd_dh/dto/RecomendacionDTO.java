package proyecto_pd_dh.dto;

import jakarta.persistence.*;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;

import java.util.Date;

public class RecomendacionDTO {

    private Integer id;

    private double puntaje_total;

    private Date fecha_publicacion;

    private String descripcion;

    private ProductoDTO producto;

    private UsuarioDTO usuario;

    public RecomendacionDTO(){};

    public RecomendacionDTO(Integer id) {
        this.id = id;
    }

    public RecomendacionDTO(Integer id, double puntaje_total, Date fecha_publicacion, String descripcion) {
        this.id = id;
        this.puntaje_total = puntaje_total;
        this.fecha_publicacion = fecha_publicacion;
        this.descripcion = descripcion;
    }

    public RecomendacionDTO(Integer id, double puntaje_total, Date fecha_publicacion, String descripcion, ProductoDTO producto, UsuarioDTO usuario) {
        this.id = id;
        this.puntaje_total = puntaje_total;
        this.fecha_publicacion = fecha_publicacion;
        this.descripcion = descripcion;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPuntaje_total() {
        return puntaje_total;
    }

    public void setPuntaje_total(double puntaje_total) {
        this.puntaje_total = puntaje_total;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
