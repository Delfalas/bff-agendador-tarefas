package com.javanauta.bff_agendador_tarefas.controller;


import com.javanauta.bff_agendador_tarefas.business.UsuarioService;
import com.javanauta.bff_agendador_tarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.UsuarioDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.EnderecoDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TelefoneDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.dto.out.UsuarioDTOResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Salva usuário", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "400", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<UsuarioDTOResponse> salvarUsuario(@RequestBody UsuarioDTORequest usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public String login(@RequestBody LoginRequestDTO usuarioDTO){
        return usuarioService.loginUsuario(usuarioDTO);
    }

    @GetMapping
    @Operation(summary = "Buscar dados de usuário por email", description = "Buscar dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<UsuarioDTOResponse> buscaUsuarioPorEmail(@RequestParam("email") String email,
                                                                   @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Delete de usuário por Id", description = "Deletar usuário")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email,
                                                      @RequestHeader("Authorization") String token){
        usuarioService.deletarUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }

    //Atualiza os dados de usuario usando o Header do token do usuario(email)
    @PutMapping
    @Operation(summary = "Atualizar dados de usuário", description = "Atualização do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<UsuarioDTOResponse> atualizaDadosUsuario(@RequestBody UsuarioDTORequest usuarioDTO,
                                                                   @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));
    }

    //Atualiza os dados de Endereco usando o Header do token do usuario(email)
    @PutMapping("/endereco")
    @Operation(summary = "Atualizar dados de endereço", description = "Atualizar dados de endereço")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<EnderecoDTOResponse> atualizaEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                                               @RequestParam("id") Long id,
                                                               @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarEndereco(id, enderecoDTO, token));
    }

    //Atualiza os dados de Telefone usando o Header do token do usuario(email)
    @PutMapping("/telefone")
    @Operation(summary = "Atualizar dados de telefone", description = "Atualizar dados de telefone")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TelefoneDTOResponse> atualizaTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                                                @RequestParam("id") Long id,
                                                                @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarTelefone(id, telefoneDTO, token));
    }

    //Cria e cadastra os dados de Endereco usando o Header do token do usuario(email)----------------------------------
    @PostMapping("/endereco")
    @Operation(summary = "Salva dados de endereço", description = "Salva dados de endereço")
    @ApiResponse(responseCode = "200", description = "Endereço salvo com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<EnderecoDTOResponse> cadastrarEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                                                 @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastrarEndereco(token, enderecoDTO));
    }

    //Cria e cadastra os dados de Telefone usando o Header do token do usuario(email)-----------------------------------
    @PostMapping("/telefone")
    @Operation(summary = "Salva dados de telefone", description = "Salva dados de telefone")
    @ApiResponse(responseCode = "200", description = "Telefone salvo com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TelefoneDTOResponse> cadastrarTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                                                 @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastrarTelefone(token, telefoneDTO));
    }

    //Método para deletar telefone
    @DeleteMapping("/telefone/{id}")
    @Operation(summary = "Delete de telefone por Id", description = "Deletar telefone")
    @ApiResponse(responseCode = "204", description = "Telefone deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido ou não informado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<Void> deletarTelefone(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        usuarioService.deletarTelefone(token, id);
        return ResponseEntity.noContent().build();
    }

    //método para deletar endereco
    @DeleteMapping("/endereco/{id}")
    @Operation(summary = "Delete de endereço por Id", description = "Deletar endereço")
    @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido ou não informado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<Void> deletarEndereco(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        usuarioService.deletarEndereco(token, id);
        return ResponseEntity.noContent().build();
    }

    //CADASTRAR TELEFONE EM LOTE
    @PostMapping("/telefones/lote")
    @Operation(summary = "Cadastrar telefones em lote",
            description = "Realiza o cadastro de múltiplos telefones para o usuário autenticado"
    )
    @ApiResponse(responseCode = "201", description = "Telefones cadastrados com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    @ApiResponse(responseCode = "401", description = "Token inválido ou não informado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<List<TelefoneDTOResponse>> cadastrarTelefones(
            @RequestHeader("Authorization") String token,
            @RequestBody List<TelefoneDTORequest> telefonesDTO) {

        return ResponseEntity.ok(usuarioService.cadastrarTelefones(token, telefonesDTO));
    }

    //CADASTRAR ENDEREÇO EM LOTE
    @PostMapping("/enderecos/lote")
    @Operation(summary = "Cadastrar endereços em lote",
            description = "Realiza o cadastro de múltiplos endereços para o usuário autenticado"
    )
    @ApiResponse(responseCode = "201", description = "Endereços cadastrados com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    @ApiResponse(responseCode = "401", description = "Token inválido ou não informado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<List<EnderecoDTOResponse>> cadastrarEnderecos(
            @RequestHeader("Authorization") String token,
            @RequestBody List<EnderecoDTORequest> enderecosDTO) {

        return ResponseEntity.ok(usuarioService.cadastrarEnderecos(token, enderecosDTO));
    }


}
