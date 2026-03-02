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
        System.out.println("STATUS: " + response.status()); System.out.println("URL: " + response.request().url());
        switch (response.status()) {
            case 409:
                return new ConflictException("Registro já existente!");
            case 400:
                return new BusinessException("Erro de validação!");
            case 403:
                return new UnauthorizedException("Acesso negado - Token inválido ou sem permissão!");
            case 401:
                return new UnauthorizedException("Autenticacao ou Token invalida!");
            case 500:
                return new BusinessException("Erro interno no microsserviço!");
            case 404:
                return new ResourceNotFoundException("Recurso não encontrado!");
            default:
                return new BusinessException("Erro de servidor! ");

        }
    }
}
