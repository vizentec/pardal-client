package br.com.vizentec.integracao.pardal.client.consumer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

	@Value("${pardal.client.diretorio:/tmp/pardal_client/}")
	private String filePath;

	@JmsListener(destination="${pardal.autenticator.topic}", containerFactory="jmsListenerContainerFactory")
	public void process(String message) {
		System.out.println("Receiving message: " + message);
		try {
			String xml = XML.toString(new JSONObject(message));
			FileUtils.writeStringToFile(new File(filePath + File.separator + UUID.randomUUID() + ".xml"), xml, Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
