public enum CardSuit {
    Spades, Clubs, Hearts, Diamonds;
    public String toString() {
        String newStr = (this == Diamonds) ? "\u2666" : (this == Hearts) ? "\u2665" : (this == Clubs) ? "\u2663" : (this == Spades) ? "\u2660" : null;
        if (newStr == null) { throw new IllegalStateException();}
        return newStr;
    }
}
