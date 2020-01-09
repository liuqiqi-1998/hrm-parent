package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.document.CourseDocument;
import cn.itsource.hrm.service.ICourseService;
import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    public ICourseService courseService;

    /**
    * 保存和修改公用的
    * @param course  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Course course){
        try {
            if(course.getId()!=null){
                courseService.updateById(course);
            }else{
                courseService.save(course);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            courseService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Course get(@PathVariable("id")Long id)
    {
        return courseService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Course> list(){

        return courseService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<Course> page(@RequestBody CourseQuery query)
    {
        return courseService.pageByQuery(query);
       /* Page<Course> page = courseService.page(new Page<Course>(query.getPage(), query.getRows()));
        return new PageList<>(page.getTotal(),page.getRecords());*/

    }

    //添加上线和下线的接口
    @PostMapping("/online")
    public AjaxResult online(@RequestBody List<Long> ids){
        try {
            courseService.online(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败了！");
        }

    }

    //下线
    @PostMapping("/offline")
    public AjaxResult offline(@RequestBody List<Long> ids){
        try {
            courseService.offline(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败了！");
        }

    }

    @PostMapping("/pageOnline")
    public PageList<CourseDocument> pageOnline(@RequestBody CourseQuery query){
        return courseService.pageOnline(query);
    }

   /* @PostMapping("/addPic")
    public AjaxResult addPic(Long courseId){
        try {
            courseService.addPic(courseId);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败！");
        }
    }*/


}
