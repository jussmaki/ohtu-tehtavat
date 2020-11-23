package ohtu;

public class TennisGame {
    
    private int player1Points = 0;
    private int player2Points = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName == player1Name)
            player1Points += 1;
        else
            player2Points += 1;
    }

    public String getScore() {
        if (player1Points==player2Points)
        {
            return scoreWhenGameIsATie(player1Points);
        }
        if (player1Points>=4 || player2Points>=4)
        {
            return scoreWhenOtherPlayerHaveEqualOrLessThanFourPoints(player1Points, player2Points);
        }
        return scoreInOtherCases(player1Points, player2Points);
    }
    
    private String scoreWhenGameIsATie(int score) {
        switch (score)
        {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            case 3:
                return "Forty-All";
            default://4
                return "Deuce";   
        }
    }

    private String scoreWhenOtherPlayerHaveEqualOrLessThanFourPoints(int player1Points, int player2Points) {
        int minusResult = player1Points-player2Points;
        if (minusResult==1) return "Advantage player1";
        else if (minusResult ==-1) return "Advantage player2";
        else if (minusResult>=2) return "Win for player1";
        return "Win for player2";
    }

    private String scoreInOtherCases(int player1Points, int player2Points) {
        return scoreForPoints(player1Points)+"-"+scoreForPoints(player2Points);

    }
    
    private String scoreForPoints(int points){
        switch(points)
        {
            case 0:
               return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            default://3
                return "Forty";
        }
    }
}