package com.comp354pjb.app.Model;

public class Cards {

    private String word;
    private boolean isRevealed;
    public cardType type;

    public Cards(){
        this.word = " ";
        isRevealed = false;
    }

    public Cards(String word) {
        this.word = word;
        this.type = cardType.RED;
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

    public cardType getType()
    {
        return type;
    }
    public void setType(cardType tp)
    {
        type = tp;
    }

}


