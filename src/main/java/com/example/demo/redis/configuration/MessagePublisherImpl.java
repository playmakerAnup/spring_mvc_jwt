package com.example.demo.redis.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * Redis configuration class<br>  
 * <h4>Created On: 19th-Jan-2019</h4><br>
 * @author Anup Nair<br>
 * 
 */
public class MessagePublisherImpl implements MessagePublisher {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ChannelTopic topic;

	public MessagePublisherImpl() {
	}

	public MessagePublisherImpl(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	@Override
	public void publish(final String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}

}
