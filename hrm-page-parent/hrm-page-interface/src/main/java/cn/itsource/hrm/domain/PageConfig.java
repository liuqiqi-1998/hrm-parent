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
@TableName("t_page_config")
public class PageConfig extends Model<PageConfig> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String templateUrl;

    /**
     * redis中数据所对应key
     */
    private String dataKey;

    @TableField("physicalPath")
    private String physicalPath;

    /**
     * 文件系统类型
     */
    private Long dfsType;

    /**
     * 静态页面地址
     */
    private String pageUrl;

    private Long pageId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
