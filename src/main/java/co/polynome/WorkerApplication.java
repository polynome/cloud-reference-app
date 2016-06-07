package co.polynome;


import co.polynome.jobs.RapperBannerGenerator;
import com.google.common.collect.ImmutableSet;
import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;
import org.hibernate.jdbc.Work;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class WorkerApplication implements CommandLineRunner {
    @Autowired
    private Queue rabbitQueue;

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    private RapperBannerGenerator bannerGenerator;


    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication();
        app.setWebEnvironment(false);
        app.setMainApplicationClass(WorkerApplication.class);
        app.setSources(ImmutableSet.of(WorkerApplication.class)); // TODO why not automatic?
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("WORKER IS STARTING");
        final SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        final MessageConverter messageConverter = new SimpleMessageConverter();
        listenerContainer.setConnectionFactory(rabbitConnectionFactory);
        listenerContainer.setQueueNames(rabbitQueue.getName());
        listenerContainer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                final Object[] payload = (Object[]) messageConverter.fromMessage(message);
                final String jobType = (String) payload[0];
                final Long id = (Long) payload[1];
                System.out.println(jobType);
                System.out.println(id);

                if (jobType.equals("RapperBannerGenerator")) {
                    bannerGenerator.run(id);
                } else {
                    throw new RuntimeException("Don't know how to run: " + jobType);
                }
            }
        });
        listenerContainer.start();
    }
}
