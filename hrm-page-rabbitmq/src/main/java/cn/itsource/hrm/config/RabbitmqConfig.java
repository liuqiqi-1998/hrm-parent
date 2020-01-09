package cn.itsource.hrm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author solargen
 * @Date 2020/1/3 16:15
 * @Version v1.0
 **/
@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_INFORM_SITE = "hrm-course";
    public static final String EXCHANGE_DIRECT_INFORM = "exchange_direct_inform";


    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(EXCHANGE_DIRECT_INFORM)
    public Exchange EXCHANGE_DIRECT_INFORM() {
        //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_INFORM_SITE)
    public Queue QUEUE_INFORM_SMS() {
        Queue queue = new Queue(QUEUE_INFORM_SITE);
        return queue;
    }

    /**
     * channel.queueBind(INFORM_QUEUE_SMS,"inform_exchange_topic","inform.#.sms.#");
     * 绑定队列到交换机 .
     *
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SITE)Queue queue,
                                            @Qualifier(EXCHANGE_DIRECT_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_INFORM_SITE).noargs();
    }


}
