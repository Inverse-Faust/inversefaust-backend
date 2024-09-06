package com.inversefaust.backend.inversefaust_backend.dto;


public class AIAdviceResponse {

    private String userId;
    private int whiteScore;
    private int blackScore;
    private String diarySentiment;
    private int diaryScore;
    private String diaryAdvice;

    // Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public void setWhiteScore(int whiteScore) {
        this.whiteScore = whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public void setBlackScore(int blackScore) {
        this.blackScore = blackScore;
    }

    public String getDiarySentiment() {
        return diarySentiment;
    }

    public void setDiarySentiment(String diarySentiment) {
        this.diarySentiment = diarySentiment;
    }

    public int getDiaryScore() {
        return diaryScore;
    }

    public void setDiaryScore(int diaryScore) {
        this.diaryScore = diaryScore;
    }

    public String getDiaryAdvice() {
        return diaryAdvice;
    }

    public void setDiaryAdvice(String diaryAdvice) {
        this.diaryAdvice = diaryAdvice;
    }
}