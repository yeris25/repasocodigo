package com.ceiba.biblioteca.servicio;


import com.ceiba.biblioteca.entidad.Prestamo;
import com.ceiba.biblioteca.repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PrestamoServicio implements PrestamoServicioInterface {//implements las interfaces implementan y las clases heredan
    @Autowired//Para inyectar dependencias
    protected PrestamoRepositorio prestamoRepositorio; //declara un objeto
    @Override
    public Prestamo crearPrestamo(Prestamo prestamo) throws Exception{ //metodo publico que llama a la entidad prestamo y se llama crear prestamo
        try {//Generar una excepcion(intentar)
            if (prestamo.getTipoUsuario() == 3 && prestamoRepositorio.existsByIdentificacionUsuario(prestamo.getIdentificacionUsuario())) { //traer prestamo
                throw new Exception("El usuario con identificación " + prestamo.getIdentificacionUsuario() + " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
            }
            prestamo.setFechaMaximaDevolucion(calcularFechaMaximaDevolucion(prestamo)); //asignar prestamo
            return prestamoRepositorio.save(prestamo);
        } catch (Exception e) { //Atrapar la excepcion
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)//Solo para leer y no modificar indormacion y verificar si el usuario esta en la lista
    public Prestamo buscarPorId(Integer id) throws Exception{
        try {//intentar
            Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(id);
            if (prestamoOptional.isPresent()) {
                return prestamoOptional.get();
            } else {
                throw new Exception("Usuario no encontrado.");
            }
        } catch (Exception e) {//errores tecnicos
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public LocalDate calcularFechaMaximaDevolucion(Prestamo prestamo) {
        Integer tipoUsuario = prestamo.getTipoUsuario();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaMaximaDevolucion;
        switch (tipoUsuario) {
            case 1: //Depende del numero de tipo de usuario
                fechaMaximaDevolucion = calcularFechaMaximaDevolucionAfiliado(fechaActual);
                return fechaMaximaDevolucion;
            case 2:
                fechaMaximaDevolucion = calcularFechaMaximaDevolucionEmpleado(fechaActual);
                return fechaMaximaDevolucion;
            case 3:
                fechaMaximaDevolucion = calcularFechaMaximaDevolucionInvitado(fechaActual);
                return fechaMaximaDevolucion;
            default:
                throw new IllegalArgumentException("Tipo de usuario no permitido en la biblioteca");
        }
    }

    @Override
    public LocalDate calcularFechaMaximaDevolucionBase(LocalDate fechaActual, Integer diasSegunTipoUsuario) {
        LocalDate fechaMaximaDevolucionBase = fechaActual;
        Integer i = 0;  //Es una variable que va a estar incrementando segun los dias dados segun el tipo de usuario i=iterenancia
        while (i < diasSegunTipoUsuario) {
            fechaMaximaDevolucionBase = fechaMaximaDevolucionBase.plusDays(1);
            if (fechaMaximaDevolucionBase.getDayOfWeek() != DayOfWeek.SATURDAY && fechaMaximaDevolucionBase.getDayOfWeek() != DayOfWeek.SUNDAY){
                i++;
            }
        }


        return fechaMaximaDevolucionBase;
    }

    public LocalDate calcularFechaMaximaDevolucionAfiliado(LocalDate fechaActual) {
        LocalDate fechaMaximaDevolucionAfiliado = fechaActual;
        fechaMaximaDevolucionAfiliado = calcularFechaMaximaDevolucionBase(fechaMaximaDevolucionAfiliado, 10);
        return fechaMaximaDevolucionAfiliado;
    }

    public LocalDate calcularFechaMaximaDevolucionEmpleado(LocalDate fechaActual) {
        LocalDate fechaMaximaDevolucionEmpleado = fechaActual;
        fechaMaximaDevolucionEmpleado = calcularFechaMaximaDevolucionBase(fechaMaximaDevolucionEmpleado, 8);
        return fechaMaximaDevolucionEmpleado;
    }

    public LocalDate calcularFechaMaximaDevolucionInvitado(LocalDate fechaActual) {
        LocalDate fechaMaximaDevolucionInvitado = fechaActual;
        fechaMaximaDevolucionInvitado = calcularFechaMaximaDevolucionBase(fechaMaximaDevolucionInvitado, 7);
        return fechaMaximaDevolucionInvitado;
    }

    public String formatearFecha(LocalDate fechaParaFormatear){ //fecha actual y la modifica asignar nuevo formato
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaParaFormatear.format(formatter); //formatear variable
        return fechaFormateada;
    }
}