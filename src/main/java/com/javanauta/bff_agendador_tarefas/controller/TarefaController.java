package com.javanauta.bff_agendador_tarefas.controller;

import com.javanauta.bff_agendador_tarefas.business.TarefaService;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bff_agendador_tarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefaController {

    private final TarefaService tarefaService;

    // 🔹 Cadastro unitário
    @PostMapping
    @Operation(summary = "Salva tarefas de usuário", description = "Cria e salva uma nova tarefa de usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTORequest> salvarTarefas(
            @RequestHeader(name = "Authorization", required = false) String token,
            @RequestBody TarefasDTORequest tarefasDTO) {

        return ResponseEntity.ok(tarefaService.gravarTarefas(token, tarefasDTO));
    }

    // 🔹 Cadastro em lote
    @PostMapping("/lote")
    @Operation(summary = "Cadastro de tarefas em lote",
            description = "Cria e salva múltiplas tarefas para o usuário autenticado"
    )
    @ApiResponse(responseCode = "200", description = "Tarefas cadastradas com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<TarefasDTORequest>> salvarTarefasEmLote(
            @RequestHeader(name = "Authorization", required = false) String token,
            @RequestBody List<TarefasDTORequest> tarefasDTOList) {

        return ResponseEntity.ok(
                tarefaService.gravarTarefasEmLote(token, tarefasDTOList)
        );
    }

    //Método GET para periodo de data (eventos)
    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por periodo", description = "Busca tarefas cadastradas por periodo")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token){

        return ResponseEntity.ok(tarefaService.buscarTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    //Método GET por email de usuario
    @GetMapping
    @Operation(summary = "Busca lista de tarefas por usuario", description = "Busca tarefas por usuario")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Email não encontrado! ")
    @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefasPorEmail(
            @RequestHeader(name = "Authorization", required = false) String token){

        return ResponseEntity.ok(tarefaService.buscarTarefasPorEmail(token));
    }

    //método para deletar DELETE tarefa por id
    @DeleteMapping
    @Operation(summary = "Deleta tarefas por Id", description = "Deleta tarefas por id")
    @ApiResponse(responseCode = "200", description = "Tarefas deletadas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada! ")
    @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    public ResponseEntity<Void> deletarTarefaPorId(@RequestParam("id") String id, @RequestHeader(name = "Authorization", required = false) String token){
        tarefaService.deletarTarefaPorId(id, token);
        return ResponseEntity.ok().build();
    }

    //método para alterar status PATCH da tarefa por id
    @PatchMapping
    @Operation(summary = "Altera status da tarefa por Id", description = "Altera status da tarefa cadastrada")
    @ApiResponse(responseCode = "200", description = "Status alterado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada! ")
    @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    public ResponseEntity<TarefasDTOResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                      @RequestParam("id") String id,
                                                                      @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.alterarStatus(status, id, token));
    }

    //método de update de tarefas PUT por id
    @PutMapping
    @Operation(summary = "Atualiza dados da tarefa por id", description = "Atualiza dados da tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada! ")
    @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    public ResponseEntity<TarefasDTORequest> updateTarefas(@RequestBody TarefasDTORequest tarefasDTO,
                                                            @RequestParam("id") String id,
                                                            @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.updateTarefas(tarefasDTO, id, token));
    }

}
