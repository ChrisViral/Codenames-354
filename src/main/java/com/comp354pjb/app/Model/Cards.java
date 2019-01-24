package com.comp354pjb.app.Model;

public class Cards {

    private String word;
    private boolean isRevealed;
    //type can be red, blue, civ, ass
    private String type;

    public Cards(){
        this.word = " ";
        isRevealed = false;
    }

    public Cards(String word, String type) {
        this.word = word;
        this.type = type;
        this.isRevealed = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}


