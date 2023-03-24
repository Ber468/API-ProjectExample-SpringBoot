package com.bera.ProjectExample.Utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailSender {
	
	private static final String hostName = "smtp.gmail.com";
	private static final String smtpPort = "465";
	private static final String charSet = "UTF-8";
	
	private String de = "bernardoradin.dalpozzo@gmail.com";
	private String senhaApp = "dlypyqqtoraqifrr";
	private String deApp = "Bernardo";
	private String assunto = "Recuperação de senha";
	
	public void enviarRecuperarSenha(String email, String nome, String novaSenha) {
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setCharset(charSet);
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setHostName(hostName);
		htmlEmail.setSslSmtpPort(smtpPort);
		htmlEmail.setAuthenticator(new DefaultAuthenticator(de, senhaApp));
		try {
			htmlEmail.setFrom(de, deApp);
			htmlEmail.setSubject(assunto);
			StringBuilder builder = new StringBuilder();
			builder.append("<h2><hr> Bem vindo <b>"+nome+"</b></h2>");
			builder.append("<p>Seu pedido para redefinir a sua senha foi aceito. Sua senha temporária é: <b>"+novaSenha+"</b></p>");
			builder.append("<h4><hr>Estamos a sua disposição, tenha um ótimo dia!</h4>");
			builder.append("<img width=\"170\" heigth=\"250\" src=\"https://www.imagemhost.com.br/images/2023/03/24/MinhaFoto.jpg\" alt=\"MinhaFoto.jpg\" border=\"0\" />");
			htmlEmail.setHtmlMsg(builder.toString());
			htmlEmail.addTo(email);
			htmlEmail.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
