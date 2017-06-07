package com.app.justclap.models;

import java.util.ArrayList;

/**
 * Created by admin on 20-03-2016.
 */
public class ModelCheckboxType {

    String questionID;
    String questionText;
    String isMandatory;
    String questionType;
    String input_type;
    String placeholder;
    String questionTypeID;
    ArrayList<String> optionId=new ArrayList<String>();
    ArrayList<String> optionArray=new ArrayList<String>();
    ArrayList<Boolean> optionSelectionArray=new ArrayList<Boolean>();


    public ArrayList<String> getOptionId() {
        return optionId;
    }

    public void setOptionId(ArrayList<String> optionId) {
        this.optionId = optionId;
    }



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

    public String getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(String questionTypeID) {
        this.questionTypeID = questionTypeID;
    }

    public ArrayList<String> getOptionArray() {
        return optionArray;
    }

    public void setOptionArray(ArrayList<String> optionArray) {
        this.optionArray = optionArray;
    }

    public ArrayList<Boolean> getOptionSelectionArray() {
        return optionSelectionArray;
    }

    public void setOptionSelectionArray(ArrayList<Boolean> optionSelectionArray) {
        this.optionSelectionArray = optionSelectionArray;
    }


}
