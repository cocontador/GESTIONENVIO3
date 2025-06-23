package com.Envios.gestionEnvio.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Envios.gestionEnvio.Model.Envio;
import com.Envios.gestionEnvio.Repository.EnvioRepository;

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioServiceImp envioService;

    private Envio envio;
    private Envio envio2;

    @BeforeEach
    void setUp() {
        envio = new Envio();
        envio.setId(1L);
        envio.setCliente("Cliente 1");
        envio.setDireccion("Dirección 123");
        envio.setDescripcion("Paquete frágil");
        envio.setCodigoSeguimiento("ABC123");
        envio.setFechaEnvio(LocalDateTime.now());

        envio2 = new Envio();
        envio2.setId(2L);
        envio2.setCliente("Cliente 2");
        envio2.setDireccion("Dirección 456");
    }

    @Test
    void testCrearEnvio() {
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);
        
        Envio resultado = envioService.crearEnvio(envio);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Cliente 1", resultado.getCliente());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testObtenerTodos() {
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio, envio2));
        
        List<Envio> envios = envioService.obtenerTodos();
        
        assertEquals(2, envios.size());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorIdExistente() {
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        
        Envio resultado = envioService.obtenerPorId(1L);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testObtenerPorIdNoExistente() {
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());
        
        Envio resultado = envioService.obtenerPorId(99L);
        
        assertNull(resultado);
    }

    @Test
    void testActualizarEnvioExistente() {
        Envio envioActualizado = new Envio();
        envioActualizado.setCliente("Cliente Actualizado");
        envioActualizado.setDireccion("Nueva Dirección");
        
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);
        
        Envio resultado = envioService.actualizarEnvio(1L, envioActualizado);
        
        assertNotNull(resultado);
        assertEquals("Cliente Actualizado", resultado.getCliente());
        assertEquals("Nueva Dirección", resultado.getDireccion());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testActualizarEnvioNoExistente() {
        Envio envioActualizado = new Envio();
        envioActualizado.setCliente("Cliente Actualizado");
        
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());
        
        Envio resultado = envioService.actualizarEnvio(99L, envioActualizado);
        
        assertNull(resultado);
        verify(envioRepository, never()).save(any());
    }

    @Test
    void testEliminarEnvio() {
        doNothing().when(envioRepository).deleteById(1L);
        
        envioService.eliminarEnvio(1L);
        
        verify(envioRepository, times(1)).deleteById(1L);
    }
}