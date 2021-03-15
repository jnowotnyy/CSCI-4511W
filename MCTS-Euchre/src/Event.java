public enum Event {
  DEAL,
  CUT, //optional
  PLAY,
  TRICKWIN, //an individual trick is won
  TRUMPCARD, //
  DEALERWIN, //dealer wins 3 or 4 tricks (1 pt)
  EUCHERED, //defender wins 3 or 4 tricks (2 pts)
  DEALERSWEEP, // dealer wins all 5 tricks
  DEFENDERSWEEP, //defender wins all 5 tricks
  WIN //first player to score 10 pts
}
