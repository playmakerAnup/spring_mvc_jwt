package com.example.demo.redis.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.example.demo.redis.dto.UserCache;

/**
 * CRUD for User Object<br>  
 * <h4>Created On: 19th-Jan-2019</h4><br>
 * @author Anup Nair<br>
 * 
 */
public interface UserRepository {

	  /**
	   * The User that is saved from this method will be stored in the
	   * cache and referenced by its' userId and deviceId in the form "userId:deviceId"
	   */
	   @CachePut("users")
	  public void saveUser(UserCache usersCache);

	  /**
	   * This method returns the cached output of saveUser.
	   */
	  @Cacheable("users")
	  public UserCache getUserByHashKey(Integer hashkey);

	  /**
	   * When this method is called, the cached User will be deleted from
	   * the cache.
	   */
	  @CacheEvict("users")
	  public void deleteUser(Integer id);
	}
