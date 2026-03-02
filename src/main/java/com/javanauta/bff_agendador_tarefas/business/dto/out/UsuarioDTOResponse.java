package com.javanauta.bff_agendador_tarefas.business.dto.out;

import com.javanauta.bff_agendador_tarefas.business.enums.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTOResponse {

    private String nome;
    private String email;
    private String senha;
    private Role role;
    private List<EnderecoDTOResponse> enderecos;
    private List<TelefoneDTOResponse> telefones;
}
