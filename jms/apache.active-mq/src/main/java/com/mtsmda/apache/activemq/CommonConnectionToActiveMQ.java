package com.mtsmda.apache.activemq;

import com.mtsmda.apache.activemq.domain.FootballClub;
import com.mtsmda.jms.common.ExceptionHandler;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by dminzat on 1/30/2017.
 */
public class CommonConnectionToActiveMQ {

    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer messageProducer;
    private MessageConsumer messageConsumer;


    private static Connection getConnection() {
        if (null == connection) {
            try {
                connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
                connection = connectionFactory.createConnection();
                connection.start();
            } catch (Exception e) {
                System.out.println(ExceptionHandler.toString(e));
            }
        }
        return connection;
    }

    public Session openSession(String queueOrTopicName, boolean isProducer, boolean isTopic) {
        try {
            session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
            if (isTopic) {
                destination = session.createTopic(queueOrTopicName);
            } else {
                destination = session.createQueue(queueOrTopicName);
            }

            if (isProducer) {
                messageProducer = session.createProducer(destination);
            } else {
                messageConsumer = session.createConsumer(destination);
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
        return session;
    }

    public void sendTextMessage(String text) {
        try {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(text);
            messageProducer.send(textMessage);
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveTextMessage() {
        try {
            Message receive = messageConsumer.receive();
            if (receive instanceof TextMessage) {
                System.out.println("receiveTextMessage - " + ((TextMessage) receive).getText());
            } else {
                throw new RuntimeException("This message not TextMessage type! Current is " + receive.getClass().getCanonicalName());
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveTextMessageWithListener() {
        try {
            messageConsumer.setMessageListener(message -> {
                try {
                    System.out.println("Received - " + ((TextMessage) message).getText() + " ldt - " + LocalDateTime.now());
                } catch (Exception e) {
                    System.out.println(ExceptionHandler.toString(e));
                }
            });
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void sendObjectMessage(Serializable serializable) {
        try {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(serializable);
            messageProducer.send(objectMessage);
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveObjectMessage() {
        try {
            Message receive = messageConsumer.receive();
            if (receive instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) receive;
                Serializable object = objectMessage.getObject();
                System.out.println("ObjectMessage - " + object.getClass().getCanonicalName());
            } else {
                throw new RuntimeException("This message not ObjectMessage type! Current is " + receive.getClass().getCanonicalName());
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void sendBytesMessage(FootballClub footballClub) {
        try {
            BytesMessage bytesMessage = session.createBytesMessage();
            bytesMessage.writeUTF(footballClub.getName());
            bytesMessage.writeUTF(footballClub.getCityName());
            bytesMessage.writeUTF(footballClub.getCountryName());
            bytesMessage.writeInt(footballClub.getFootballClubId().intValue());
            messageProducer.send(bytesMessage);
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveBytesMessage() {
        try {
            Message receive = messageConsumer.receive();
            if (receive instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) receive;
                FootballClub footballClub = new FootballClub(bytesMessage.readInt(), bytesMessage.readUTF(), bytesMessage.readUTF(), bytesMessage.readUTF());
                System.out.println("BytesMessage - footballClub = " + footballClub);
            } else {
                throw new RuntimeException("This message not BytesMessage type! Current is " + receive.getClass().getCanonicalName());
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void sendStreamMessage(FootballClub footballClub) {
        try {
            StreamMessage streamMessage = session.createStreamMessage();
            streamMessage.writeString(footballClub.getName());
            streamMessage.writeString(footballClub.getCityName());
            streamMessage.writeString(footballClub.getCountryName());
            streamMessage.writeInt(footballClub.getFootballClubId().intValue());
            messageProducer.send(streamMessage);
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveStreamMessage() {
        try {
            Message receive = messageConsumer.receive();
            if (receive instanceof StreamMessage) {
                StreamMessage streamMessage = (StreamMessage) receive;
                FootballClub footballClub = new FootballClub(streamMessage.readInt(), streamMessage.readString(), streamMessage.readString(), streamMessage.readString());
                System.out.println("StreamMessage - footballClub = " + footballClub);
            } else {
                throw new RuntimeException("This message not StreamMessage type! Current is " + receive.getClass().getCanonicalName());
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void sendMapMessage(Map<String, Object> map) {
        try {
            MapMessage mapMessage = session.createMapMessage();
            map.forEach((key, value) -> {
                try {
                    mapMessage.setObject(key, value);
                } catch (JMSException e) {
                    System.out.println(ExceptionHandler.toString(e));
                }
            });
            messageProducer.send(mapMessage);
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveMapMessage() {
        try {
            Message receive = messageConsumer.receive();
            if (receive instanceof MapMessage) {
                MapMessage streamMessage = (MapMessage) receive;
                FootballClub footballClub = new FootballClub(streamMessage.getInt("footballClubId"),
                        streamMessage.getString("name"), streamMessage.getString("countryName"), streamMessage.getString("cityName"));
                System.out.println("MapMessage - footballClub = " + footballClub);
            } else {
                throw new RuntimeException("This message not MapMessage type! Current is " + receive.getClass().getCanonicalName());
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

}