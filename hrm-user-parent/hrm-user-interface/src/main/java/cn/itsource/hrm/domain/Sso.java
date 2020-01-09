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
 * 会员登录账号
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sso")
public class Sso extends Model<Sso> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    @TableField("updateTime")
    private Long updateTime;

    /**
     * 三方登录名
     */
    @TableField("thirdUid")
    private String thirdUid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 用户状态
     */
    @TableField("bitState")
    private Long bitState;

    /**
     * 安全级别
     */
    @TableField("secLevel")
    private Integer secLevel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
