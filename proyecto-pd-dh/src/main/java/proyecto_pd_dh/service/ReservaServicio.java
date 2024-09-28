package proyecto_pd_dh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.entities.Producto;
import proyecto_pd_dh.entities.Reserva;
import proyecto_pd_dh.repository.IProductoRepository;
import proyecto_pd_dh.repository.IReservaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServicio {

    @Autowired
    private IReservaRepository reservaRepository;

    public Reserva save(Reserva reserva){
        return reservaRepository.save(reserva);
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
