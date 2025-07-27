package br.com.grupo9.middleware.config;

import br.com.grupo9.middleware.listener.RedisListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisListener redisListener;
    private static final String CRUD_CHANNEL = "crud-channel";

    /**
     * Cria um adaptador que conecta a lógica de recebimento de mensagens
     * à nossa classe de negócio (RedisListener).
     * O Spring chamará o método "handleMessage" do nosso listener.
     */
    @Bean
    MessageListenerAdapter listenerAdapter() {
        MessageListenerAdapter adapter = new MessageListenerAdapter(redisListener, "handleMessage");
        // Define que a mensagem recebida do Redis deve ser convertida para String antes de chamar nosso método.
        adapter.setSerializer(new StringRedisSerializer());
        return adapter;
    }

    /**
     * Cria e configura o container que gerencia o processo de escuta (listening).
     *
     * @param connectionFactory A fábrica de conexões com o Redis, gerenciada pelo Spring.
     * @return O container configurado para ouvir o canal 'crud-channel'.
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // Adiciona nosso adaptador (que contém nosso listener) para o canal desejado.
        container.addMessageListener(listenerAdapter(), new ChannelTopic(CRUD_CHANNEL));
        return container;
    }
}