package com.example.demo.redis.configuration;

/**
 * Redis configuration class<br>  
 * <h4>Created On: 19th-Jan-2019</h4><br>
 * @author Anup Nair<br>
 * 
 * 
 */
public interface MessagePublisher {

    void publish(final String message);
}
