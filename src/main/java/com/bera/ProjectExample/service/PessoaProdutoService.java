package com.bera.ProjectExample.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bera.ProjectExample.dto.PessoaProduto;
import com.bera.ProjectExample.repository.PessoaProdutoRepository;

@Service
public class PessoaProdutoService {

	@Autowired
	private PessoaProdutoRepository repository;

	public List<PessoaProduto> listAll() {
		return repository.findAll();
	}

	public PessoaProduto insert(PessoaProduto pessoaProduto) {
		if (pessoaProduto.getId() != null) {
			Date data = repository.recuperaData(pessoaProduto.getId());
			OffsetDateTime dataCriacao = data.toInstant().atOffset(ZoneOffset.UTC);
			pessoaProduto.setCreatedAt(dataCriacao);
			pessoaProduto.setUpdateAt(OffsetDateTime.now(ZoneId.of("America/Sao_Paulo")));
		}
		return repository.save(pessoaProduto);
	}

	public String delete(Long id) {
		PessoaProduto pessoaProduto = repository.buscaIdPessoaProduto(id);
		if (pessoaProduto != null) {
			repository.deleteById(id);
			return "PessoaProduto deletado com sucesso!";
		} else {
			return "PessoaProduto inexistente!";
		}

	}

	public Optional<PessoaProduto> listOne(Long id) {
		return repository.findById(id);
	}

	public List<PessoaProduto> listPessoaProduto(Long idPessoa) {
		return repository.BuscaPessoaProduto(idPessoa);
	}

}
