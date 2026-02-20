package com.javanauta.bff_agendador_tarefas.business;

import com.javanauta.bff_agendador_tarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.UsuarioDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.EnderecoDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TelefoneDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.dto.out.UsuarioDTOResponse;
import com.javanauta.bff_agendador_tarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient client;

    public UsuarioDTOResponse salvaUsuario(UsuarioDTORequest usuarioDTO) {
        return client.salvarUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginRequestDTO usuarioDTO) {
        return client.login(usuarioDTO);
    }

    public UsuarioDTOResponse buscarUsuarioPorEmail(String email, String token) {
        return client.buscaUsuarioPorEmail(email, token);
    }

    public void deletarUsuarioPorEmail(String email, String token) {
        client.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioDTOResponse atualizaDadosUsuario(String token, UsuarioDTORequest usuarioDTO) {
        return client.atualizaDadosUsuario(usuarioDTO, token);
    }

    //Método para atualizar Endereço
    public EnderecoDTOResponse atualizarEndereco(Long idEndereco, EnderecoDTORequest enderecoDTO, String token) {
        return client.atualizaEndereco(enderecoDTO, idEndereco, token);
    }

    //Método para atualizar Telefone
    public TelefoneDTOResponse atualizarTelefone(Long idTelefone, TelefoneDTORequest telefoneDTO, String token) {
        return client.atualizaTelefone(telefoneDTO, idTelefone, token);
    }

    //Método para cadastrar novo Endereço
    public EnderecoDTOResponse cadastrarEndereco(String token, EnderecoDTORequest enderecoDTO) {
        return client.cadastrarEndereco(enderecoDTO, token);
    }

    //Método para cadastrar novo Telefone
    public TelefoneDTOResponse cadastrarTelefone(String token, TelefoneDTORequest telefoneDTO) {
        return client.cadastrarTelefone(telefoneDTO, token);
    }

    //Método para deletar telefone
    public void deletarTelefone(String token, Long idTelefone) {
        client.deletarTelefone(idTelefone, token);
    }

    //Método para deletar endereco
    public void deletarEndereco(String token, Long idEndereco) {
        client.deletarEndereco(idEndereco, token);
    }

    //CADASTRAR TELEFONE EM LOTE
    public List<TelefoneDTOResponse> cadastrarTelefones(String token, List<TelefoneDTORequest> telefonesDTO) {

        return client.cadastrarTelefones(token, telefonesDTO);
    }

    //CADASTRAR ENDEREÇO EM LOTE
    public List<EnderecoDTOResponse> cadastrarEnderecos(String token, List<EnderecoDTORequest> enderecosDTO) {
        return client.cadastrarEnderecos(token, enderecosDTO);
    }

}
