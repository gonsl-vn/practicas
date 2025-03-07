package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.repositorys.ProductoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoraService {
    private final ProductoraRepository productoraRepository;

    public ProductoraService(ProductoraRepository productoraRepository) {
        this.productoraRepository = productoraRepository;
    }

    public List<Productora> obtenerProductoras() {
        return productoraRepository.findAll();
    }

    public Optional<Productora> obtenerProductoraPorNombre(String nombre) {
        return productoraRepository.findByNombre(nombre);
    }

    public Optional<Productora> obtenerProductoraPorId(int idProductora) {
        return productoraRepository.findByIdProductora(idProductora);
    }

    public void insertarProductora(Productora productora) {
        productoraRepository.save(productora);
    }

    public void eliminarProductora(int idProductora) {
        productoraRepository.delete(productoraRepository.findByIdProductora(idProductora).get());
    }

    public Productora actualizarProductora(int idProductora, Productora productoraActualizado) {
        return productoraRepository.findByIdProductora(idProductora).map(productoraAntiguo -> {
            productoraAntiguo.setNombre(productoraActualizado.getNombre());
            productoraAntiguo.setAnoFundacion(productoraActualizado.getAnoFundacion());
            return productoraRepository.save(productoraAntiguo); // Se guarda en la BD
        }).orElseThrow(() -> new RuntimeException("Productora no encontrado con ID: " + idProductora));
    }
}
