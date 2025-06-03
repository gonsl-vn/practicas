package com.viewnext.practica6.config;

import com.viewnext.practica6.model.Mensaje;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Kafka stream config.
 */
@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    /**
     * Kafka streams configuration kafka streams configuration.
     *
     * @return the kafka streams configuration
     */
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> props = new HashMap<>();

        props.put("application.id", "kafka-streams-app"); // id de la aplicación
        props.put("bootstrap.servers", "localhost:9092"); // ruta en la que está nuestro bootstrap server
        props.put("default.key.serde", Serdes.String().getClass().getName());
        props.put("default.value.serde", JsonSerde.class.getName());
        props.put("auto.offset.reset",
                "latest"); // Cogemos el último parámetro dentro del offset, en concreto dentro del tópico, en nuestro caso "topic1"

        return new KafkaStreamsConfiguration(props);
    }

    /**
     * K stream k stream.
     *
     * @param streamsBuilder
     *         the streams builder
     * @return the k stream
     */
    @Bean
    public KStream<String, Mensaje> kStream(StreamsBuilder streamsBuilder) {
        JsonSerde<Mensaje> mensajeSerde = new JsonSerde<>(Mensaje.class);

        KStream<String, Mensaje> stream = streamsBuilder.stream("topic1", Consumed.with(Serdes.String(), mensajeSerde));

        stream.foreach((key, mensaje) -> System.out.println("Mensaje recibido en stream: " + mensaje));

        return stream;
    }
}

