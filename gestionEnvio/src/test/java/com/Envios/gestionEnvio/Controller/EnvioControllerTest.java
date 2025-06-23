package com.Envios.gestionEnvio.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.Envios.gestionEnvio.Assemblers.EnvioModelAssembler;
import com.Envios.gestionEnvio.Model.Envio;
import com.Envios.gestionEnvio.Service.EnvioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class EnvioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnvioService envioService;
    @Mock
    private EnvioModelAssembler assembler;
    @InjectMocks
    private EnvioController envioController;

    private Envio envio;
    private List<Envio> envios;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(envioController).build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        envio = new Envio();
        envio.setId(1L);
        envio.setCliente("Cliente Test");
        envio.setDireccion("Dirección Test");
        envio.setCodigoSeguimiento("TEST123");
        envio.setFechaEnvio(LocalDateTime.now());

        Envio envio2 = new Envio();
        envio2.setId(2L);
        envio2.setCliente("Cliente Test 2");
        envio2.setDireccion("Dirección 2");
        envio2.setCodigoSeguimiento("TEST456");
        envio2.setFechaEnvio(LocalDateTime.now());

        envios = Arrays.asList(envio, envio2);
    }

    @Test
    void testCrearEnvio() throws Exception {
        Envio nuevoEnvio = new Envio();
        nuevoEnvio.setCliente("Cliente Test");
        nuevoEnvio.setDireccion("Dirección Test");
        nuevoEnvio.setCodigoSeguimiento("TEST123");
        nuevoEnvio.setFechaEnvio(LocalDateTime.now());

        when(envioService.crearEnvio(any(Envio.class))).thenReturn(envio);

        mockMvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoEnvio)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.cliente").value("Cliente Test"));
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(envioService.obtenerTodos()).thenReturn(envios);

        mockMvc.perform(get("/api/envios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(envioService.obtenerPorId(1L)).thenReturn(envio);

        when(assembler.toModel(envio)).thenReturn(EntityModel.of(envio));
        mockMvc.perform(get("/api/envios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.cliente").value("Cliente Test"));
    }

    @Test
    void testActualizarEnvio() throws Exception {
        Envio actualizadoEnvio = new Envio();
        actualizadoEnvio.setCliente("Cliente Actualizado");
        actualizadoEnvio.setDireccion("Nueva Dirección");
        actualizadoEnvio.setCodigoSeguimiento("NEW123");
        actualizadoEnvio.setFechaEnvio(LocalDateTime.now());

        when(envioService.actualizarEnvio(eq(1L), any(Envio.class))).thenReturn(envio);

        mockMvc.perform(put("/api/envios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizadoEnvio)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.cliente").value("Cliente Test")); // verificamos el mockeado
    }

    @Test
    void testEliminarEnvio() throws Exception {
        doNothing().when(envioService).eliminarEnvio(1L);

        mockMvc.perform(delete("/api/envios/1"))
            .andExpect(status().isNoContent());

        verify(envioService, times(1)).eliminarEnvio(1L);
    }
}