package com.clin.api.vo;

import com.clin.core.vo.CommonVO;

import java.io.Serializable;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-08
 **/
public class ProDataVO  extends CommonVO implements Serializable {

    private String name;
    private int sex;
    private String telephone;
    private String agestart;
    private String ageend;
    private String status;
    private String username;
    private String scorestart;
    private String scoreend;
    private String roleCode;
    private long userId;

    private String sort;
    private String sequence;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAgestart() {
        return agestart;
    }

    public void setAgestart(String agestart) {
        this.agestart = agestart;
    }

    public String getAgeend() {
        return ageend;
    }

    public void setAgeend(String ageend) {
        this.ageend = ageend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScorestart() {
        return scorestart;
    }

    public void setScorestart(String scorestart) {
        this.scorestart = scorestart;
    }

    public String getScoreend() {
        return scoreend;
    }

    public void setScoreend(String scoreend) {
        this.scoreend = scoreend;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


}
