package com.sz.cache;

public interface RedisLock extends AutoCloseable {

	void unlock();

}
