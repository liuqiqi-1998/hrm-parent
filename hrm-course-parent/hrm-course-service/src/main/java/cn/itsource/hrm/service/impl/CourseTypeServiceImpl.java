package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.client.StaticPageClient;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {
    @Autowired
    private RedisClient redisClient;

    private final String COURSE_TYPE = "hrm:course_type:treeData";

    @Autowired
    private StaticPageClient pageClient;

    @Override
    public boolean save(CourseType entity) {
        super.save(entity);
        synOperate();
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        super.removeById(id);
        synOperate();
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        super.updateById(entity);
        synOperate();
        return true;
    }

    @Override
    public List<CourseType> loadTreeData() {
        /*List<CourseType> courseTypes = getByParentId(0L);*/
        /*List<CourseType> courseTypes = loadTreeDataLoop();*/
        //从redis中查询数据
        String courseTypesStr = redisClient.get(COURSE_TYPE);
        List<CourseType> list = null;

        //如果redis不存在，使用双重校验第一次redis不存在的时候查询数据库
        if (StringUtils.isEmpty(courseTypesStr)) {
            //查询数据库，防止缓存穿透，大量请求同时查询数据库，同步代码块
            synchronized (CourseTypeServiceImpl.class) {
                courseTypesStr = redisClient.get(COURSE_TYPE);
                if (StringUtils.isEmpty(courseTypesStr)) {
                    //如果不存在，则查询数据库
                    list = loadTreeDataLoop();
                    //list集合转json字符串
                    String jsonStr = JSONObject.toJSONString(list);
                    //保存到redis中
                    redisClient.set(COURSE_TYPE, jsonStr);
                    return list;
                }
            }
        }
        //如果存在
        //json字符串转java集合
        list = JSONObject.parseArray(courseTypesStr, CourseType.class);
        return list;
    }

    @Override
    public void staticCoursePage(Long pageId) {
        //查询数据放到redis里
        List<CourseType> list = loadTreeDataLoop();
        String jsonString = JSONObject.toJSONString(list);
        String dataKey = "page:"+pageId+":courseTypes";
        redisClient.set(dataKey,jsonString);
        //调用页面静态化化的接口
        pageClient.staticPages(dataKey,pageId);
    }


    /**
     * 循环方式 - 二
     *
     * @return
     */
    public List<CourseType> loadTreeDataLoop() {
        //初始化一个集合存放一级类型
        List<CourseType> firstLevelTypes = new ArrayList<>();
        //查询数据库中的所有类型
        List<CourseType> courseTypes = baseMapper.selectList(null);

        //创建一个Map，将courseTypes数据存到map中，key使用id，value就是CourseType
        Map<Long, CourseType> map = new HashMap<>();
        for (CourseType courseType : courseTypes) {
            map.put(courseType.getId(), courseType);
        }

        //循环courseTypes，分配一级类型和非一级类型
        for (CourseType courseType : courseTypes) {
            if (courseType.getPid().longValue() == 0L) {
                firstLevelTypes.add(courseType);
            } else {
                CourseType parent = map.get(courseType.getPid());
                if (parent != null) {
                    parent.getChildren().add(courseType);
                }
            }
        }

        return firstLevelTypes;
    }

    /**
     * 循环方式
     *
     * @return
     */
    public List<CourseType> loadTreeDataLoop_1() {

        //初始化一个集合存放一级类型
        List<CourseType> firstLevelTypes = new ArrayList<>();
        //查询数据库中的所有类型
        List<CourseType> courseTypes = baseMapper.selectList(null);

        for (CourseType courseType : courseTypes) {
            //如果是一级类型，直接放入到firstLevelTypes
            if (courseType.getPid() == 0L) {
                firstLevelTypes.add(courseType);
            } else {
                //如果不是，找父类型，放入父类型的children集合中
                for (CourseType parent : courseTypes) {
                    if (courseType.getPid().longValue() == parent.getId().longValue()) {
                        parent.getChildren().add(courseType);
                    }
                }
            }
        }
        return firstLevelTypes;
    }


    /**
     * 根据父id递归查询课程类型
     *
     * @param pid
     * @return
     */
    public List<CourseType> getByParentId(Long pid) {

        List<CourseType> children = baseMapper.selectList(
                new QueryWrapper<CourseType>()
                        .eq("pid", pid)
        );
        //递归的出口
        if (children == null || children.size() == 0) {
            return null;
        }
        for (CourseType child : children) {
            List<CourseType> childs = getByParentId(child.getId());
            child.setChildren(childs);
        }
        return children;

    }

    /**
     * 增删改同步操作
     */
    private void synOperate() {
        List<CourseType> list = loadTreeDataLoop();
        //list集合转json字符串
        String jsonStr = JSONObject.toJSONString(list);
        //保存到redis中
        redisClient.set(COURSE_TYPE, jsonStr);
        //重新生成课程首页静态页面
        staticCoursePage(9L);
    }

}
