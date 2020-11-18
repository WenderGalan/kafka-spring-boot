package io.github.wendergalan.websocketchat.config.websocket;

import io.github.wendergalan.websocketchat.interceptor.CustomHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
// Habilita o websocket na api
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configura o prefixo para o stomp
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                // Endpoint que os clientes usarão para se conectar
                .addEndpoint("/ws")
                // Aceita todas as origins
                .setAllowedOrigins("*")
                // Interceptor handshake
                .setHandshakeHandler(new CustomHandshakeHandler())
                // Usado para habilitar o fallback para navegadores que não suportam websocket
                .withSockJS();
    }

    /**
     * Configura os prefixos do message broker
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                // Mensagens com esse destino devem ser roteadas para métodos de tratamento de mensagens
                .setApplicationDestinationPrefixes("/app");
        registry
                // Mensagens com esse destino devem ser roteadas para o agente de mensagens
                .enableSimpleBroker("/topic");
    }

    /**
     * Configura os limites do socket
     *
     * @param registry
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // Tamanho limite para transporte na mensagem
        registry.setMessageSizeLimit(200000);
        // Tempo máximo para enviar de 200 segundos
        registry.setSendTimeLimit(20 * 10000);
        // Tamanho limite para enviar
        registry.setSendBufferSizeLimit(3 * 512 * 1024);
    }

    /**
     * Configura o inbound channel
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public org.springframework.messaging.Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor == null)
                    return message;
                if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    accessor.setUser(auth);
                }
                return message;
            }
        });
    }
}
