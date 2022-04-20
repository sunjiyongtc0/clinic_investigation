package com.clin.api.vo;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-10
 **/
public class QuestionSetting  implements Serializable {

    private String questionName;

    private String pdcItems;

    private JSONArray questionTitle;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getPdcItems() {
        return pdcItems;
    }

    public void setPdcItems(String pdcItems) {
        this.pdcItems = pdcItems;
    }

    public JSONArray getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(JSONArray questionTitle) {
        this.questionTitle = questionTitle;
    }

    @Override
    public String toString() {
        return "QuestionSetting{" +
                "questionName='" + questionName + '\'' +
                ", pdcItems='" + pdcItems + '\'' +
                ", questionTitle=" + questionTitle +
                '}';
    }
}

