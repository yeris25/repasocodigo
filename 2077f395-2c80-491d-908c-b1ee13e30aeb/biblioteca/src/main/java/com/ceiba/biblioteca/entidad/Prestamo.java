package com.ceiba.biblioteca.entidad;


import javax.persistence.*; //para importar las anotaciones
import java.time.LocalDate;

@Entity //Para decirle al compilador que esta clase es una entidad
@Table(name ="prestamos") //Para crear la tabla
public class Prestamo { //siempre van en plural los nombres de las tablas

    @Id //Es para especificar que la variable es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para darle el valor
    @Column(name = "id")
    private Integer id; //

    @Column(name = "isbn", length = 10) //crear una columna //length cantidad de letras permitidas
    private String isbn;

    @Column(name = "identificacion_usuario", length = 10)
    private String identificacionUsuario;

    @Column(name = "fecha_maxima_devolucion")
    private LocalDate fechaMaximaDevolucion;

    @Column(name = "tipo_usuario")
    private Integer tipoUsuario;

    public Prestamo() {
    }

    public Prestamo(Integer id, String isbn, String identificacionUsuario, LocalDate fechaMaximaDevolucion, Integer tipoUsuario) {
        this.id = id;
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public LocalDate getFechaMaximaDevolucion() {
        return fechaMaximaDevolucion;
    }

    public void setFechaMaximaDevolucion(LocalDate fechaMaximaDevolucion) {
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Transient //Significa que esta variable no se almacena en la base de datos-para mostrar el mensaje de error en el postman
    private String mensajeError;//

    public String getMensajeError() { //constructor vacio
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
