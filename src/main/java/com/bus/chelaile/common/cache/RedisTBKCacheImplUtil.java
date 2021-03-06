package com.bus.chelaile.common.cache;

import net.spy.memcached.internal.OperationFuture;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bus.chelaile.common.Constants;
import com.bus.chelaile.model.PropertiesName;
import com.bus.chelaile.util.New;
import com.bus.chelaile.util.config.PropertiesUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisTBKCacheImplUtil implements ICache {
    private static Logger log = LoggerFactory.getLogger(RedisTBKCacheImplUtil.class);
    //	private static final String DEFAULT_REDIS_HOST = "127.0.0.1";
    //	private static final int DEFAULT_REDIS_PORT = 6379;

    //private static String REDIS_HOST = PropertiesReaderWrapper.read("redisCount.host", DEFAULT_REDIS_HOST);
    //private static int REDIS_PORT = PropertiesReaderWrapper.readInt("redisCount.port", DEFAULT_REDIS_PORT);

    private static String REDIS_HOST = PropertiesUtils.getValue(PropertiesName.CACHE.getValue(), "redisTBK.host");
    private static int REDIS_PORT = Integer.parseInt(PropertiesUtils.getValue(PropertiesName.CACHE.getValue(), "redisTBK.port"));

    private static JedisPool pool = null;

    static {
        initPool();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        String host = REDIS_HOST;
        int port = REDIS_PORT;
        config.setMaxTotal(600);
        //config.setMaxActive(400);
        config.setMaxIdle(200);
        config.setMinIdle(20);

        //config.setMaxWait(2000000);
        //config.setMaxWaitMillis();
//        config.setTestWhileIdle(true);
//        config.setTestOnBorrow(true);
//        config.setTestOnReturn(true);

        pool = new JedisPool(config, host, port);

        log.info("Redis_TBK_CacheImplUtil init success,ip={},host={}", REDIS_HOST, REDIS_PORT);
    }

    private static JedisPool getPool() {
        if (pool == null)
            initPool();
        return pool;
    }

    public void set(String key, String value, int expire) {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.set(key, value);
            if (expire != -1)
                conn.expire(key, expire);
            log.debug("Redis-Set: Key=" + key + ",Value=" + value);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.set, key=%s value=%s, error message: " + e.getMessage(), key, value));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
    }

    public void setList(String key, String value, int expire) {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.lpush(key, value);
            if (expire != -1)
                conn.expire(key, expire);
            log.debug("Redis-Set: Key=" + key + ",Value=" + value);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.set, key=%s value=%s, error message: " + e.getMessage(), key, value));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
    }

    public void setSet(String key, String value, int expire) {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.sadd(key, value);
            if (expire != -1)
                conn.expire(key, expire);
            log.debug("Redis-Set: Key=" + key + ",Value=" + value);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.set, key=%s value=%s, error message: " + e.getMessage(), key, value));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
    }

    /**
     * 缓存 有序集合
     * @param key
     * @param score
     * @param value
     * @param expire
     */
    public void setSortedSet(String key, double score, String value, int expire) {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.zadd(key, score, value);
            if (expire != -1)
                conn.expire(key, expire);
            log.debug("Redis-SortedSet: Key=" + key + ",Value=" + value);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.set, key=%s value=%s, error message: " + e.getMessage(), key, value));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
    }

    /**
     * 按照score，从小到大获取值。 起始score为startScore，从0开始获取count个值
     * @return
     */
    public Set<String> zrangeByScore(String key, double startScore, double endScore, int count) {
        JedisPool pool = null;
        Jedis conn = null;
        Set<String> value = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            value = conn.zrangeByScore(key, startScore, endScore, 0, count);
            log.debug("Redis-SortedGet: Key=" + key + ",Value=" + value);
        } catch (Exception e) {
            log.error(
                    String.format("Error occur in Redis.set, key=%s startScore=%s, endScore=%s, error message: " + e.getMessage(),
                            key, startScore, endScore));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return value;
    }

    /**
     * 按照score，从大到小获取值。 起始score为endScore，从0开始获取count个值
     * @return
     */
    public Set<String> zrevRangeByScore(String key, double endScore, double startScore, int count) {
        JedisPool pool = null;
        Jedis conn = null;
        Set<String> value = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            value = conn.zrevrangeByScore(key, endScore, startScore, 0, count);
            log.debug("Redis-SortedGet: Key=" + key + ",Value=" + value);
        } catch (Exception e) {
            log.error(
                    String.format("Error occur in Redis.set, key=%s startScore=%s, endScore=%s, error message: " + e.getMessage(),
                            key, startScore, endScore));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return value;
    }

    public void clearDB() {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.flushDB();
            log.debug("Redis-clearDB");
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.flushDB"));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
    }

    public Set allKeys(String patternstr) {
        JedisPool pool = null;
        Jedis conn = null;
        Set result = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            result = conn.keys(patternstr);
            log.debug("Redis-Keys: pattern=" + patternstr);
        } catch (Exception e) {
            log.error("Error occur in Redis.keys, pattern=: " + patternstr + " exception:" + e);
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return result;
    }

    public void set(String key, String value) {
        set(key, value, -1);
    }

    public void addList(String key, String value) {
        setList(key, value, -1);
    }

    public List<String> getMValue(String[] udidList) {
        JedisPool pool = null;
        Jedis conn = null;
        List<String> ret = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            ret = conn.mget(udidList);
            log.debug("Redis-Get: Key=" + udidList.length + ",Value=" + ret);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.set, key=%s, error message: " + e.getMessage(), udidList.length));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return ret;
    }

    public String get(String key) {
        long startTime = System.currentTimeMillis();
        JedisPool pool = null;
        Jedis conn = null;
        String ret = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            ret = conn.get(key);
            log.debug("Redis-Get: Key=" + key + ",Value=" + ret);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.get, key=%s, error message: " + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        if(System.currentTimeMillis() - startTime > 50) {
            log.info("TBK redis.get cost time :{}", System.currentTimeMillis() - startTime);
        }
        
        return ret;
    }

    public List<String> getList(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        List<String> ret = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            ret = conn.lrange(key, 0, -1);
            log.debug("Redis-Get: Key=" + key + ",Value=" + ret);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.getList, key=%s, error message: " + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }

        return ret;
    }

    public Set<String> getSet(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        Set<String> ret = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            ret = conn.smembers(key);
            log.debug("Redis-Get: Key=" + key + ",Value=" + ret);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.set, key=%s, error message: " + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }

        return ret;
    }

    public void del(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.del(key);
            log.debug("Redis-Del: Key=" + key);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
    }

    public long delFromSet(String key, String member) {
        JedisPool pool = null;
        Jedis conn = null;
        long i = -1;
        try {
            pool = getPool();
            conn = pool.getResource();
            i = conn.srem(key, member);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return i;
    }

    public long delFromSortedSet(String key, String member) {
        JedisPool pool = null;
        Jedis conn = null;
        long i = -1;
        try {
            pool = getPool();
            conn = pool.getResource();
            i = conn.zrem(key, member);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return i;
    }

    public boolean isInSet(String key, String member) {
        JedisPool pool = null;
        Jedis conn = null;
        boolean flag = false;
        try {
            pool = getPool();
            conn = pool.getResource();
            flag = conn.sismember(key, member);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return flag;
    }

    public long getValueSize(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        long i = 0;
        try {
            pool = getPool();
            conn = pool.getResource();
            i = conn.scard(key);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return i;
    }

    public long getSortedSetValueSize(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        long i = 0;
        try {
            pool = getPool();
            conn = pool.getResource();
            i = conn.zcard(key);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return i;
    }

    /**
     * 设置有效期，单位 秒
     */
    @Override
    public long IncValue(String key, int expire) {
        JedisPool pool = null;
        Jedis conn = null;
        long i = 0;
        try {
            pool = getPool();
            conn = pool.getResource();
            i = conn.incr(key);
            if (expire != -1)
                conn.expire(key, expire);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return i;
    }

    public static long DecValue(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        long i = 0;
        try {
            pool = getPool();
            conn = pool.getResource();
            i = conn.decr(key);
            log.debug("Redis-Del: Key=" + key);

        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return i;
    }

    public boolean compareAndIncrBy(final String key, final String exp, final long incrValue) {
        JedisPool pool = null;
        Jedis conn = null;
        boolean ret = false;
        try {
            pool = getPool();
            conn = pool.getResource();

            conn.watch(key); //监视当前的key
            String curr = conn.get(key);

            if (StringUtils.equals(exp, curr)) {
                Transaction tx = conn.multi();
                tx.incrBy(key, incrValue);
                List<Object> txResult = tx.exec();
                if (txResult != null && !txResult.isEmpty()) {
                    // 修改成功
                    ret = true;
                }
            }
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.compareAndIncrBy, key=%s, errMsg=%s", key, e.getMessage()), e);

            if (pool != null && conn != null) {
                conn.unwatch();
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
            ret = false;
        } finally {
            if (pool != null && conn != null) {
                conn.unwatch();
                pool.returnResource(conn);
            }
        }

        if (ret) {
            log.debug("Redis-compareAndIncBy: Key={}, success", key);
        } else {
            log.debug("Redis-compareAndIncBy: Key={}, fail", key);
        }

        return ret;
    }

    public boolean compareAndDecrBy(final String key, final String exp, final long decValue) {
        JedisPool pool = null;
        Jedis conn = null;
        boolean ret = false;
        try {
            pool = getPool();
            conn = pool.getResource();

            conn.watch(key); //监视当前的key
            String curr = conn.get(key);

            if (StringUtils.equals(exp, curr)) {
                Transaction tx = conn.multi();
                tx.decrBy(key, decValue);
                List<Object> txResult = tx.exec();
                if (txResult != null && !txResult.isEmpty()) {
                    // 修改成功
                    ret = true;
                }
            }
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.compareAndDecrBy, key=%s, errMsg=%s", key, e.getMessage()), e);

            if (pool != null && conn != null) {
                conn.unwatch();
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
            ret = false;
        } finally {
            if (pool != null && conn != null) {
                conn.unwatch();
                pool.returnResource(conn);
            }
        }

        if (ret) {
            log.debug("Redis-compareAndDecrBy: Key={}, success", key);
        } else {
            log.debug("Redis-compareAndDecrBy: Key={}, fail", key);
        }

        return ret;
    }

    public String getHashSetValue(String key, String field) {
        JedisPool pool = null;
        Jedis conn = null;
        String result = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            result = conn.hget(key, field);

            log.debug("Redis-Hget: Key={}, field={}, value={}", key, field, result);
        } catch (Exception e) {
            log.error("Error occur in Redis.Hget, key={}, error message: {}", key, e.getMessage());
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return result;
    }

    public Long setHashSetValue(String key, String field, String value) {
        JedisPool pool = null;
        Jedis conn = null;
        Long result = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            result = conn.hset(key, field, value);

            log.debug("Redis-Hset: Key={}, field={}, value={}", key, field, result);
        } catch (Exception e) {
            log.error("Error occur in Redis.Hset, key={}, error message: {}", key, e.getMessage());
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return result;
    }

    @Override
    public void set(String key, int exp, Object obj) {
        set(key, (String) obj, exp);

    }

    @Override
    public OperationFuture<Boolean> delete(String key) {
        del(key);
        return null;
    }

    @Override
    public Map<String, Object> getByList(List<String> list) {
        Jedis jedis = getPool().getResource();
        Map<String, Object> map = New.hashMap();
        try {
            String[] keysArr = new String[list.size()];
            keysArr = list.toArray(keysArr);
            List<String> valueList = jedis.mget(keysArr);
            int i = 0;
            for (String key : list) {
                map.put(key, valueList.get(i));
                i++;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null)
                pool.returnResource(jedis);
        }
        return map;
    }

    /*
     * 获取锁
     */
    @Override
    public boolean acquireLock() {
        Jedis jedis = getPool().getResource();
        long expires = System.currentTimeMillis() + Constants.EXPIREMSECS + 1; // 锁到期时间
        String expiresStr = String.valueOf(expires);

        try {
            if (jedis.setnx(Constants.LOCKKEY, expiresStr) == 1) {
                return true;
            }

            String currentValueStr = jedis.get(Constants.LOCKKEY); // redis里的时间
            // now > lock.timeout，说明锁超时
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {

                // 获取上一个锁到期时间，并设置现在的锁到期时间，
                String oldValueStr = jedis.getSet(Constants.LOCKKEY, expiresStr);
                // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    // 如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return false;
    }

    /*
     * 释放锁
     */
    @Override
    public boolean releaseLock() {
        Jedis jedis = getPool().getResource();
        String currentValueStr = jedis.get(Constants.LOCKKEY); // redis里的时间
        try {
            if (currentValueStr != null && System.currentTimeMillis() < Long.parseLong(currentValueStr)) {
                // now < lock.timeout，则主动释放锁。否则，不用
                jedis.del(Constants.LOCKKEY);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return false;
    }

    /*
     * 增加指定数目
     */
    @Override
    public void incrBy(String key, int incNumber, int exp) {
        JedisPool pool = null;
        Jedis conn = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            conn.incrBy(key, incNumber);
            if (exp != -1)
                conn.expire(key, exp);
        } catch (Exception e) {
            log.error(String.format("Error occur in Redis.del, key=%s" + e.getMessage(), key));
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }

    }

    public Map<String, String> getHsetAll(String key) {
        JedisPool pool = null;
        Jedis conn = null;
        Map<String, String> result = null;
        try {
            pool = getPool();
            conn = pool.getResource();
            result = conn.hgetAll(key);

            log.debug("Redis-Hget: Key={}, value={}", key, result);
        } catch (Exception e) {
            log.error("Error occur in Redis.Hget, key={}, error message: {}", key, e.getMessage());
            if (pool != null && conn != null) {
                pool.returnResource(conn);
                pool = null;
                conn = null;
            }
        } finally {
            if (pool != null && conn != null)
                pool.returnResource(conn);
        }
        return result;
    }
}
