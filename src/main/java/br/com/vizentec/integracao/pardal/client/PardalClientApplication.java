package br.com.vizentec.integracao.pardal.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import lombok.Getter;

@EnableJms
@SpringBootApplication
public class PardalClientApplication {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Value("${pardal.broker.username}")
	private @Getter String username;

	@Value("${pardal.broker.password}")
	private @Getter String password;


	public static void main(String[] args) {
		SpringApplication.run(PardalClientApplication.class, args);
	}

	@Bean
	public ActiveMQConnectionFactory amqConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(this.username, this.password, this.brokerUrl);
		activeMQConnectionFactory.setAlwaysSyncSend(true);

		return activeMQConnectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(amqConnectionFactory());
		factory.setPubSubDomain(true);
		factory.setAutoStartup(true);
		return factory;
	}
}
