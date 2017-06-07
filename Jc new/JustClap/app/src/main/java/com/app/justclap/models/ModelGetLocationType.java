package com.app.justclap.models;

/**
 * Created by admin on 20-03-2016.
 */
public class ModelGetLocationType {

    private String questionText;
    private String isMandatory;
    private String questionType;
    private String input_type;
    private String placeholder;
    private String questionTypeID;

    private String address="";
    private String latitude="";
    private String longitude="";

    private String questionID;

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getInput_type() {
        return input_type;
    }

    public void setInput_type(String input_type) {
        this.input_type = input_type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    public String getQuestionImageUrl() {
        return QuestionImageUrl;
    }

    public void setQuestionImageUrl(String questionImageUrl) {
        QuestionImageUrl = questionImageUrl;
    }

    String QuestionImageUrl;
    public String getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(String questionTypeID) {
        this.questionTypeID = questionTypeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
