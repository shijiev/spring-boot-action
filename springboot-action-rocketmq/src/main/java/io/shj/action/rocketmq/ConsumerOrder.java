package io.shj.action.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static org.apache.rocketmq.remoting.common.RemotingHelper.DEFAULT_CHARSET;

@Slf4j
public class ConsumerOrder {
    public static void main(String[] args) throws MQClientException {
        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("java_order_group");
        //指定nameserver
        consumer.setNamesrvAddr("192.168.140.140:9876");
        //设置消息最大拉取数
        consumer.setConsumeMessageBatchMaxSize(2);
        //消息订阅
        consumer.subscribe("Topic_order_Demo", "*");
        //创建监听
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt messageExt : list) {
                    try {
                        log.info("消息id：{}", messageExt.getMsgId());
                        //消息主题
                        String topic = messageExt.getTopic();
                        //标记
                        String tags = messageExt.getTags();
                        //消息主体
                        String msg = new String(messageExt.getBody(), DEFAULT_CHARSET);
                        System.out.println("消费顺序消息：" + "主题：" + topic + "==" + "标记：" + tags + "==" + "消息主体" + msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //重试
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }
}

