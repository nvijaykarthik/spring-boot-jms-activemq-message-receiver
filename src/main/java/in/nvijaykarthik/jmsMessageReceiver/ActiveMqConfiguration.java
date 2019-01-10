package in.nvijaykarthik.jmsMessageReceiver;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMqConfiguration {

	@Bean(name = "connectionFactory")
	public ConnectionFactory getCachingConnectionFactory()
			throws IllegalArgumentException, NamingException, JMSException {
		CachingConnectionFactory connFactory = new CachingConnectionFactory();
		connFactory.setTargetConnectionFactory(getConnectionFactory());
		connFactory.setSessionCacheSize(10);
		return connFactory;
	}

	@Bean
	public ConnectionFactory getConnectionFactory() throws NumberFormatException, JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		return factory;
	}
	
	@Bean(name = "jmsTemplate")
	public JmsTemplate getTemplate() {
		try {
			JmsTemplate template = new JmsTemplate(getCachingConnectionFactory());
			return template;
		} catch (IllegalArgumentException | NamingException | JMSException e) {
			e.printStackTrace();
		}
		return null;
	}
}
