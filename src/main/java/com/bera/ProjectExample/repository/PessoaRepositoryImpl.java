package com.bera.ProjectExample.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class PessoaRepositoryImpl implements PessoaRepository {
	
	@PersistenceContext
	private EntityManager em;

}
