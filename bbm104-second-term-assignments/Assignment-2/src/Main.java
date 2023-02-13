import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Main {


    public static void main(String[] args) throws IOException {
        String line;
        PropertyJsonReader property_reader = new PropertyJsonReader();
        ListJsonReader list_reader = new ListJsonReader();
        FileReader input_file = new FileReader(args[0]);
        BufferedReader reader = new BufferedReader(input_file);
        Player player_1 = new Player("Player 1");
        Player player_2 = new Player("Player 2");
        Banker banker = new Banker();
        Game game = new Game(player_1, player_2, banker);
        while ((line = reader.readLine()) != null) {
            if (line.equals("show()")) {
                Game.show(player_1, player_2, banker);
            } else {
                String[] splitted_line = line.split(";");
                int steps = Integer.parseInt(splitted_line[1]);
                Player player_to_play = (splitted_line[0].equals("Player 1")) ? player_1 : player_2;
                Player other_player = (splitted_line[0].equals("Player 1")) ? player_2 : player_1;
                game.round(player_to_play, other_player, banker, steps);
            }
            }
        game.end_game();
        }
    }
