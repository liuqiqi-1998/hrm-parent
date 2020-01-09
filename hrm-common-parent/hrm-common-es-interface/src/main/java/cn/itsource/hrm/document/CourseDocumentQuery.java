package cn.itsource.hrm.document;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class CourseDocumentQuery {
    private Integer page;
    private Integer rows;
    private String  keyword;
    private Long courseTypeId;
    private Double minPrice;
    private Double maxPrice;

    private String orderField;
    private Integer orderType;




}
