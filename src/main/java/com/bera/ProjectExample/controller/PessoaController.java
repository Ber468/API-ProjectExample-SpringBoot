package com.bera.ProjectExample.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bera.ProjectExample.Utils.EmailSender;
import com.bera.ProjectExample.Utils.TokenJwt;
import com.bera.ProjectExample.dto.ErrorReturn;
import com.bera.ProjectExample.dto.Pessoa;
import com.bera.ProjectExample.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "listAll")
	public ResponseEntity<?> listAll(@RequestHeader(value = "token") String token) {
		TokenJwt.validarToken(token);
		List<Pessoa> listPessoa = service.listAll();
		if (listPessoa == null || listPessoa.isEmpty()) {
			return new ResponseEntity<>("Nenhum produto encontrado :(", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listPessoa, HttpStatus.OK);
		}
	}

	@PostMapping(value = "insert")
	public ResponseEntity<?> insert(@RequestBody Pessoa pessoa) {
		Pessoa pessoaAceita = service.insert(pessoa);
		if (pessoaAceita == null) {
			return new ResponseEntity<>("Não foi possivel realizar a operação!", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(pessoaAceita, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "delete")
	public ResponseEntity<?> delete(@RequestHeader(value = "id") Long id, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		String deletado = service.delete(id);
		if (deletado.contains("sucesso")) {
			return new ResponseEntity<>(deletado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(deletado, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "listOne")
	public ResponseEntity<?> listOne(@RequestHeader(value = "id") Long id, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		Optional<Pessoa> listOnePessoa = service.listOne(id);
		if (listOnePessoa == null || listOnePessoa.isEmpty()) {
			return new ResponseEntity<>("Nenhuma pessoa encontrada", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listOnePessoa, HttpStatus.OK);
		}
	}

	@PutMapping(value = "update")
	public String update(@RequestBody Pessoa pessoa, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		return service.listOne(pessoa.getId()).map(pessoaBase -> {
			modelMapper.map(pessoa, pessoaBase);
			service.insert(pessoaBase);
			return "Pessoa alterada com sucesso!";
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada"));
	}

	@GetMapping(value = "login")
	public ResponseEntity<?> login(@RequestHeader(value = "ds_login") String ds_login,
			@RequestHeader(value = "senha") String senha) {
		Pessoa pessoa;
		ErrorReturn errorReturn = new ErrorReturn();
		if (ds_login != null && !ds_login.isEmpty() && senha != null && !senha.isEmpty()) {
			pessoa = service.findByEmail(ds_login);
			if (pessoa == null) {
				pessoa = service.findByCpf(ds_login);
				if (pessoa == null) {
					StringBuilder sb = new StringBuilder(ds_login);
					sb = sb.insert(3, ".");
					sb = sb.insert(7, ".");
					sb = sb.insert(11, "-");
					ds_login = sb.toString();
					pessoa = service.findByCpf(ds_login);
					if (pessoa == null) {
						errorReturn.setCodigoRetorno(400L);
						errorReturn.setMensagem("Não foi possivel gerar o token!");
						return new ResponseEntity<>(errorReturn, HttpStatus.BAD_REQUEST);
					}
				}
			}
			if(pessoa.getId() != null) {
				if(pessoa.getSenha().equals(senha)) {
					pessoa.setToken(TokenJwt.processarTokenJWT(ds_login));
					errorReturn.setCodigoRetorno(200L);
					errorReturn.setMensagem(pessoa.getToken());
					return new ResponseEntity<>(errorReturn, HttpStatus.OK);
				} else {
					errorReturn.setCodigoRetorno(400L);
					errorReturn.setMensagem("Não foi possivel gerar o token!");
					return new ResponseEntity<>(errorReturn, HttpStatus.BAD_REQUEST);
				}
			} else {
				errorReturn.setCodigoRetorno(400L);
				errorReturn.setMensagem("Não foi possivel gerar o token!");
				return new ResponseEntity<>(errorReturn, HttpStatus.BAD_REQUEST);
			}
		} else {
			errorReturn.setCodigoRetorno(400L);
			errorReturn.setMensagem("Não foi possivel gerar o token!");
			return new ResponseEntity<>(errorReturn, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "emailRecuperaSenha")
	public ResponseEntity<?> emailRecuperaSenha(@RequestHeader(value = "login")String login) {
		Pessoa pessoa = service.enviaEmailSenha(login);
		if(pessoa != null) {
			String novaSenha = RandomStringUtils.randomAlphanumeric(10);
			service.atualizaSenha(pessoa.getEmail(), novaSenha);
			EmailSender emailSender = new EmailSender();
			emailSender.enviarRecuperarSenha(pessoa.getEmail(), pessoa.getNome(), novaSenha);
			return new ResponseEntity<>("Email de recuperar senha enviado!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Email não localizado para recuperar senha", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "atualizaSenha")
	public String atualizaSenha(@RequestBody Pessoa pessoa) {
		return service.listByEmail(pessoa.getEmail()).map(pessoaBase -> {
			modelMapper.map(pessoa, pessoaBase);
			service.insert(pessoaBase);
			return "Senha atualizada com sucesso!";
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não encontrado")); 
	}

}
