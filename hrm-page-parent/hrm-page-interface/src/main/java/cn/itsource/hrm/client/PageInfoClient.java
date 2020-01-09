package cn.itsource.hrm.client;

import cn.itsource.hrm.client.impl.PageInfoImpl;
import cn.itsource.hrm.domain.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(path = "/pageInfo",value = "PAGE-SERVICE",fallback = PageInfoImpl.class)
public interface PageInfoClient {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PageInfo get(@PathVariable("id")Long id);
}
