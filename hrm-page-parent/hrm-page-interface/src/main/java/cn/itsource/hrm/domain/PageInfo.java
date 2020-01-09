package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_page_info")
public class PageInfo extends Model<PageInfo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 英文名
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    private Long type;

    @TableField("physicalPath")
    private String physicalPath;

    @TableField("createTime")
    private Long createTime;

    private Long siteId;
    @TableField(exist = false)
    private Site site;

    /**
     * 模板在hdfs中的路径地址
     */
    private String templateUrl;
    //模板文件
    private String templateFile;


    @TableField(exist = false)
    private PageConfig pageConfig;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
