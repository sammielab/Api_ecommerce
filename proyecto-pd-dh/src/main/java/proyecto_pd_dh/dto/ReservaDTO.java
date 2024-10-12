package proyecto_pd_dh.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Usuario;

import java.time.LocalDate;

public class ReservaDTO {

    private Integer id;

    private Integer id_producto;

    private Integer id_usuario;

    private LocalDate check_in;

    private LocalDate check_out;

    private LocalDate fecha_reserva;

    private String estado;

    public ReservaDTO(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public LocalDate getCheck_in() {
        return check_in;
    }

    public void setCheck_in(LocalDate check_in) {
        this.check_in = check_in;
    }

    public LocalDate getCheck_out() {
        return check_out;
    }

    public void setCheck_out(LocalDate check_out) {
        this.check_out = check_out;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
