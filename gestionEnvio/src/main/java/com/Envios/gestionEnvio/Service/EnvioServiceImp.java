package com.Envios.gestionEnvio.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Envios.gestionEnvio.Model.Envio;
import com.Envios.gestionEnvio.Repository.EnvioRepository;

@Service
public class EnvioServiceImp implements EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Override
    public Envio crearEnvio (Envio envio){
        return envioRepository.save(envio);
    }

    @Override
    public List<Envio> obtenerTodos() {
        return envioRepository.findAll();
    }

    @Override
    public Envio obtenerPorId(Long id) {
        return envioRepository.findById(id).orElse(null);
    }

    @Override
    public Envio actualizarEnvio(Long id, Envio envioActualizado) {
    Optional<Envio> envioExistente = envioRepository.findById(id);
        if (envioExistente.isPresent()) {
        Envio envio = envioExistente.get();
        envio.setCliente(envioActualizado.getCliente());
        envio.setDireccion(envioActualizado.getDireccion());
        envio.setDescripcion(envioActualizado.getDescripcion());
        envio.setFechaEnvio(envioActualizado.getFechaEnvio());
        envio.setCodigoSeguimiento(envioActualizado.getCodigoSeguimiento());
        return envioRepository.save(envio);
    } else {
        return null;
        }
    }

    @Override
    public void eliminarEnvio(Long id) {
        envioRepository.deleteById(id);
    }
}

