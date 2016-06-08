package co.polynome.service;


import co.polynome.jobs.RapperBannerGenerator;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("worker")
@Component
public class WorkerRunner implements CommandLineRunner {
    @Autowired
    private Queue rabbitQueue;

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    private RapperBannerGenerator bannerGenerator;

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
