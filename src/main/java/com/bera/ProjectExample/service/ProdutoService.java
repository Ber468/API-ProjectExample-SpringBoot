package com.bera.ProjectExample.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bera.ProjectExample.dto.Produto;
import com.bera.ProjectExample.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public List<Produto> listAll() {
		return repository.findAll();
	}

	public Produto insert(Produto produto) {
		if (produto.getId() != null) {
			Date data = repository.recuperaData(produto.getId());
			OffsetDateTime dataCriacao = data.toInstant().atOffset(ZoneOffset.UTC);
			produto.setCreatedAt(dataCriacao);
			produto.setUpdateAt(OffsetDateTime.now(ZoneId.of("America/Sao_Paulo")));
		}
		return repository.save(produto);
	}

	public String delete(Long id) {
		Produto produto = repository.buscaIdProduto(id);
		if (produto != null) {
			repository.deleteById(id);
			return "Produto deletado com sucesso!";
		} else {
			return "Produto inexistente!";
		}

	}

	public Optional<Produto> listOne(Long id) {
		return repository.findById(id);
	}

}
