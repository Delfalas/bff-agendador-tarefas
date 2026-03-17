package com.javanauta.bff_agendador_tarefas.infrastructure.client.config;

import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.BusinessException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.ConflictException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.IllegalArgumentException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.bff_agendador_tarefas.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        String mensagemErro = mensagemErro(response);

        System.out.println("STATUS: " + response.status()); System.out.println("URL: " + response.request().url());

        switch (response.status()) {
            case 409:
                return new ConflictException("Erro: " + mensagemErro);
            case 400:
                return new IllegalArgumentException("Erro: " + mensagemErro);
            case 403, 404:
                return new ResourceNotFoundException("Erro: " + mensagemErro);
            case 401:
                return new UnauthorizedException("Erro: " + mensagemErro);
            default:
                return new BusinessException("Erro: " + mensagemErro);

        }
    }

    private String mensagemErro(Response response) {
        try {
            if(Objects.isNull(response.body())) {
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
