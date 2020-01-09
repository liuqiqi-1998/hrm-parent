package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.client.CourseClient;
import cn.itsource.hrm.client.SystemDictionaryitemClient;
import cn.itsource.hrm.document.CourseDocument;
import cn.itsource.hrm.document.CourseDocumentQuery;
import cn.itsource.hrm.domain.*;
import cn.itsource.hrm.mapper.*;
import cn.itsource.hrm.query.CourseQuery;
import cn.itsource.hrm.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;
    @Autowired
    private SystemDictionaryitemClient systemDictionaryitemClient;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private CourseResourceMapper courseResourceMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private CourseClient courseClient;

    @Override
    public PageList<Course> pageByQuery(CourseQuery query) {
        //传统分页和高级查询
        /*Long total = baseMapper.count(query);
        List<Course> rows = baseMapper.selectByQuery(query);
        return new PageList<Course>(total,rows);*/


        //mybatis plus 的分页插件与高级查询
        IPage<Course> mypage = baseMapper.mypage(new Page(query.getPage(), query.getRows()), query);
        return new PageList<>(mypage.getTotal(),mypage.getRecords());

    }

    //上线
    @Override
    @Transactional
    public void online(List<Long> ids) {
        //修改数据库状态
        baseMapper.online(ids,System.currentTimeMillis());
        //更具id到数据库里查询数据
        List<Course> courses = baseMapper.selectBatchIds(ids);
        //调用es服务的接口
        List<CourseDocument> courseDocument = coursesToDocs(courses);
        courseClient.creat(courseDocument);
    }

    //下线
    @Override
    public void offline(List<Long> ids) {
        baseMapper.offline(ids,System.currentTimeMillis());
        courseClient.delete(ids);

    }


    //这个方法用来将List<Course> 转为List<CourseDocument>
    private List<CourseDocument> coursesToDocs(List<Course> courses) {
        List<CourseDocument> list = new ArrayList<>();
        CourseDocument courseDocument=null;
        for (Course cours : courses) {
            //对象互转
            courseDocument = courseToDoc(cours);
            list.add(courseDocument);
        }
        return list;

    }

    private CourseDocument courseToDoc(Course c){
        CourseDocument cd = new CourseDocument();
        //设置属性
        BeanUtils.copyProperties(c,cd);  //从Course拷贝属性值到CourseDocument
        //把剩下没有的属性赋值
        //courseTypeName
        Long courseTypeId = c.getCourseTypeId();
        CourseType courseType = courseTypeMapper.selectById(courseTypeId);
        cd.setCourseTypeName(courseType.getName());
        //gradeid
        cd.setGradeId(c.getGrade());
        //gradeName
        Systemdictionaryitem systemdictionaryitem = systemDictionaryitemClient.get(c.getGrade());
        cd.setGradeName(systemdictionaryitem == null ? null:systemdictionaryitem.getName());
        //intro
        CourseDetail courseDetail = courseDetailMapper.selectOne(new QueryWrapper<CourseDetail>().eq("course_id", c.getId()));
        cd.setIntro(courseDetail==null? null:courseDetail.getIntro());
        //resources
        CourseResource courseResource = courseResourceMapper.selectOne(new QueryWrapper<CourseResource>().eq("course_id", c.getId()));
        cd.setResources(courseResource==null? null:courseResource.getResources());
        //
        CourseMarket courseMarket = courseMarketMapper.selectOne( new QueryWrapper<CourseMarket>() .eq("course_id", c.getId()) );
        //copyProperties(从哪里，复制到哪里去，忽略那些字段)
        BeanUtils.copyProperties(courseMarket==null? new CourseMarket():courseMarket,cd,"id");
        //对
        String all = c.getTenantName()+" "+(courseType==null? "":courseType.getName())+" "+c.getName();
        cd.setAll(all);
        return cd;
    }

    @Override
    public PageList<CourseDocument> pageOnline(CourseQuery query) {
        CourseDocumentQuery courseDocumentQuery =  new CourseDocumentQuery();
        BeanUtils.copyProperties(query,courseDocumentQuery);
        return courseClient.searchIndexs(courseDocumentQuery);
    }


}
