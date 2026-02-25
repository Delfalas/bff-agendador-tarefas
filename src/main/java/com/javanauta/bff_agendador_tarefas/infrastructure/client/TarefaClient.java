package com.javanauta.bff_agendador_tarefas.infrastructure.client;

import com.javanauta.bff_agendador_tarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${tarefas.url}")
public interface TarefaClient {

    // 🔹 Cadastro unitário
    @PostMapping
    TarefasDTORequest salvarTarefas(@RequestHeader("Authorization") String token, @RequestBody TarefasDTORequest tarefasDTO);


    // 🔹 Cadastro em lote
    @PostMapping("/lote")
    List<TarefasDTORequest> salvarTarefasEmLote(@RequestHeader("Authorization") String token,
                                                 @RequestBody List<TarefasDTORequest> tarefasDTOList);


    //Método GET para periodo de data (eventos)
    @GetMapping("/eventos")
    List<TarefasDTOResponse> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token);


    //Método GET por email de usuario
    @GetMapping
    List<TarefasDTOResponse> buscaListaDeTarefasPorEmail(@RequestHeader("Authorization") String token);


    //método para deletar DELETE tarefa por id
    @DeleteMapping
    void deletarTarefaPorId(@RequestParam("id") String id,
                            @RequestHeader("Authorization") String token);


    //método para alterar status PATCH da tarefa por id
    @PatchMapping
    TarefasDTOResponse alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                               @RequestParam("id") String id,
                                               @RequestHeader("Authorization") String token);


    //método de update de tarefas PUT por id
    @PutMapping
    TarefasDTORequest updateTarefas(@RequestBody TarefasDTORequest tarefasDTO,
                                     @RequestParam("id") String id,
                                     @RequestHeader("Authorization") String token);

}