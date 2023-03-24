package com.bera.ProjectExample.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bera.ProjectExample.dto.ErrorReturn;

@ControllerAdvice
public class ErrorHandlingController {
	
	//Tratamento de erro das CONSTRAINT do banco(unique, foreign_key)
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
		ErrorReturn errorReturn = new ErrorReturn();
	    errorReturn.setCodigoRetorno(400L); 
	    String error = ExceptionUtils.getStackTrace(ex);
	    if(error.contains("duplicate key value violates unique constraint \"tb_pessoa_cpf_key\"")) {
	    	errorReturn.setMensagem("CPF já cadastrado!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    return ResponseEntity.badRequest().body(error);
	}
	
	//Tratamento das propriedades do atributo, por exemplo @Column
	@ExceptionHandler(PropertyValueException.class)
	public final ResponseEntity<?> handlePropertyViolationException(PropertyValueException ex) {
		ErrorReturn errorReturn = new ErrorReturn();
		errorReturn.setCodigoRetorno(400L); 
	    String error = ExceptionUtils.getStackTrace(ex);
	    if(error.contains("not-null property")&& error.contains("Pessoa.senha") ) {
	    	errorReturn.setMensagem("Campo senha é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("Pessoa.cpf") ) {
	    	errorReturn.setMensagem("Campo CPF é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("Pessoa.email") ) {
	    	errorReturn.setMensagem("Campo email é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("Pessoa.nome") ) {
	    	errorReturn.setMensagem("Campo nome é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("Produto.nome") ) {
	    	errorReturn.setMensagem("Campo nome é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("Produto.preco") ) {
	    	errorReturn.setMensagem("Campo preco é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("Produto.descricao") ) {
	    	errorReturn.setMensagem("Campo descricao é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("PessoaProduto.idPessoa") ) {
	    	errorReturn.setMensagem("Campo idPessoa é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("PessoaProduto.idProduto") ) {
	    	errorReturn.setMensagem("Campo idProduto é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("not-null property")&& error.contains("PessoaProduto.quantidade") ) {
	    	errorReturn.setMensagem("Campo quantidade é obrigatório!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    return ResponseEntity.badRequest().body(error);
	}
	
	//Tratamento das exceções do banco, exemplo tamanho do Varchar
	@ExceptionHandler(DataException.class)
	public final ResponseEntity<?> handleDataException(DataException ex) {
		ErrorReturn errorReturn = new ErrorReturn();
	    errorReturn.setCodigoRetorno(400L); 
	    String error = ExceptionUtils.getStackTrace(ex);
	    if(error.contains("value too long for type character varying(14)")) {
	    	errorReturn.setMensagem("Tamanho do CPF inválido!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    return ResponseEntity.badRequest().body(error);
	}
	
	//Tratamento de erros de requisições feitas através do Body
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
		ErrorReturn errorReturn = new ErrorReturn();
		errorReturn.setCodigoRetorno(400L);
		String error = ExceptionUtils.getStackTrace(ex);
		if(error.contains("request body is missing")) {
	    	errorReturn.setMensagem("Adicionar o body na requisição");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
		return ResponseEntity.badRequest().body(error);
	}
	
	//Tratamento de erros de requisições feitas através da Header
	@ExceptionHandler(ServletRequestBindingException.class) 
	public final ResponseEntity<?> handleException(ServletRequestBindingException ex) {
		ErrorReturn errorReturn = new ErrorReturn();
	    errorReturn.setCodigoRetorno(400L); 
	    String error = ExceptionUtils.getStackTrace(ex);
	    if(error.contains("request header 'id'")) {
	    	errorReturn.setMensagem("Adicionar na Header o atributo id");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("request header 'idPessoa'")) {
	    	errorReturn.setMensagem("Adicionar na Header o atributo idPessoa");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    if(error.contains("Required request header 'token'")) {
	    	errorReturn.setMensagem("Acesso negado, faça seu login!");
	    	return ResponseEntity.badRequest().body(errorReturn);
	    }
	    return ResponseEntity.badRequest().body(error);
	}

}
