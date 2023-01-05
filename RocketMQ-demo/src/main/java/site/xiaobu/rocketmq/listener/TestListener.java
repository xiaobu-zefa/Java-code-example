package site.xiaobu.rocketmq.listener;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RocketMQMessageListener(
        consumerGroup = "${rocketmq.consumer.group}",
        topic = "${rocketmq.consumer.topic}",
        consumeMode = ConsumeMode.ORDERLY
)
public class TestListener implements RocketMQListener<MessageExt> {

    int a = 0;

    @Override
    public void onMessage(MessageExt s) {
        String body = new String(s.getBody(), StandardCharsets.UTF_8);
        System.out.println("消息内容[" + body + "]");
        System.out.println("当前重试次数: " + s.getReconsumeTimes());
        if (a < 10) {
            a++;
            throw new RuntimeException("消费失败");
        }
        System.out.println("消费成功");
    }
}
