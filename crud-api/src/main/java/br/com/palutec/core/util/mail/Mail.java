package br.com.palutec.core.util.mail;

import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.commons.compress.utils.IOUtils;

import jakarta.mail.BodyPart;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;

public class Mail {

	public static void main(String args[]) throws Exception {

		String host = "10.10.0.9";
		String username = "documentos@gdemandas.net.br";
		String password = "documentos#123";

		int port = 993;

		// Crie uma propriedade para definir as configurações do servidor
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// Crie uma instância de Session com as propriedades e autenticação
		Session session = Session.getInstance(properties);

		
		// Conecte-se ao servidor IMAP
		Store store = session.getStore();
		store.connect(host, port, username, password);

		// Abra a caixa de entrada
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);

		// Obtenha as mensagens da caixa de entrada
		Message[] messages = inbox.getMessages();

		// Itere sobre as mensagens e faça o que desejar com elas
		for (Message message : messages) {
			
			if(message.getFlags().contains(Flags.Flag.SEEN)) {
				System.out.println("Ignorando email lido...");
				continue;
			}
			
		    // Converta a mensagem para MimeMessage (para acessar conteúdo e informações específicas)
		    MimeMessage mimeMessage = (MimeMessage) message;
		    
		    //message.setFlag(Flags.Flag.SEEN, true);
		    
		    // Acesse informações do email, como remetente, destinatários, assunto, conteúdo, etc.
		    String from = mimeMessage.getFrom()[0].toString();
		    String subject = mimeMessage.getSubject();

		    // Faça o que desejar com as informações do email
		    System.out.println("De: " + from);
		    System.out.println("Assunto: " + subject);
		    
		    // Verifique se a mensagem contém partes (incluindo anexos)
		    if (mimeMessage.isMimeType("multipart/*")) {
		        Multipart multipart = (Multipart) mimeMessage.getContent();

		        // Itere sobre as partes do email
		        for (int i = 0; i < multipart.getCount(); i++) {
		            BodyPart bodyPart = multipart.getBodyPart(i);

		            // Verifique se a parte é um anexo
		            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
		                // Obtenha o nome do arquivo anexo
		                String fileName = bodyPart.getFileName();
		                
		                var out = new FileOutputStream("/tmp/mail/"+fileName); 
		                IOUtils.copy(bodyPart.getInputStream(), out);
		                out.close();
		                
		                // Faça o que desejar com o arquivo anexo, como salvá-lo em disco
		                //bodyPart.saveFile("/caminho/para/salvar/" + fileName);
		                
		                
		                System.out.println("Anexo salvo: " + fileName);
		            }
		        }
		    }
		}
		
		// Feche a caixa de correio e desconecte do servidor IMAP
		inbox.close(false);
		store.close();

	}
}
