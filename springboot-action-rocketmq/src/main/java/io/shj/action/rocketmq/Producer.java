package io.shj.action.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("java-group");
        //启动nameserver
        producer.setNamesrvAddr("192.168.140.140:9876");
        //启动生产者
        producer.start();
        //创建消息
        Message message = new Message(
                "Topic_Demo",       //主题
                "Tags_1",            //标记 过滤条件
                "Key_1",             // key 可以根据key获取具体的消息
                "haha".getBytes(StandardCharsets.UTF_8)); //消息主体
        //发送消息
        SendResult result = producer.send(message);
        log.info("消息发送成功：{}",result);
        //关闭消息
        producer.shutdown();
    }
}

