package cn.itsource.hrm.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.annotation.Documented;
import java.math.BigDecimal;

@Data
@Document(indexName = "hrm",type = "course")
public class CourseDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)  //keyword表示不分词
    private String name;
    @Field(type = FieldType.Keyword)
    private String users;
    @Field(type = FieldType.Long)
    private Long courseTypeId;
    @Field(type = FieldType.Keyword)
    private String courseTypeName;
    @Field(type = FieldType.Long)
    private Long gradeId;
    @Field(type = FieldType.Keyword)
    private String gradeName;
    @Field(type = FieldType.Integer)
    private Integer status;
    @Field(type = FieldType.Long)
    private Long tenantId;
    @Field(type = FieldType.Keyword)
    private String tenantName;
    @Field(type = FieldType.Long)
    private Long userId;
    @Field(type = FieldType.Keyword)
    private String userName;
    @Field(type = FieldType.Long)
    private Long startTime;
    @Field(type = FieldType.Long)
    private Long endTime;
    @Field(type = FieldType.Keyword)
    private String intro;
    @Field(type = FieldType.Keyword)
    private String resources;//图片
    @Field(type = FieldType.Long)
    private Long expires;//过期时间
    @Field(type = FieldType.Double)
    private BigDecimal priceOld;//原价
    @Field(type = FieldType.Double)
    private BigDecimal price;
    @Field(type = FieldType.Keyword)
    private String qq;
    //关键字搜索，把关键字搜索所有设计到的列 字符串拼接到一个all字段中
    //可以拼接  机构名称，课程类型名称，课程名称
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String all;

}
