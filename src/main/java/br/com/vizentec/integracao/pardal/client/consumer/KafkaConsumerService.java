package br.com.vizentec.integracao.pardal.client.consumer;

import java.io.File;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.vizentec.integracao.pardal.client.config.ConfigurationBean;

@Service
public class KafkaConsumerService {

	private @Autowired ConfigurationBean configBean;
	private @Value("${pardal.client.diretorio:c://tmp/pardal_client//}") String filePath;

	private KafkaConsumer<String, String> consumer;
	private Properties props;

	@PostConstruct
	public void init() {
		String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
		String jaasCfg = String.format(jaasTemplate, configBean.getTopicUsername(), configBean.getTopicPassword());
		String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        props = new Properties();
        props.put("bootstrap.servers", configBean.getBoostrapServers());
        props.put("group.id", configBean.getGroupId());
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism","PLAIN");
        props.put("sasl.jaas.config", jaasCfg);

        consume();
	}

	private void consume() {
		consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(configBean.getTopicName()));
		do {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

			for (ConsumerRecord<String, String> record : records) {
				System.out.println("Consumindo registros do tópico: ".concat(configBean.getTopicName()).concat(" - ").concat(record.value()));
				try {
					FileUtils.writeStringToFile(new File(filePath + File.separator + UUID.randomUUID() + ".json"), record.value(),
							Charset.defaultCharset());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		} while (true);
	}
}
