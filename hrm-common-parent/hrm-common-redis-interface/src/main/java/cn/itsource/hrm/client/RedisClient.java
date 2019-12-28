package cn.itsource.hrm.client;

import cn.itsource.hrm.client.impl.RedisClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@FeignClient(path = "/redis",value = "REDIS-SERVICE",fallback = RedisClientImpl.class)
public interface RedisClient {

    @PostMapping("/set")
    public void set(@RequestParam("key") String key, @RequestParam("value") String value);

    @GetMapping("/get")
    public String get(@RequestParam("key") String key);


}
