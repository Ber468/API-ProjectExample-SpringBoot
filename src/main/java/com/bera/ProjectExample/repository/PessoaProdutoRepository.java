package com.bera.ProjectExample.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bera.ProjectExample.dto.PessoaProduto;

public interface PessoaProdutoRepository extends JpaRepository<PessoaProduto, Long> {
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PESSOA_PRODUTO WHERE IdPessoa = :idPessoa")
	public List<PessoaProduto> BuscaPessoaProduto(Long idPessoa);
	
	@Query (nativeQuery = true, value = "SELECT CreatedAt FROM TB_PESSOA_PRODUTO WHERE Id = :id")
	public Date recuperaData(Long id);
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PESSOA_PRODUTO WHERE Id = :id")
	public PessoaProduto buscaIdPessoaProduto(Long id);

}
