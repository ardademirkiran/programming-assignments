import java.util.ArrayList;

public class Human {
   static void calculate_calorie_needs(int weight, int height, int age, String gender) {
      double temp_number;
      int calorie_need;
      if (gender.equals("male") == true) {
         temp_number = (66 + (13.75 * weight) + (5 * height)) - (6.8 * age);
         calorie_need = (int) (temp_number + 0.5);
         daily_calorie_needs.add(calorie_need);
      } else {
         temp_number = (665 + (9.6 * weight) + (1.7 * height)) - (4.7 * age);
         calorie_need = (int) (temp_number + 0.5);
         daily_calorie_needs.add(calorie_need);
      }
   }
   static String eat(String person_id, String food_id, String portion){
      int human_index = Human.human_id.indexOf(Integer.parseInt(person_id));
      int food_index = Food.food_id.indexOf(Integer.parseInt(food_id));
      int food_calorie = Food.food_calorie.get(food_index) * Integer.parseInt(portion);
      int current_taken = Human.calories_taken.get(human_index);
      String food_name =  Food.food_name.get(food_index);
      Human.calories_taken.set(human_index, food_calorie + current_taken);
      String text = person_id + "\t" + "has" + "\t" + "taken" + "\t" + String.valueOf(food_calorie) + "kcal" + "\t" + "from" + "\t" + food_name;
      return text;
   }
   static String exercise(String person_id, String sport_id, String duration){
      int human_index = Human.human_id.indexOf(Integer.parseInt(person_id));
      int sport_index = Sport.sport_id.indexOf(Integer.parseInt(sport_id));
      double exercise_time = Integer.parseInt(duration);
      int burned_calories = (int) ((exercise_time / 60) * Sport.sport_calorie.get(sport_index));
      int current_burned = Human.calories_burned.get(human_index);
      String sport_name = Sport.sport_name.get(sport_index);
      Human.calories_burned.set(human_index, burned_calories + current_burned);
      String text = person_id + "\t" + "has" + "\t" + "burned" + "\t" + String.valueOf(burned_calories) + "kcal" + "\t" + "thanks  to" + "\t" + sport_name;
      return text;
   }
   static void calculate_result(int person_id){
      int human_index = Human.human_id.indexOf(person_id);
      int result = Human.calories_taken.get(human_index) - Human.calories_burned.get(human_index) - Human.daily_calorie_needs.get(human_index);
      Human.result.set(human_index, result);
   }

   static ArrayList<Integer> human_id = new ArrayList<>();
   static ArrayList<String> names = new ArrayList<>();
   static ArrayList<String> genders = new ArrayList<>();
   static ArrayList<Integer> weights = new ArrayList<>();
   static ArrayList<Integer> heights = new ArrayList<>();
   static ArrayList<Integer> birthdates = new ArrayList<>();
   static ArrayList<Integer> daily_calorie_needs = new ArrayList<>();
   static ArrayList<Integer> calories_taken = new ArrayList<>();
   static ArrayList<Integer> calories_burned = new ArrayList<>();
   static ArrayList<Integer> result = new ArrayList<>();
}
