import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInput {
    private ArrayList<String> commands = new ArrayList<>();
    private ArrayList<ArrayList<Jewel>> map = new ArrayList<>();
    private ArrayList<Player> leaderboard = new ArrayList<>();

    public ArrayList<Player> getLeaderboard() {
        return leaderboard;
    }

    public ArrayList<ArrayList<Jewel>> getMap() {
        return map;}
    public ArrayList<String> getCommands() {
        return commands;
    }

    private void createMap(String map_file_name) throws IOException {
        String line;
        FileReader map_file = new FileReader(map_file_name);
        BufferedReader reader = new BufferedReader(map_file);
        while ((line = reader.readLine()) != null){
            ArrayList<Jewel> objects_line = new ArrayList<>();
            String[] splitted_line = line.split(" ");
            for (String string : splitted_line){
                switch (string){
                    case "D": objects_line.add(new Diamond("D", 30));
                    break;
                    case "S": objects_line.add(new Square("S", 15));
                    break;
                    case "T": objects_line.add(new Triangle("T", 15));
                    break;
                    case "W": objects_line.add(new Wildcard("W", 10));
                    break;
                    case "/": objects_line.add(new Slash("/", 20));
                    break;
                    case "-": objects_line.add(new Dash("-", 20));
                    break;
                    case "+": objects_line.add(new Plus("+", 20));
                    break;
                    case "\\": objects_line.add(new Backslash("\\", 20));
                    break;
                    case "|": objects_line.add(new Bar("|", 20));
                    break;
                }
            }
            map.add(objects_line);


        }
    }

    private void readCommands(String command_file_name) throws IOException {
        String line;
        FileReader command_file = new FileReader(command_file_name);
        BufferedReader reader = new BufferedReader(command_file);
        while ((line = reader.readLine()) != null) {
            commands.add(line);
        }
    }
private void readLeaderboard(String leaderboard_file_name) throws IOException{
    String line;
    FileReader command_file = new FileReader(leaderboard_file_name);
    BufferedReader reader = new BufferedReader(command_file);
    while ((line = reader.readLine()) != null) {
        String[] splittedLine = line.split(" ");
        leaderboard.add(new Player(splittedLine[0], Integer.parseInt(splittedLine[1])));
    }

}

    public ReadInput(String map_file_name, String command_file_name) throws IOException {
        createMap(map_file_name);
        readCommands(command_file_name);
        readLeaderboard("leaderboard.txt");
    }
}
