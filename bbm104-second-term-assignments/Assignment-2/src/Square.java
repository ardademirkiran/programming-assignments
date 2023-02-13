public class Square {
    int id;
    String name;
    int cost;
    int owned_flag;
    Player owner;
    public int check_property(){
        if (owned_flag == 0){
            return 1;
        } else {
            return 0;
        }
    }
    public double calculate_cost(int dice){
        return 0;
    }
}

class Land extends Square{
    public Land(int id, String name, int cost, int owned_flag, Player owner){
        this.id = id;
        this. name = name;
        this.cost = cost;
        this.owned_flag = owned_flag;
        this.owner = owner;
    }
    public double calculate_cost(int dice){
        if (cost <= 2000) {
            return cost * 4 / 10;
        } else if (cost <= 3000) {
            return cost * 3 / 10;
        } else {
            return cost * 3.5 / 10;
        }
    }
}

class Railroad extends Square {
    public Railroad(int id, String name, int cost, int owned_flag, Player owner) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.owned_flag = owned_flag;
        this.owner = owner;
    }
    public double calculate_cost(int dice){
        int num_of_rr = owner.num_of_railroads;
        return num_of_rr * 25;
    }
}

class Company extends Square{
    public Company(int id, String name, int cost, int owned_flag, Player owner) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.owned_flag = owned_flag;
        this.owner = owner;
    }
    public double calculate_cost(int dice){
        return 4 * dice;
    }
}
