package proyecto_pd_dh.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import proyecto_pd_dh.entities.Producto;

import java.util.List;

public class UbicacionDTO {


    private Integer id;

    private String ciudad;

    private List<ProductoDTO> productosDTO;

    public UbicacionDTO(){};

    public UbicacionDTO(Integer id, String ciudad, List<ProductoDTO> productosDTO) {
        this.id = id;
        this.ciudad = ciudad;
        this.productosDTO = productosDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<ProductoDTO> getProductosDTO() {
        return productosDTO;
    }

    public void setProductosDTO(List<ProductoDTO> productosDTO) {
        this.productosDTO = productosDTO;
    }
}
