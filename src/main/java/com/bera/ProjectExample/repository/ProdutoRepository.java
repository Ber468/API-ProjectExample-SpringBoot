package com.bera.ProjectExample.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bera.ProjectExample.dto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query (nativeQuery = true, value = "SELECT CreatedAt FROM TB_PRODUTO WHERE Id = :id")
	public Date recuperaData(Long id);
	
	@Query (nativeQuery = true, value = "SELECT * FROM TB_PRODUTO WHERE Id = :id")
	public Produto buscaIdProduto(Long id);

}
