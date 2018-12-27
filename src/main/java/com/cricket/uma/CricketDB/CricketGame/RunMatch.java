package com.cricket.uma.CricketDB.CricketGame;

import com.mongodb.*;

public class RunMatch {
    public static void main(String[] args) {
        CricketGame cricketGame = new CricketGame();
        cricketGame.setListener(listener);
        cricketGame.setDbListener(dbListener);
        cricketGame.startGame();
    }

    private static final MatchListener listener = new MatchListener() {
        @Override
        public void teamSetupCompleted(CricketTeam teamA, CricketTeam teamB) {
            System.out.println("Team Setup completed");
            System.out.println("Teams: " + teamA.getTeamName() + " " + teamB.getTeamName());
        }

        @Override
        public void tossCompleted(CricketTeam tossWinner) {
            System.out.println("Toss completed -- outcome = " + tossWinner.getTeamName());
        }

        @Override
        public void inningsCompleted(ScoreBoard inningScoreBoard, CricketGameResult currentResult) {
            System.out.println(currentResult.getCurrentInningTeam().getTeamName() + "-> innings completed with " + inningScoreBoard.getCurrentTotal());
        }

        public void matchResult(CricketGameResult currentResult, boolean tie) {
            if (tie) {
                System.out.println("Match Tie");

            } else {
                System.out.println(currentResult.getWinningTeam().getTeamName() + " won the match");
            }
        }
    };

    private static final DBListener dbListener = new DBListener() {
        @Override
        public void updatePlayerRuns(Client mongo,CricketGameResult currentResult, ScoreBoard inningScoreBoard) {

            DB db = mongo.getDB("cricket");
            DBCollection col = db.getCollection("team");
            DBObject query = new BasicDBObject("team", currentResult.getCurrentInningTeam().getTeamName());
            DBObject update = new BasicDBObject();
            update.put("$set", new BasicDBObject("runs", inningScoreBoard.getCurrentTotal()));
            WriteResult result = col.update(query, update);
           // String ds="{'team':'"+currentResult.getCurrentInningTeam().getTeamName()+"','players':{'name':'t1p1','runs':'"+inningScoreBoard.getCurrentTotal()+"'}}";
           // System.out.println(ds);
            //col.update(query,)
        }


    @Override
    public void queryResult(Client mongo,CricketGameResult finalResult) {

        DB db = mongo.getDB("cricket");
        DBCollection col = db.getCollection("team");
        DBObject query = new BasicDBObject("team", finalResult.getWinningTeam().getTeamName());
        DBCursor data = col.find(query);
        System.out.println(data.next());
        mongo.close();
    }

    };
}
