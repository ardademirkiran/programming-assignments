import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    static String print_information(ArrayList<Integer> indexes_list){
        String text = "";
        if (indexes_list.isEmpty() == true){
            return "there\tis\tno\tsuch\tperson\n";
        }
        for (int index: indexes_list){
            Human.calculate_result(Human.human_id.get(index));
            String person_name = Human.names.get(index);
            int age = 2022 -Human.birthdates.get(index);
            int daily_need = Human.daily_calorie_needs.get(index);
            int taken_calories = Human.calories_taken.get(index);
            int burned_calories = Human.calories_burned.get(index);
            int result = Human.result.get(index);
            String sign = result > 0 ? "+" : "";
            text = text + person_name + "\t" + age + "\t" + daily_need + "kcal\t" + taken_calories + "kcal\t" + burned_calories + "kcal\t" + sign + result + "kcal\n" ;
        }
        return text;
    }

    public static void main(String[] args) throws IOException {
        String line, text = "";
        ArrayList<Integer> processed_indexes = new ArrayList<>();
        ArrayList<Integer> indexes_to_print = new ArrayList<>();
        FileReader input_file = new FileReader(args[0]);
        FileWriter output_file = new FileWriter("monitoring.txt");
        BufferedWriter writer = new BufferedWriter(output_file);
        BufferedReader reader = new BufferedReader(input_file);
        ReadData.get_human_data();
        ReadData.get_food_data();
        ReadData.get_sport_data();
        while ((line = reader.readLine()) != null){
            if (line.equals("printWarn") == true){
            for (int index: processed_indexes){
                if (Human.result.get(index) > 0){
                    indexes_to_print.add(index);
                }
            }
                text = print_information(indexes_to_print);

            } else if (line.equals("printList") == true){
                text = print_information(processed_indexes);

            } else if (line.contains("print") == true){
                int person_id = Integer.parseInt(line.substring(6, line.indexOf(")")));
                int person_index = Human.human_id.indexOf(person_id);
                indexes_to_print.add(person_index);
                text = print_information(indexes_to_print);

            } else {
                String[] splitted_line = line.split("\t");
                 int human_id = Integer.parseInt(splitted_line[0]);
                 if (processed_indexes.contains(Human.human_id.indexOf(human_id)) == false) {
                     processed_indexes.add(Human.human_id.indexOf(human_id));
                 }
                 if (splitted_line[1].startsWith("1") == true){
                   text = Human.eat(splitted_line[0], splitted_line[1], splitted_line[2]) + "\n";
                } else {
                   text = Human.exercise(splitted_line[0], splitted_line[1], splitted_line[2]) + "\n";
                 }
                Human.calculate_result(human_id);
            }
            writer.write(text);
            writer.write("***************\n");
            writer.flush();
            indexes_to_print.clear();
        }
        RandomAccessFile output = new RandomAccessFile(new File("monitoring.txt"), "rw");
        output.setLength(output.length() - 17);
        output.close();
    }
}
