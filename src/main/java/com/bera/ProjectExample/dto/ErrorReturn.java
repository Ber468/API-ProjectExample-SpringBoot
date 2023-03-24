package com.bera.ProjectExample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErrorReturn {
	
	private Long codigoRetorno;
	
	private String mensagem;

}
