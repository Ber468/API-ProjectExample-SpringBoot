package com.bera.ProjectExample.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.bera.ProjectExample.Utils.TokenJwt;
import com.bera.ProjectExample.dto.PessoaProduto;
import com.bera.ProjectExample.service.PessoaProdutoService;

@RestController
@RequestMapping(value = "pessoaProduto")
public class PessoaProdutoController {

	@Autowired
	private PessoaProdutoService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "listAll")
	public ResponseEntity<?> listAll(@RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		List<PessoaProduto> listPessoaProduto = service.listAll();
		if (listPessoaProduto == null || listPessoaProduto.isEmpty()) {
			return new ResponseEntity<>("Nenhum registro encontrado", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listPessoaProduto, HttpStatus.OK);
		}
	}

	@PostMapping(value = "insert")
	public ResponseEntity<?> insert(@RequestBody PessoaProduto pessoaProduto, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		PessoaProduto pessoaProdutoAceito = service.insert(pessoaProduto);
		if (pessoaProdutoAceito == null) {
			return new ResponseEntity<>("Não foi possivel realizar a operação!", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(pessoaProdutoAceito, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "delete")
	public ResponseEntity<?> delete(@RequestHeader(value = "id") Long id, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		String deletado = service.delete(id);
		if(deletado.contains("sucesso")) {
			return new ResponseEntity<>(deletado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(deletado, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "listOne")
	public ResponseEntity<?> listOne(@RequestHeader(value = "id") Long id, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		Optional<PessoaProduto> listOnePessoaProduto = service.listOne(id);
		if (listOnePessoaProduto == null || listOnePessoaProduto.isEmpty()) {
			return new ResponseEntity<>("Nnehum registro encontrado", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listOnePessoaProduto, HttpStatus.OK);
		}
	}

	@PutMapping(value = "update")
	public String update(@RequestBody PessoaProduto pessoaProduto, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		return service.listOne(pessoaProduto.getId()).map(pessoaProdutoBase -> {
			modelMapper.map(pessoaProduto, pessoaProdutoBase);
			service.insert(pessoaProdutoBase);
			return "PessoaProduto alterado com sucesso!";
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "PessoaProduto não encontrado"));
	}

	@GetMapping(value = "listPessoaProduto")
	public ResponseEntity<?> listPessoaProduto(@RequestHeader(value = "idPessoa") Long idPessoa, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		List<PessoaProduto> listPessoaProduto = service.listPessoaProduto(idPessoa);
		if (listPessoaProduto == null || listPessoaProduto.isEmpty()) {
			return new ResponseEntity<>("Nenhum registro encontrado!", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listPessoaProduto, HttpStatus.OK);
		}
	}

}
