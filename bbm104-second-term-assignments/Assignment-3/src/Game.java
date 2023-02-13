import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {
    int PlayerPoint;
    int flag = 1;
    int mapHorizontalLength;
    int mapVerticalLength;
    ArrayList<int[]> PositionsToRemove = new ArrayList<>();
    ArrayList<ArrayList<Jewel>> map;
    ArrayList<Player> leaderboard;
    public Game(ArrayList<ArrayList<Jewel>> map, ArrayList<Player> leaderboard){

        this.map = map;
        this.leaderboard = leaderboard;
        mapHorizontalLength = map.get(0).size();
        mapVerticalLength = map.size();
    }
    public <E extends Jewel> void round(Jewel jewel, int[] position){
        int roundPoint = 0;
        for(int[] directions: jewel.getDirectionsToSearch()){
            flag = 1;
            searcher(jewel, directions[0], directions[1], position, 0);
            if (flag == 1){
                PositionsToRemove.add(position);

                roundPoint = remover();
                break;
            }
        }
        reshapeMap();
        WriteOutput.writeMap(map);
        WriteOutput.AddText("Score: " + roundPoint + " points\n\n");
        PlayerPoint += roundPoint;

    }
    public <E extends Jewel> void searcher(E jewel, int verticalDirection, int horizontalDirection, int[] position, int counter){
        try {
            int[] positionToCheck = {position[0] + verticalDirection, position[1] + horizontalDirection};
            Jewel jewelToCheck = map.get(positionToCheck[0]).get(positionToCheck[1]);

            if ((Arrays.asList(jewel.getMatchingJewels())).contains(jewelToCheck.toString())) {
                PositionsToRemove.add(positionToCheck);
            } else {
                flag = 0;
                PositionsToRemove.clear();
                return;
            }
            if (counter == 0) {
                searcher(jewelToCheck, verticalDirection, horizontalDirection, positionToCheck, counter+1);
            }
        } catch(Exception e){
            flag = 0;
            PositionsToRemove.clear();
        }




    }
    public int remover(){
        int point = 0;
        for (int[] position : PositionsToRemove){
            point += map.get(position[0]).get(position[1]).getPoint();
            map.get(position[0]).set(position[1], new Empty(" "));
        }
        PositionsToRemove.clear();

        return point;
    }

    public void reshapeMap(){
        ArrayList<Jewel> tempColumn = new ArrayList<>();
        for (int ColumnIndex = 0; ColumnIndex < mapHorizontalLength; ColumnIndex++){
            for (int RowIndex = 0; RowIndex < mapVerticalLength; RowIndex++){
                Jewel JewelToCheck = map.get(RowIndex).get(ColumnIndex);
                if ( JewelToCheck.toString().equals(" ")){
                    tempColumn.add(0, new Empty(" "));
                } else {
                    tempColumn.add(JewelToCheck);
                }
                for (int i = 0; i < tempColumn.size(); i++){
                    map.get(i).set(ColumnIndex, tempColumn.get(i));
                }

            }
            tempColumn.clear();
        }
    }
    public void endGame(String playerName) throws IOException {
        String text;
        int playerRank;
        Player player = new Player(playerName, PlayerPoint);
        leaderboard.add(player);
        WriteOutput.writeLeaderboard(leaderboard);
        Collections.sort(leaderboard);
        Collections.reverse(leaderboard);
        playerRank = leaderboard.indexOf(player);
        WriteOutput.AddText("Total score: " + PlayerPoint + " points\n\n");
        WriteOutput.AddText("Enter name: " + playerName + "\n\n");
        if (playerRank == 0){
            Player nextPlayer = leaderboard.get(playerRank + 1);
            WriteOutput.AddText("Your rank is " +  (playerRank +1) + "/" + leaderboard.size() + ", your score is " + (player.getPoint() - nextPlayer.getPoint()) +  " points higher than " + nextPlayer.getName() + "\n\n");
        } else if (playerRank == leaderboard.size() - 1){
            Player previousPlayer = leaderboard.get(playerRank - 1);
            WriteOutput.AddText("Your rank is " +  (playerRank +1) + "/" + leaderboard.size() + ", your score is " + (previousPlayer.getPoint() - player.getPoint()) +  " points lower than " + previousPlayer.getName() + "\n\n");
        } else {
            Player nextPlayer = leaderboard.get(playerRank + 1);
            Player previousPlayer = leaderboard.get(playerRank - 1);
            WriteOutput.AddText("Your rank is " +  (playerRank +1) + "/" + leaderboard.size() + ", your score is "  + (previousPlayer.getPoint() - player.getPoint()) +  " points lower than " + previousPlayer.getName() + " and " + (player.getPoint() - nextPlayer.getPoint()) +  " points higher than " + nextPlayer.getName() + "\n\n");

        }

        WriteOutput.AddText("Good bye!");
    }
}
