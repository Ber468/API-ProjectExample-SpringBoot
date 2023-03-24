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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name="TB_PESSOA_PRODUTO")
public class PessoaProduto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	
	@Column(name="quantidade", nullable = false)
	private Long quantidade;
	
	@OneToOne
	@JoinColumn(name="idpessoa", nullable = false)
	private Pessoa idPessoa;
	
	@OneToOne
	@JoinColumn(name="idproduto", nullable = false)
	private Produto idProduto;
	
	@Column(name="createdat")
	@JsonProperty(access = Access.WRITE_ONLY)
	private OffsetDateTime createdAt = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));
	
	@Column(name="updateat")
	@JsonProperty(access = Access.WRITE_ONLY)
	private OffsetDateTime updateAt;

	public PessoaProduto(Long id) {
		super();
		this.id = id;
	}
	

}
