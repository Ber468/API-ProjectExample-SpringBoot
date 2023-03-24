package com.bera.ProjectExample.dto;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name="TB_PESSOA")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	
	@Column(name="nome", nullable = false)
	private String nome;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@Column(name="cpf", nullable = false, length = 14, unique = true)
	private String cpf;
	
	@Column(name="senha", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;
	
	@Column(name="createdat")
	@JsonProperty(access = Access.WRITE_ONLY)
	private OffsetDateTime createdAt = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));
	
	@Column(name="updateat")
	@JsonProperty(access = Access.WRITE_ONLY)
	private OffsetDateTime updateAt;
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private String token;

	public Pessoa(Long id) {
		super();
		this.id = id;
	}

}
