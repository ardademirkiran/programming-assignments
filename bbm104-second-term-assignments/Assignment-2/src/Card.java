import java.util.ArrayList;
import java.util.Objects;

public class Card {
    static int next_chance_index = 0;
    static int next_chest_index = 0;
    static ArrayList<String> chance_list = new ArrayList<>();
    static ArrayList<String> chest_list = new ArrayList<>();

    public static String get_chance(Player player, Banker banker){
        String chance_string = chance_list.get(next_chance_index);
        if (Objects.equals(chance_string, "Advance to Go (Collect $200)")){ player.move(41 - player.current_position, banker);}
        else if (Objects.equals(chance_string, "Advance to Leicester Square")) { player.move((40 - player.current_position + 27) % 40, banker);}
        else if(Objects.equals(chance_string, "Go back 3 spaces")){player.move(-3, banker);}
        else if(Objects.equals(chance_string, "Pay poor tax of $15")){player.pay(banker, 15);}
        else if(Objects.equals(chance_string, "Your building loan matures - collect $150")){banker.pay(player, 150);}
        else if(Objects.equals(chance_string, "You have won a crossword competition - collect $100 ")){banker.pay(player,100);}
        next_chance_index = (next_chance_index + 1) % 6;
        return chance_string;
    }

    public static String get_chest(Player player, Player player_2, Banker banker){
        String chest_string = chest_list.get(next_chest_index);
        if (Objects.equals(chest_string, "Advance to Go (Collect $200)")){ player.move(41 - player.current_position, banker);}
        else if (Objects.equals(chest_string, "Bank error in your favor - collect $75")) {banker.pay(player, 75);}
        else if (Objects.equals(chest_string, "Doctor's fees - Pay $50")) {player.pay(banker, 50);}
        else if (Objects.equals(chest_string, "It is your birthday Collect $10 from each player")) {player_2.pay(player, 10);}
        else if (Objects.equals(chest_string, "Grand Opera Night - collect $50 from every player for opening night seats")) {player_2.pay(player, 50);}
        else if (Objects.equals(chest_string, "Income Tax refund - collect $20")) {player_2.pay(player, 20);}
        else if (Objects.equals(chest_string, "Life Insurance Matures - collect $100")) {banker.pay(player, 20);}
        else if (Objects.equals(chest_string, "Pay Hospital Fees of $100")) {player.pay(banker, 20);}
        else if (Objects.equals(chest_string, "Pay School Fees of $50")) {player.pay(banker, 20);}
        else if (Objects.equals(chest_string, "You inherit $100")) {banker.pay(player, 100);}
        else if (Objects.equals(chest_string, "From sale of stock you get $50")) {banker.pay(player, 50);}
        next_chest_index = (next_chest_index + 1) % 11;
        return chest_string;

    }

}
