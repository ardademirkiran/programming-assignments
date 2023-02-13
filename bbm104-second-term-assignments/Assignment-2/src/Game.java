import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.System.exit;

public class Game {
    Player player_1;
    Player player_2;
    Banker banker;
    public Game(Player player_1, Player player_2, Banker banker){
        this.player_1 = player_1;
        this.player_2 = player_2;
        this.banker = banker;

    }
    public static boolean includes(int[] array, int num_to_check) {
        for (int x : array) {
            if (x == num_to_check) {
                return true;
            }
        }
        return false;
    }
    public static void show(Player player_1, Player player_2, Banker banker){
        Player winner = (player_1.money >= player_2.money) ? player_1:player_2;
        WriteOutput.add_seperator();
        WriteOutput.add_text(player_1.getPlayer_text() + "\t" + player_1.money + "\thave: " + String.join(",", player_1.properties_names) + "\n");
        WriteOutput.add_text(player_2.getPlayer_text() + "\t" + player_2.money + "\thave: " + String.join(",", player_2.properties_names) + "\n");
        WriteOutput.add_text("Banker\t" + banker.money + "\n");
        WriteOutput.add_text("Winner\t" + winner.getPlayer_text() + "\n");
        WriteOutput.add_seperator();
    }


    public String square_process(Player player_to_play, Player other_player, int steps){
        String text = "";
        int square_id = player_to_play.current_position;
        int initial_position = player_to_play.current_position;
        int[] chest_ids = {3, 18, 34};
        int[] chance_ids = {8, 23, 37};
        int[] jail_ids = {11, 31};
        int[] tax_ids = {5, 39};
        if (includes(chance_ids, player_to_play.current_position)){
            text += player_to_play.getPlayer_text() + " draw " + Card.get_chance(player_to_play, banker);
        } else if (includes(chest_ids, player_to_play.current_position)){
            text += player_to_play.getPlayer_text() + " draw Community Chest -" + Card.get_chest(player_to_play, other_player, banker);
        } else if (includes(jail_ids, player_to_play.current_position)){
            jail_process(player_to_play);
            text += player_to_play.getPlayer_text() + " went to jail";
        } else if (includes(tax_ids, player_to_play.current_position)){
            player_to_play.pay(banker, 100);
            text += player_to_play.getPlayer_text() +" paid Tax";
        } else if (player_to_play.current_position == 21){
            free_parking_process(player_to_play);
            text += player_to_play.getPlayer_text() +" is in Free Parking";
        } else if (player_to_play.current_position == 1){
            text += player_to_play.getPlayer_text() +" is in GO square";
        } else {
            text += player_to_play.getPlayer_text() + property_process(player_to_play, steps);
        }
        if (initial_position != player_to_play.current_position && includes(jail_ids, player_to_play.current_position) == false){
            text += " " + square_process(player_to_play, other_player, steps);
        }
        return text;
    }
    public void end_game() throws IOException {
        show(player_1, player_2, banker);
        WriteOutput.write_to_file();
        System.exit(0);
    }
    public String check_game(Player player_1, Player player_2){
        String text = "";
        if (player_1.money <= 0){
            text += player_1.getPlayer_text() + " goes bankrupt";
        } else if (player_2.money <= 0){
            text += player_2.getPlayer_text() + " goes bankrupt";
            }
        return text;
        }

    public void round(Player player_to_play,Player other_player,Banker banker, int steps) throws IOException {
        String text = "";
        if (player_to_play.jail_count != 0){
            player_to_play.jail_count-=1;
           WriteOutput.add_text(player_to_play.getPlayer_text() + "\t" + steps + "\t11\t" + player_1.money + "\t" + player_2.money + "\t" + player_to_play.getPlayer_text() + " in jail (count=" + (3 - player_to_play.jail_count) + ")\n" );
        } else if (player_to_play.parking_count != 0) {
            player_to_play.parking_count -= 1;
            WriteOutput.add_text(player_to_play.getPlayer_text() + "\t" + steps + "\t21\t" + player_1.money + "\t" + player_2.money + "\t" + player_to_play.getPlayer_text() + " is in Free Parking.\n" );
        } else {
            player_to_play.move(steps, banker);
            WriteOutput.add_text(player_to_play.getPlayer_text() + "\t" + steps + "\t");
            text = square_process(player_to_play, other_player, steps);
            WriteOutput.add_text(player_to_play.current_position + "\t" + player_1.money + "\t" + player_2.money + "\t");
            WriteOutput.add_text(text + "\n");
            if (check_game(player_1, player_2).equals("") == false) {
                WriteOutput.add_text(player_to_play.getPlayer_text() + "\t" + steps + "\t");
                WriteOutput.add_text(player_to_play.current_position + "\t" + player_1.money + "\t" + player_2.money + "\t");
                text = check_game(player_1, player_2);
                WriteOutput.add_text(text + "\n");
                end_game();
            }
        }
    }

    public String property_process(Player player, int dice){
        String text = "";
        int property_id = player.current_position;
        Square property = PropertyJsonReader.squares.get(PropertyJsonReader.square_ids.indexOf(property_id));
        if (property.check_property() == 1){
            player.pay(banker, property.cost);
            player.buy(property);
            property.owner = player;
            text += " bought " + property.name;
        } else {
            if (property.owner != player){
            double cost = property.calculate_cost(dice);
            player.pay(property.owner, cost);
            text += " paid rent for " + property.name;
            } else {
               text += " has " + property.name;
            }
        }
        return text;
    }
    public static void jail_process(Player player){
        player.current_position = 11;
        player.jail_count = 3;
    }
    public static void free_parking_process(Player player){
        player.parking_count = 1;
    }
}
