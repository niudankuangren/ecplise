package tensquare_rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tensquare.rabbitmq.RabbitmqApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=RabbitmqApplication.class)
public class MqTest {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Test
	public void testSend(){
		rabbitTemplate.convertAndSend("itcast","直接模式");
	}
	
	@Test
	public void testSendFanout() {
		rabbitTemplate.convertAndSend("change", "", "分列模式走起");
		
	}
	
	@Test
	public void testSendTopic1() {
		rabbitTemplate.convertAndSend("topictest", "goods.aaa", "主题模式走起gods.#");
		
	}
	
	@Test
	public void testSendTopic2() {
		rabbitTemplate.convertAndSend("topictest", "aaa.list", "主题模式走起#.list");
		
	}
	
	@Test
	public void testSendTopic3() {
		rabbitTemplate.convertAndSend("topictest", "goods.list", "主题模式走起goods.list");
		
	}
}
