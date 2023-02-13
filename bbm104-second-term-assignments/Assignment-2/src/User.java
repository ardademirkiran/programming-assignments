import java.util.ArrayList;

public abstract class User {
    int money;

    public void pay(User receiver, double amount) {

        this.money -= amount;
        receiver.money += amount;

    }
}

class Player extends User{
    public String getPlayer_text() {
        return player_text;
    }


    private final String player_text;
    int num_of_railroads = 0;
    int jail_count = 0;
    int parking_count = 0;
    int current_position = 1;
    ArrayList<String> properties_names = new ArrayList<>();
    ArrayList<Square> properties = new ArrayList<>();
    public void move(int steps, Banker banker){
        current_position += steps;
        if (current_position > 40){
            current_position = current_position % 40;
            banker.pay(this, 200);
        }

    }
    public void buy(Square square){

        properties.add(square);
        properties_names.add(square.name);
        square.owned_flag = 1;
        if (square instanceof Railroad){
            num_of_railroads += 1;
        }
    }
    public Player(String text){
        money = 15000;
        player_text = text;
    }
}

class Banker extends User{
    public Banker(){money = 100000;}
}
