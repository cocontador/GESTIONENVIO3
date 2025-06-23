package com.Envios.gestionEnvio.Assemblers;

import com.Envios.gestionEnvio.Controller.EnvioController;
import com.Envios.gestionEnvio.Model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Ensamblador HATEOAS para la entidad Envio.
 * Transforma un objeto Envio en un EntityModel que incluye enlaces navegables (hypermedia).
 * Esto permite que la API sea más autoexplicativa y orientada a acciones posibles sobre los recursos.
 */
@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    /**
     * Transforma un objeto Envio en un EntityModel con enlaces HATEOAS.
     *
     * @param envio el objeto Envio que se va a transformar
     * @return un EntityModel con los datos del envío y enlaces útiles
     */
    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
            // Enlace al recurso actual (GET /api/envios/{id})
            linkTo(methodOn(EnvioController.class).obtenerPorId(envio.getId())).withSelfRel(),

            // Enlace a la lista completa de envíos (GET /api/envios)
            linkTo(methodOn(EnvioController.class).obtenerTodos()).withRel("envios"),

            // Enlace para eliminar este envío (DELETE /api/envios/{id})
            linkTo (methodOn(EnvioController.class).eliminarEnvio(envio.getId())).withRel("eliminar"),

            // Enlace para actualizar este envío (PUT /api/envios/{id})
            linkTo(methodOn(EnvioController.class).actualizarEnvio(envio.getId(), envio)).withRel("actualizar")
        );
    }
}