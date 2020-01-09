package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.FileUtil;
import cn.itsource.hrm.client.FileClient;
import cn.itsource.hrm.client.PageInfoClient;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.domain.PageInfo;
import cn.itsource.hrm.util.VelocityUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PageController {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private PageInfoClient pageInfoClient;
    @Autowired
    private FileClient fileClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;




    //页面静态化的接口
    @PostMapping("/staticPage")
    public AjaxResult staticPages(@RequestParam("dataKey") String dataKey, @RequestParam("pageId") Long pageId)  {
        //从redis中获取数据
        String datastr = redisClient.get(dataKey);  //redis通过key查找到数据
        JSONArray data = JSONObject.parseArray(datastr); //转成json数组
        //下载模板
        PageInfo pageInfo = pageInfoClient.get(pageId);  //通过pageId获取到PageInfo对象
        String templateUrl = pageInfo.getTemplateUrl(); //得到模板的url和模板文件的名字
        String templateFile = pageInfo.getTemplateFile();
        //feign下载文件
        Response response = fileClient.download(templateUrl);
        Response.Body body = response.body();
        String templatePath = "D:/JavaLearn/temp/template.zip";
        String templateDir = "D:/JavaLearn/temp/";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = body.asInputStream();
            //下载到这里
            outputStream =new FileOutputStream(new File(templatePath));
            //下载
            IOUtils.copy(inputStream,outputStream);  //实现文件输出，把输入流的东西拷贝到输出流中去，相当于保存到本地
            //解压  unZipFiles(File,String)  把文件解压到哪里去
            FileUtil.unZipFiles(new File(templatePath),templateDir);

            //静态化页面  模板+数据
            Map<String,Object> map = new HashMap<>();
            map.put("staticRoot",templateDir);      //模板的根目录
            map.put("courseTypes",data);        //模板的数据集合
            String FilePathAndName = templateDir + templateFile;
            String targetFilePathAndName = "D:/JavaLearn/IdeaWorkSpace/ecommerce/home.html";
            VelocityUtils.staticByTemplate(map,FilePathAndName,targetFilePathAndName);
            System.out.println("到这里，静态化成功啦！");

            //上传到fastdfs
            FileItem item = createFileItem(new File(targetFilePathAndName));
            MultipartFile multipartFile = new CommonsMultipartFile(item);
            AjaxResult ajaxResult = fileClient.feignUpload(multipartFile);  //feign的上传
            String fileId = ajaxResult.getResultObj().toString();


            //发送消息到MQ
            Map<String,Object> msg = new HashMap<>();
            msg.put("pageId", pageId);
            msg.put("fileId", fileId);
            msg.put("physicalPath",pageInfo.getPhysicalPath());
            String jsonString = JSONObject.toJSONString(msg);
            rabbitTemplate.convertAndSend("hrm-course",jsonString);
            return AjaxResult.me();

        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败了！");
        }
    }
    private FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        FileItem item = factory.createItem("file", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
