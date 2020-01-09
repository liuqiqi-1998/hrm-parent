package cn.itsource.hrm.client;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.client.impl.CourseClientImpl;
import cn.itsource.hrm.document.CourseDocument;
import cn.itsource.hrm.document.CourseDocumentQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Component
@FeignClient(value = "ES-SERVICE",path = "/es",fallback = CourseClientImpl.class)
public interface CourseClient {
    
    @PostMapping("/creat")
    public AjaxResult creat(@RequestBody List<CourseDocument> courseDocument);
    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody List<Long> ids);

    @PostMapping("/search")
    PageList<CourseDocument> searchIndexs(CourseDocumentQuery courseDocumentQuery);
}
