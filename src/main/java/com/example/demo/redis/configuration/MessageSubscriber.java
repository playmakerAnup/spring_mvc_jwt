package com.example.demo.redis.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Redis configuration class<br>  
 * <h4>Created On: 19th-Jan-2019</h4><br>
 * @author Anup Nair<br>
 * 
 */
public class MessageSubscriber implements MessageListener{


    public static List<String> messageList = new ArrayList<String>();

	@Override
	public void onMessage(final Message message, byte[] pattern) {
		// TODO Auto-generated method stub
		  messageList.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
		
	}
}
