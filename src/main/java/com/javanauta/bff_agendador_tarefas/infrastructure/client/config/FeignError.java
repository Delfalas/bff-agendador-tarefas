package com.javanauta.bff_agendador_tarefas.infrastructure.client.config;

import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.BusinessException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.ConflictException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignError implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()) {
            case 409:
                return new ConflictException("Registro já existente!");
            case 403:
                return new ResourceNotFoundException("Nenhum registro foi encontrado!");
            case 401:
                return new UnauthorizedException("Autenticacao ou Token invalida!");
            default:
                return new BusinessException("Erro de servidor! ");
        }
    }
}
