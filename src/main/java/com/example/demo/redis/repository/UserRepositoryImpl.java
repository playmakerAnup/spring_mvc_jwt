package com.example.demo.redis.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.redis.dto.UserCache;
import com.example.demo.utils.Validate;

@Component
public class UserRepositoryImpl implements UserRepository {

	private static final String KEY = "JwtUser";
    private RedisTemplate<String , Object> redisTemplate;
    private HashOperations hashOperations;
    
    @Autowired
    public UserRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveUser(UserCache usersCache) {
		// TODO Auto-generated method stub
		if(!Validate.isEmpty(usersCache) && !Validate.isEmpty(usersCache.getUserId())) {
			hashOperations.put(KEY, usersCache.getUserId(), usersCache);	
		}
	}

	@Override
	public UserCache getUserByHashKey(Integer hashkey) {
		// TODO Auto-generated method stub
		return (UserCache) hashOperations.get(KEY, hashkey);
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		hashOperations.delete(KEY, id);
	}
 
}
