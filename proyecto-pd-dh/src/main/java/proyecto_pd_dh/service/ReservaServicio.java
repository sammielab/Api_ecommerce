package proyecto_pd_dh.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.dto.ProductoDTO;
import proyecto_pd_dh.dto.ReservaDTO;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Recomendacion;
import proyecto_pd_dh.entities.Reserva;
import proyecto_pd_dh.repository.IProductoRepository;
import proyecto_pd_dh.repository.IReservaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservaServicio {

    @Autowired
    private IReservaRepository reservaRepository;
    private final IProductoRepository productoRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Reserva save(Reserva reserva) throws Exception {

        Optional<Producto> producto = productoRepository.findById(reserva.getProducto().getId());

        if(producto.isPresent()){

            if(!producto.get().getReservas().isEmpty()){
                List<Reserva> reservasProducto = producto.get().getReservas();

                boolean existeReserva = reservasProducto.stream().anyMatch(res -> {
                    LocalDate checkIn = res.getCheck_in();
                    LocalDate checkOut = res.getCheck_out();
                    return (
                                    (checkIn.isBefore(reserva.getCheck_in()) && checkOut.isAfter(reserva.getCheck_out())) ||
                                    (checkIn.isAfter(reserva.getCheck_in()) && checkOut.isAfter(reserva.getCheck_out())) ||
                                    (checkIn.isAfter(reserva.getCheck_in()) &&  checkOut.isBefore(reserva.getCheck_out())) ||
                                            ((checkIn.isBefore(reserva.getCheck_in()) && checkOut.isAfter(reserva.getCheck_in())) && checkOut.isBefore(reserva.getCheck_out())) ||
                            checkIn.isEqual(reserva.getCheck_in()) || checkOut.isEqual(reserva.getCheck_out()));
                });

                if(existeReserva){
                    throw new Exception("No se puede realizar la reserva en esas fechas");
                }else{
                    return reservaRepository.save(reserva);

                }
            }else{
                return  reservaRepository.save(reserva);
            }

        }else{
            throw new Exception("producto no encontrado");

        }


    }

    public Optional<List<ProductoDTO>> findByDate(LocalDate checkin, LocalDate checkout) throws Exception {
        Optional<List<Integer>> unavaibleProducts = reservaRepository.findUnavaibleProducts(checkin, checkout);

        if(unavaibleProducts.isPresent()){
            Optional<List<Producto>> avaiableProducts = Optional.ofNullable(productoRepository.findProductsNotInIds(unavaibleProducts));


            if(avaiableProducts.isPresent()){
                List<ProductoDTO> productsToReturn = new ArrayList<>();

                for(Producto product : avaiableProducts.get()){
                    ProductoDTO productoDTO = new ProductoDTO();

                    productoDTO.setId(product.getId());
                    productoDTO.setTitulo(product.getTitulo());
                    productoDTO.setCategoria(product.getCategoria());
                    productoDTO.setDescripcion(product.getDescripcion());
                    productoDTO.setCaracteristicas(product.getCaracteristicas());
                    productoDTO.setPrecio(product.getPrecio());
                    productsToReturn.add(productoDTO);


                }
                return Optional.of(productsToReturn);

            } else {
                throw new Exception("No se encuentran productos disponibles");
            }
        }else{
            List<ProductoDTO> allProductsDto = new ArrayList<>();
            List<Producto> allProducst =  productoRepository.findAll();

            for(Producto p :  allProducst){
                ProductoDTO pDto =  convertToDto(p);
                allProductsDto.add(pDto);
            }

            return Optional.of(allProductsDto);
        }
    };

    public Optional<List<ReservaDTO>> findByUsuarioId(Integer id) throws Exception {
      Optional<List<Reserva>> reservas =  reservaRepository.findByUsuarioId(id);

        if(reservas.isPresent()){

            List<ReservaDTO> reservaDTOS = new ArrayList<>();

            for(Reserva reserva : reservas.get()){
                ReservaDTO resDTO = ReservaToDto(reserva);
                resDTO.setId_producto(reserva.getProducto().getId());
                resDTO.setId_usuario(reserva.getUsuario().getId());
                reservaDTOS.add(resDTO);
            }

            return Optional.of(reservaDTOS);

        }else{
            throw new Exception("No se encuentran reservas disponibles");
        }
    }
    public ReservaDTO ReservaToDto(Reserva rsv) {
        return modelMapper.map(rsv, ReservaDTO.class);
    }

    public ProductoDTO convertToDto(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    public Optional<Reserva> findById(Integer id){
        return reservaRepository.findById(id);
    }

    public List<Reserva> findAll(){
        return reservaRepository.findAll();
    }

    public Reserva update(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    public void delete(Integer id){
        reservaRepository.deleteById(id);
    }

}
