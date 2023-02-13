import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteOutput {
    static String text = "";
    public static void writeMap(ArrayList<ArrayList<Jewel>> map){
        for(ArrayList<Jewel> line : map){
            for(Jewel jewel : line) {
                text += jewel + " ";
            }
            text += "\n";
        }
        text+=("\n");
    }
    public static void AddText(String textToAdd){
        text+= textToAdd;
    }
    public static void writeText() throws IOException {
        FileWriter outputFile = new FileWriter("monitoring.txt");
        BufferedWriter writer = new BufferedWriter(outputFile);
        writer.write(text);
        writer.close();
    }
    public static void writeLeaderboard(ArrayList<Player> leaderboard) throws IOException {
        FileWriter outputFile = new FileWriter("leaderboard.txt");
        BufferedWriter writer = new BufferedWriter(outputFile);
        for(Player player : leaderboard){
            writer.write(player.toString() + "\n");
        }
        writer.close();
    }
}
