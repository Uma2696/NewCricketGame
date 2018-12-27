package com.cricket.uma.CricketDB.CricketGame;

public interface DBListener {

   public void updatePlayerRuns(Client mongo,CricketGameResult currentResult,ScoreBoard inningsScoreBoard);
   public void queryResult(Client mongo,CricketGameResult finalResult);
}
