package com.example.models.models.modelsDB;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "personaspasaportes")
public class PersonasPasaportes {
    private int id;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private LocalDate fechaExpedicionDocumento;
    private String email;
    private String numeroCelular;
    private String link;
    private Boolean activo;
    private LocalDate fechaUltimaModificacion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nombres")
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Column(name = "apellidos")
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Column(name = "tipoDocumento")
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    @Column(name = "numeroDocumento")
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Column(name = "fechaNacimiento")
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Column(name = "fechaExpedicionDocumento")
    public LocalDate getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    public void setFechaExpedicionDocumento(LocalDate fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String correo) {
        this.email = correo;
    }

    @Column(name = "numeroCelular")
    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }
    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "indicadorHabilitado")
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean indicadorHabilitado) {
        this.activo = indicadorHabilitado;
    }

    @Column(name = "fechaUltimaModificacion")
    public LocalDate getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(LocalDate fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }
}
