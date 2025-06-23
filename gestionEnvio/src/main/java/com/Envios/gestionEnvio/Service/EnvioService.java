package com.Envios.gestionEnvio.Service;

import java.util.List;

import com.Envios.gestionEnvio.Model.Envio;

public interface EnvioService {
    Envio crearEnvio(Envio envio);

    List<Envio> obtenerTodos();

    Envio obtenerPorId(Long id);

    Envio actualizarEnvio(Long id, Envio envio);

    void eliminarEnvio(Long id);

}
