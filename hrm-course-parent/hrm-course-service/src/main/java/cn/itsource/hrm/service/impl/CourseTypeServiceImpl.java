package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.RedisClient;
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
 * @since 2019-12-25
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private RedisClient redisClient;



    //便于数据的分类，设置不同的目录或者层级使用冒号
    public final String COURSE_TYPE = "hrm:courseType:treeData";

    @Override
    public List<CourseType> loadTreeData() {
        //从redis里面查询数据
        String str = redisClient.get(COURSE_TYPE);
        List<CourseType> list = null;
        //判断是否存在数据
        if(StringUtils.isNotEmpty(str)){
            //存在数据直接返回
            //字符串转集合对象
            list = JSONObject.parseArray(str,CourseType.class);
        }else {
            //不存在从数据库里查询了先存到redis里面，再返回
            //从数据库里查询数据
            list = getByPid03();
            //独显转成json字符串
            String jsonString = JSONObject.toJSONString(list);
            //以键值对的方式存储到redis缓存
            redisClient.set(COURSE_TYPE,jsonString);
        }
        return list;
    }

    @Override
    public List<CourseType> findChildById(Long id) {
        //一个子标题的集合
        List<CourseType> children = new ArrayList<>();
        //查询所有
        List<CourseType> allLevel = baseMapper.selectList(null);
        CourseType one = baseMapper.selectById(id);
        if(one != null){
            for (CourseType courseType : allLevel) {
                if(courseType.getPid().longValue() == one.getId()){
                    children.add(courseType);
                }
            }
        }
        return children;
    }

    /**
     * 同步方法
     * 增删改后同步redis的数据，保持和数据库同步
     */
    public void synchronization(){
        List<CourseType> list = getByPid03();
        //转成字符串
        String jsonString = JSONObject.toJSONString(list);
        //以键值对的方式存储到redis缓存
        redisClient.set(COURSE_TYPE,jsonString);

    }

    @Override
    public boolean save(CourseType entity) {
        super.save(entity);
        synchronization();
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        super.removeById(id);
        synchronization();
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        super.updateById(entity);
        synchronization();
        return true;
    }

    //递归的方式实现  (可读性较差，容易栈溢出)
    public List<CourseType> getByPid01(Long pid){
        //先拿到所有一级标题
        List<CourseType> children = baseMapper.selectList(
                new QueryWrapper<CourseType>().eq("pid",pid)
        );
        //递归的出口，递归必须有出口不然会栈溢出
        if( children==null || children.size()==0){
            return null;
        }
        //遍历所有标题
        for (CourseType child : children) {
            List<CourseType> childrens = getByPid01(child.getId());
            child.setChildren(childrens);
        }
        return children;
    }
    //循环实现（查询次数太多）
    public List<CourseType> getByPid02(){
        //定义一个集合存放一级标题
        List<CourseType> firstLevel = new ArrayList<>();
        //查询所有的标题
        List<CourseType> allLevel = baseMapper.selectList(null);
        //遍历所有的标题
        for (CourseType courseType : allLevel) {
            //入股是以及标题就存放再以及标题的集合中
            if(courseType.getPid()==0L){
                firstLevel.add(courseType);
            }else {
                //如果不是，就找到他的父级类型存放
                for (CourseType parent : allLevel) {
                    //得到的是对象，需要转成包装类型比较
                    if(courseType.getPid().longValue() == parent.getId().longValue()){
                        parent.getChildren().add(courseType);
                    }
                }
            }
        }
        return firstLevel;
    }

    //map+循环  （循环的优化，减少查询次数，提高效率）
    public List<CourseType> getByPid03(){
        //定义一个集合存放一级标题
        List<CourseType> firstLevel = new ArrayList<>();
        //查询所有的标题
        List<CourseType> allLevel = baseMapper.selectList(null);
        //创建一个map,把allLevel的数据放在里面，key是id，value就是CourseType
        Map<Long,CourseType> map = new HashMap<>();
        for (CourseType courseType : allLevel) {
            map.put(courseType.getId(),courseType);
        }
        //循环所有标题
        for (CourseType courseType : allLevel) {
            //判断如果该标题的的pid为0则为以及标题，放在一级标题的集合
            if(courseType.getPid().longValue() ==0L){
                firstLevel.add(courseType);
            }else {
                //如果不是一级标题，那么找到它的父级，把自己放在父级标题的children集合里面
                CourseType parent = map.get(courseType.getPid());
                if(parent!=null){
                    parent.getChildren().add(courseType);
                }
            }
        }

        return firstLevel;
    }

}
