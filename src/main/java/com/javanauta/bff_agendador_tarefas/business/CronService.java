package com.javanauta.bff_agendador_tarefas.business;

import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {

    private final TarefaService tarefaService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    //Buscar tarefas na próxima hora com intervalo de 5 minutos
    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(converterParaRequestDTO());

        log.info("Iniciada a busca de tarefas ");
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
        LocalDateTime horaFuturaMaisCinco = LocalDateTime.now().plusHours(1).plusMinutes(5);

        List<TarefasDTOResponse> listaTarefas = tarefaService.buscarTarefasAgendadasPorPeriodo(
                horaFutura, horaFuturaMaisCinco, token);
        log.info("Tarefas encontradas: {}", listaTarefas);

        listaTarefas.forEach(tarefa -> {
            emailService.enviarEmail(tarefa);
            log.info("Email enviado para o usuário " + tarefa.getEmailUsuario());
            tarefaService.alterarStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token);
        });
        log.info("Finalizada a busca e notificação de tarefa");
    }

    //fazer login para agendar no Cron
    public String login(LoginRequestDTO dto){
        return usuarioService.loginUsuario(dto);
    }

    //credenciais de login para DTO
    public LoginRequestDTO converterParaRequestDTO(){
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
