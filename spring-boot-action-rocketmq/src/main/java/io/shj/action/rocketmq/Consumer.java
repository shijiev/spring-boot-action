package io.shj.action.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static org.springframework.boot.web.servlet.server.Encoding.DEFAULT_CHARSET;

@Slf4j
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("java-group");
        //指定nameserver
        consumer.setNamesrvAddr("192.168.140.140:9876");
        //设置消息最大拉取数
        consumer.setConsumeMessageBatchMaxSize(2);
        //消息订阅
        consumer.subscribe("Topic_Demo", "*");
        //创建监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    try {
                        log.info("消息id：{}", messageExt.getMsgId());
                        //消息主题
                        String topic = messageExt.getTopic();
                        //标记
                        String tags = messageExt.getTags();
                        //消息主体
                        String msg = new String(messageExt.getBody(), DEFAULT_CHARSET);
                        System.out.println("消费消息：主题：" + topic + "==" + "标记：" + tags + "==" + "消息体" + msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}



