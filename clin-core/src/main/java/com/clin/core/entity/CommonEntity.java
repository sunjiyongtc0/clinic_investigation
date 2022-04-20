package com.clin.core.entity;



import java.io.Serializable;

/**
 * <pre>
 * Title :  实体类共用字段
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-02-28
 **/
public class CommonEntity extends TimeEntity implements Serializable {

    private static final long serialVersionUID = 6272491356994391729L;
    private Long parentId;// 上级ID

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
