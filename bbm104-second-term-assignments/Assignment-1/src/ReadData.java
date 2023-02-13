import java.io.*;

public class ReadData {
    static void get_human_data() throws IOException {
        String line;
        FileReader human_file = new FileReader("people.txt");
        BufferedReader reader = new BufferedReader(human_file);
        while ((line = reader.readLine()) != null){
              String[] splitted_line = line.split("\t");
              Human.human_id.add(Integer.parseInt(splitted_line[0]));
              Human.names.add(splitted_line[1]);
              Human.genders.add(splitted_line[2]);
              Human.weights.add(Integer.parseInt(splitted_line[3]));
              Human.heights.add(Integer.parseInt(splitted_line[4]));
              Human.birthdates.add(Integer.parseInt(splitted_line[5]));
              Human.calories_burned.add(0);
              Human.calories_taken.add(0);
              Human.result.add(0);
              Human.calculate_calorie_needs(Integer.parseInt(splitted_line[3]), Integer.parseInt(splitted_line[4]), 2022 - Integer.parseInt(splitted_line[5]), splitted_line[2] );
        }
    }
    static void get_food_data() throws IOException {
        String line;
        FileReader food_file = new FileReader("food.txt");
        BufferedReader reader = new BufferedReader(food_file);
        while ((line = reader.readLine()) != null){
            String[] splitted_line = line.split("\t");
            Food.food_id.add(Integer.parseInt(splitted_line[0]));
            Food.food_name.add(splitted_line[1]);
            Food.food_calorie.add(Integer.parseInt(splitted_line[2]));
        }
    }
    static void get_sport_data() throws IOException{
        String line;
        FileReader sport_file = new FileReader("sport.txt");
        BufferedReader reader = new BufferedReader(sport_file);
        while ((line = reader.readLine()) != null){
            String[] splitted_line = line.split("\t");
            Sport.sport_id.add(Integer.parseInt(splitted_line[0]));
            Sport.sport_name.add(splitted_line[1]);
            Sport.sport_calorie.add(Integer.parseInt(splitted_line[2]));
        }
    }
}
