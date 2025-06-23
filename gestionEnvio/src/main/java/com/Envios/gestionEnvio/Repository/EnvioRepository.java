package com.Envios.gestionEnvio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Envios.gestionEnvio.Model.Envio;
@Repository
public interface EnvioRepository extends JpaRepository <Envio, Long> {
    
}

