package io.shj.action.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class ProducerOrder {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("java_order_group");
        //启动nameserver
        producer.setNamesrvAddr("192.168.140.140:9876");
        //启动生产者
        producer.start();
        //创建消息
        //发送消息(指定)
        for (int i=0;i<3;i++){
            Message message = new Message(
                    "Topic_order_Demo",       //主题
                    "Tags_order",            //标记 过滤条件
                    "Key_order"+i,             // key 可以根据key获取具体的消息
                    ("hello"+i).getBytes(StandardCharsets.UTF_8)); //消息主体
            SendResult result = producer.send(
                    message,
                    new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                            Integer index=(Integer) arg;
                            return list.get(index);
                        }
                    },
                    0
            );
            log.info("消息发送结果：{}",result);
        }
        //关闭消息
        producer.shutdown();
    }
}


