package com.ceiba.biblioteca.dto;

public class PrestamoErrorDTO extends PrestamoDTO{

  //Nombre variables
    //Mostrar mensaje error
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
