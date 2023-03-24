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
import com.bera.ProjectExample.dto.Produto;
import com.bera.ProjectExample.service.ProdutoService;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "listAll")
	public ResponseEntity<?> listAll(@RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		List<Produto> listProduto = service.listAll();
		if(listProduto == null || listProduto.isEmpty()) {
			return new ResponseEntity<>("Nenhum produto encontrado :(", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listProduto, HttpStatus.OK);  
		}
	}
	
	@PostMapping(value = "insert")
	public ResponseEntity<?> insert(@RequestBody Produto produto, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		Produto produtoAceito = service.insert(produto);
		if(produtoAceito == null) {
			return new ResponseEntity<>("Não foi possivel realizar a operação!", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(produtoAceito, HttpStatus.OK);
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
		Optional<Produto> listOneProduto = service.listOne(id);
		if(listOneProduto == null || listOneProduto.isEmpty()) {
			return new ResponseEntity<>("Nenhum produto encontrado", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listOneProduto, HttpStatus.OK);
		}
	}
	
	@PutMapping(value = "update")
	public String update(@RequestBody Produto produto, @RequestHeader(value = "token")String token) {
		TokenJwt.validarToken(token);
		return service.listOne(produto.getId()).map(produtoBase -> {
			modelMapper.map(produto, produtoBase);
			service.insert(produtoBase);
			return "Produto alterado com sucesso!";
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado :("));
	}

}
