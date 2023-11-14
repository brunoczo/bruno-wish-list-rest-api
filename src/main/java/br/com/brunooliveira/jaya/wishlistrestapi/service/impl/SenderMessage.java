package br.com.brunooliveira.jaya.wishlistrestapi.service.impl;

import br.com.brunooliveira.jaya.wishlistrestapi.dto.WishListDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.SerializationUtils;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Component
public class SenderMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SenderMessage.class);
    //@Value("${wishlist.queue}")
    private static  String QUEUE = "wishListqueue";

    @Autowired
    Sender sender;
    public void sendToQueue(WishListDTO wl){
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(wl);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        byte[] orderSerialized = SerializationUtils.serialize(json);
        Flux<OutboundMessage> outbound = Flux.just( new OutboundMessage(
                "",
                QUEUE, orderSerialized));

        // Declare the queue then send the flux of messages.
        sender.declareQueue(QueueSpecification.queue(QUEUE))
                .thenMany(sender.sendWithPublishConfirms(outbound))
                .doOnError(e -> LOGGER.error("Send failed", e))
                .subscribe(m -> {
                    LOGGER.info("Message sent");
                });
    }

}
