package com.bera.ProjectExample.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bera.ProjectExample.dto.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	@Query (nativeQuery = true, value = "SELECT CreatedAt FROM TB_PESSOA WHERE Id = :id")
	public Date recuperaData(Long id);
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PESSOA WHERE Id = :id")
	public Pessoa buscaIdPessoa(Long id);
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PESSOA WHERE email = :email")
	public Pessoa findByEmail(String email);
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PESSOA WHERE cpf = :cpf")
	public Pessoa findByCpf(String cpf);
	
	@Query (nativeQuery = true, value = "UPDATE TB_PESSOA SET senha = :senha WHERE email = :email")
	@Modifying
	@Transactional(readOnly = false)
	public void atualizaSenha(String email, String senha);
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PESSOA WHERE email = :email")
	public Optional<Pessoa> listByEmail(String email);
}
