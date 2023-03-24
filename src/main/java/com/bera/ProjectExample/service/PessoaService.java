package com.bera.ProjectExample.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bera.ProjectExample.dto.Pessoa;
import com.bera.ProjectExample.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> listAll() {
		return repository.findAll();
	}

	public Pessoa insert(Pessoa pessoa) {
		if (pessoa.getId() != null) {
			Date data = repository.recuperaData(pessoa.getId());
			OffsetDateTime dataCriacao = data.toInstant().atOffset(ZoneOffset.UTC);
			pessoa.setCreatedAt(dataCriacao);
			pessoa.setUpdateAt(OffsetDateTime.now(ZoneId.of("America/Sao_Paulo")));
		}
		return repository.save(pessoa);
	}

	public String delete(Long id) {
		Pessoa pessoa = repository.buscaIdPessoa(id);
		if (pessoa != null) {
			repository.deleteById(id);
			return "Pessoa deletada com sucesso!";
		} else {
			return "Pessoa inexistente!";
		}
	}

	public Optional<Pessoa> listOne(Long id) {
		return repository.findById(id);
	}
	
	public Pessoa findByEmail(String ds_login) {
		return repository.findByEmail(ds_login);
	}
	
	public Pessoa findByCpf(String ds_login) {
		return repository.findByCpf(ds_login);
	}
	
	public Pessoa enviaEmailSenha(String login) {
		if(login.contains("@")) {
			return repository.findByEmail(login);
		} else if(login.contains("-") && login.contains(".")) {
			return repository.findByCpf(login);
		} else if(login.length() == 11) {
			StringBuilder sb = new StringBuilder(login);
			sb = sb.insert(3, ".");
			sb = sb.insert(7, ".");
			sb = sb.insert(11, "-");
			login = sb.toString();
			return repository.findByCpf(login);
		} else {
			return null;
		}
	}
	
	public void atualizaSenha(String email, String senha) {
		repository.atualizaSenha(email, senha);
	}
	
	public Optional<Pessoa> listByEmail(String email) {
		return repository.listByEmail(email);
	}

}
