package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.repositorys.ProductoraCriteriaRepository;
import com.viewnext.practica4.repositorys.ProductoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoraService {
    private final ProductoraRepository productoraRepository;
    private final ProductoraCriteriaRepository productoraCriteriaRepository;

    public ProductoraService(ProductoraRepository productoraRepository,
            ProductoraCriteriaRepository productoraCriteriaRepository) {
        this.productoraRepository = productoraRepository;
        this.productoraCriteriaRepository = productoraCriteriaRepository;
    }

    // -------------------- Métodos con JPA Repository --------------------

    public List<Productora> obtenerProductoras() {
        return productoraRepository.findAll();
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

    public Productora actualizarProductora(int idProductora, Productora productoraActualizada) {
        return productoraRepository.findByIdProductora(idProductora).map(productoraAntigua -> {
            productoraAntigua.setNombre(productoraActualizada.getNombre());
            productoraAntigua.setAnoFundacion(productoraActualizada.getAnoFundacion());
            return productoraRepository.save(productoraAntigua);
        }).orElseThrow(() -> new RuntimeException("Productora no encontrada con ID: " + idProductora));
    }

    // -------------------- Métodos con Criteria API --------------------

    public List<Productora> obtenerProductorasCriteria() {
        return productoraCriteriaRepository.listarProductoras();
    }

    public Productora obtenerProductoraPorIdCriteria(int idProductora) {
        return productoraCriteriaRepository.buscarProductora(idProductora);
    }

    @Transactional
    public void insertarProductoraCriteria(Productora productora) {
        productoraCriteriaRepository.insertarProductora(productora);
    }

    @Transactional
    public void actualizarProductoraCriteria(int idProductora, Productora productoraActualizada) {
        productoraCriteriaRepository.actualizarProductora(idProductora, productoraActualizada);
    }

    @Transactional
    public void eliminarProductoraCriteria(int idProductora) {
        productoraCriteriaRepository.borrarProductoraPorId(idProductora);
    }
}
