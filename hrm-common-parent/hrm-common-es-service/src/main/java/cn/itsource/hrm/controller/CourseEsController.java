package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.document.CourseDocument;
import cn.itsource.hrm.document.CourseDocumentQuery;
import cn.itsource.hrm.repository.CourseRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/es")
public class CourseEsController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/creat")
    public AjaxResult creat(@RequestBody List<CourseDocument> courseDocument){
        try {
            courseRepository.saveAll(courseDocument);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败！");
        }

    }
    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody List<Long> ids){
        try {
            for (Long id : ids) {
                courseRepository.deleteById(id);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败！");
        }

    }
    @PostMapping("/search")
    public PageList<CourseDocument> searchIndexs(@RequestBody CourseDocumentQuery query){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //构建查询与过滤
        //all关键字   courseTypeId  maxPrice  minPrice
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(StringUtils.isNotEmpty(query.getKeyword()))
            boolQueryBuilder.must(new MatchQueryBuilder("all",query.getKeyword()));
        List<QueryBuilder> filter = boolQueryBuilder.filter();
        if(query.getCourseTypeId()!=null)
            filter.add(new TermQueryBuilder("courseTypeId",query.getCourseTypeId()));
        //最高价格和最低价格
        if(query.getMaxPrice()!=null)
            filter.add(new RangeQueryBuilder("price").lte(query.getMaxPrice()));
        if(query.getMinPrice()!=null)
            filter.add(new RangeQueryBuilder("price").gte(query.getMinPrice()));
        builder.withQuery(boolQueryBuilder);


        //构建分页  es分页页数从0开始
        builder.withPageable(PageRequest.of(query.getPage()-1, query.getRows()));

        //构建排序条件
        // order by 列名  desc/asc
        //排序列
        String orderField = "startTime";
        if(StringUtils.isNotEmpty(query.getOrderField())){
            switch (query.getOrderField()){
                case "xp":
                    orderField = "startTime";
                    break;
                case "jg":
                    orderField = "price";
                    break;
            }
        }

        //排序方式
        SortOrder orderType = SortOrder.ASC;
        if(query.getOrderType()!=null){
            switch (query.getOrderType()){
                case 1://降序
                    orderType = SortOrder.DESC;
                    break;
                case 0://升序
                    orderType = SortOrder.ASC;
                    break;
            }
        }


        builder.withSort(new FieldSortBuilder(orderField).order(orderType));

        SearchQuery searchQuery = builder.build();
        Page<CourseDocument> page = courseRepository.search(searchQuery);
        PageList<CourseDocument> pageList =
                new PageList<>(page.getTotalElements(),page.getContent());
        return pageList;
    }


}
