package br.com.vizentec.integracao.pardal.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class ConfigurationBean {

	private @Getter @Value("${spring.kafka.bootstrap-servers}") String boostrapServers;
	private @Getter @Value("${spring.kafka.consumer.group-id}") String groupId;

	private @Getter @Value("${pardal.client.consumer-username}") String topicUsername;
	private @Getter @Value("${pardal.client.consumer-password}") String topicPassword;

	private @Getter @Value("${pardal.client.topic-name}") String topicName;
}
