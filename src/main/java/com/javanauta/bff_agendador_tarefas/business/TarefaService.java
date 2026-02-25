package com.javanauta.bff_agendador_tarefas.business;


import com.javanauta.bff_agendador_tarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bff_agendador_tarefas.infrastructure.client.TarefaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaClient tarefaClient;

    public TarefasDTORequest gravarTarefas(String token, TarefasDTORequest tarefasDTO){
       return tarefaClient.salvarTarefas(token, tarefasDTO);
    }

    public List<TarefasDTORequest> gravarTarefasEmLote(String token, List<TarefasDTORequest> tarefasDTOList) {
        return tarefaClient.salvarTarefasEmLote(token, tarefasDTOList);
    }

    //método para buscar GET por periodo
    public List<TarefasDTOResponse> buscarTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token){
        return tarefaClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    //método para buscar GET por email de usuario
    public List<TarefasDTOResponse> buscarTarefasPorEmail(String token){
        return tarefaClient.buscaListaDeTarefasPorEmail(token);
    }

    //método para deletar DELETE tarefa por id
    public void deletarTarefaPorId(String id, String token){
       tarefaClient.deletarTarefaPorId(id, token);
    }

    //método para alterar status PATCH da tarefa por id
    public TarefasDTOResponse alterarStatus(StatusNotificacaoEnum status, String id, String token){
        return tarefaClient.alteraStatusNotificacao(status, id, token);
    }

    //método de update de tarefas PUT por id
    public TarefasDTORequest updateTarefas(TarefasDTORequest tarefasDTO, String id, String token){
        return tarefaClient.updateTarefas(tarefasDTO, id, token);
    }

}
