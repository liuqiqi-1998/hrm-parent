package cn.itsource.hrm.client;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.impl.StaticPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "STATIC-SERVICE",fallback = StaticPageImpl.class)
public interface StaticPageClient {

    @PostMapping("/staticPage")
    AjaxResult staticPages(@RequestParam("dataKey") String dataKey, @RequestParam("pageId") Long pageId);

}
