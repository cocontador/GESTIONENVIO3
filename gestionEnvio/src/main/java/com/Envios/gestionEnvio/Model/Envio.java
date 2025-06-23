package com.Envios.gestionEnvio.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table (name ="envio")

public class Envio {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long id;

    @Column (name = "cliente", nullable=false)
    private String cliente;
    @Column (name = "direccion", nullable=false)
    private String direccion;
    @Column (name = "descripcion")
    private String descripcion;
    @Column (name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();
    @Column(name = "codigo_seguimiento", nullable = false, unique = true)
    private String codigoSeguimiento;
}
