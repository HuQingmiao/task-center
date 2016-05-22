package com.github.walker.taskcenter.vo;


import com.github.walker.common.BasicVo;

/**
 * 系统编码库，即数据字典
 */
public class SysCodes extends BasicVo {
    private static final long serialVersionUID = 1L;

    private Long id ;
    private String kind ;
    private String kindDesc ;
    private String code ;
    private String codeDesc ;
    private Integer orderBy ;

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getKind() {
        return kind;
    }
    public void setKind(String kind){
        this.kind = kind;
    }

    public String getKindDesc() {
        return kindDesc;
    }
    public void setKindDesc(String kindDesc){
        this.kindDesc = kindDesc;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code){
        this.code = code;
    }

    public String getCodeDesc() {
        return codeDesc;
    }
    public void setCodeDesc(String codeDesc){
        this.codeDesc = codeDesc;
    }

    public Integer getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

}
/*将各字段列如下:
"id", "kind", "kind_desc", "code", "code_desc", "order_by"
*/