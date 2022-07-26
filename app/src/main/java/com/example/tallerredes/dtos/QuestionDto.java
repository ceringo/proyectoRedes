package com.example.tallerredes.dtos;

public class QuestionDto {
    String QuestionName;
    String Answer;

    public String getQuestionName() {
        return QuestionName;
    }

    public QuestionDto setQuestionName(String questionName) {
        QuestionName = questionName;
        return this;
    }

    public String getAnswer() {
        return Answer;
    }

    public QuestionDto setAnswer(String answer) {
        Answer = answer;
        return this;
    }

    @Override
    public String toString() {
        return "======================================" + '\n' + " PREGUNTA :" + '\t' + QuestionName + '\n' + " RESPUESTA:" + '\t' + Answer + '\n' + "======================================";
    }
}