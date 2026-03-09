package com.javanauta.bff_agendador_tarefas.business.dto.in;

import com.javanauta.bff_agendador_tarefas.business.enums.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTORequest {

    private String nome;
    private String email;
    private String senha;
    private Role role;
    private List<EnderecoDTORequest> enderecos;
    private List<TelefoneDTORequest> telefones;
}
