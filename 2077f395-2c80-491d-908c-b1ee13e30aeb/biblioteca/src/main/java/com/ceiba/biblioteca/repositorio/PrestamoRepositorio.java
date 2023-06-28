package com.ceiba.biblioteca.repositorio;

import com.ceiba.biblioteca.entidad.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer> { //interface es un conjunto de operaciones que deben ser implementada por las clases que la utilizan.
    boolean existsByIdentificacionUsuario(String identificacionUsuario);

} //repositorio hereda de jpa  y llama la entidad prestamo Integer=numero y integer para buscar id
//