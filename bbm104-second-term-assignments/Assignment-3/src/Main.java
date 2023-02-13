import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ReadInput inputReader = new ReadInput(args[0], args[1]);
        ArrayList<ArrayList<Jewel>> map = inputReader.getMap();
        ArrayList<String> commands = inputReader.getCommands();
        ArrayList<Player> leaderboard = inputReader.getLeaderboard();
        Game game = new Game(map, leaderboard);
        WriteOutput.AddText("Game grid:\n\n");
        WriteOutput.writeMap(map);
        for (int i = 0; i < commands.size(); i++){
            try {
                String command = commands.get(i);
                if (command.equals("E")) {
                    WriteOutput.AddText("Select coordinate or enter E to end the game: E\n\n");
                    String playerName = commands.get(i + 1);
                    game.endGame(playerName);
                    WriteOutput.writeText();
                    System.exit(0);
                } else {
                    WriteOutput.AddText("Select coordinate or enter E to end the game: " + command + "\n\n");
                    String[] splittedCommand = command.split(" ");
                    int[] position = {Integer.parseInt(splittedCommand[0]), Integer.parseInt(splittedCommand[1])};
                    game.round(map.get(position[0]).get(position[1]), position);
                }
            } catch (Exception e){
                WriteOutput.AddText("Please enter a valid coordinate\n\n");
            }


        }
    }
}
