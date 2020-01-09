package cn.itsource.hrm.client.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.client.CourseClient;
import cn.itsource.hrm.document.CourseDocument;
import cn.itsource.hrm.document.CourseDocumentQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseClientImpl implements CourseClient {
    @Override
    public AjaxResult creat(List<CourseDocument> courseDocument) {
        return AjaxResult.me().setMessage("托底数据，保存失败了");
    }

    @Override
    public AjaxResult delete(List<Long> ids) {
        return AjaxResult.me().setMessage("托底数据，删除失败了");
    }

    @Override
    public PageList<CourseDocument> searchIndexs(CourseDocumentQuery courseDocumentQuery) {
        return null;
    }
}
