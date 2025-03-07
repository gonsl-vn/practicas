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
}
