package com.javanauta.bff_agendador_tarefas.infrastructure.client;

import com.javanauta.bff_agendador_tarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.UsuarioDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.EnderecoDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TelefoneDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.dto.out.UsuarioDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/usuario")
    UsuarioDTOResponse buscaUsuarioPorEmail(@RequestParam("email") String email,
                                            @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOResponse salvarUsuario(@RequestBody UsuarioDTORequest usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody LoginRequestDTO usuarioDTO);

    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization") String token);

    //Atualiza os dados de usuario usando o Header do token do usuario(email)
    @PutMapping
    UsuarioDTOResponse atualizaDadosUsuario(@RequestBody UsuarioDTORequest usuarioDTO,
                                            @RequestHeader("Authorization") String token);

    //Atualiza os dados de Endereco usando o Header do token do usuario(email)
    @PutMapping("/endereco")
    EnderecoDTOResponse atualizaEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization") String token);

    //Atualiza os dados de Telefone usando o Header do token do usuario(email)
    @PutMapping("/telefone")
    TelefoneDTOResponse atualizaTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization") String token);

    //Cria e cadastra os dados de Endereco usando o Header do token do usuario(email)----------------------------------
    @PostMapping("/endereco")
    EnderecoDTOResponse cadastrarEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                          @RequestHeader("Authorization") String token);

    //Cria e cadastra os dados de Telefone usando o Header do token do usuario(email)-----------------------------------
    @PostMapping("/telefone")
    TelefoneDTOResponse cadastrarTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                          @RequestHeader("Authorization") String token);

    //Método para deletar telefone
    @DeleteMapping("/telefone/{id}")
    void deletarTelefone(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token);

    //método para deletar endereco
    @DeleteMapping("/endereco/{id}")
    void deletarEndereco(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token);

    //CADASTRAR TELEFONE EM LOTE
    @PostMapping("/telefones/lote")
    List<TelefoneDTOResponse> cadastrarTelefones(
            @RequestHeader("Authorization") String token,
            @RequestBody List<TelefoneDTORequest> telefonesDTO);

    //CADASTRAR ENDEREÇO EM LOTE
    @PostMapping("/enderecos/lote")
    List<EnderecoDTOResponse> cadastrarEnderecos(
            @RequestHeader("Authorization") String token,
            @RequestBody List<EnderecoDTORequest> enderecosDTO);
}

