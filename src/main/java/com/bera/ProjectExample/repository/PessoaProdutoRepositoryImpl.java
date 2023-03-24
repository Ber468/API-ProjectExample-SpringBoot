package com.bera.ProjectExample.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class PessoaProdutoRepositoryImpl implements PessoaProdutoRepository {
	
	@PersistenceContext
	private EntityManager em;

}
