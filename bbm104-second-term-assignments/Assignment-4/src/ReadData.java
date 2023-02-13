import java.io.*;
import java.util.ArrayList;

public class ReadData {
    static int maxError;
    static String title;
    static int discountPercentage;
    static int blockTime;
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Movie> movies = new ArrayList<>();
    static void dataReader() throws IOException {
        Movie currentMovie = null;
        Hall currentHall = null;
        String line;
        File file = new File("assets/data/backup.dat");
        if (!file.exists()){
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("user\tadmin\tX03MO1qnZdYdgyfeuILPmQ==\ttrue\ttrue\n");
            writer.write("film\tAvengers: Endgame\tavengers_endgame.mp4\t182");
            writer.close();
        }
        FileReader dataFile = new FileReader(file);
        BufferedReader reader = new BufferedReader(dataFile);
        while((line = reader.readLine()) != null){
            String[] splitted_line = line.split("\t");
            if (splitted_line[0].equals("user")){
                Boolean check_cm = splitted_line[3].equals("true");
                Boolean check_admin = splitted_line[4].equals("true");
                users.add(new User(splitted_line[1], splitted_line[2], check_cm, check_admin));
            } else if (splitted_line[0].equals("film")){
                currentMovie = new Movie(splitted_line[1], splitted_line[2], Integer.parseInt(splitted_line[3]));
                movies.add(currentMovie);

            } else if (splitted_line[0].equals("hall")){
                currentHall = new Hall(currentMovie, splitted_line[2], Integer.parseInt(splitted_line[3]), Integer.parseInt(splitted_line[4]), Integer.parseInt(splitted_line[5]));
                currentMovie.getHalls().add(currentHall);
            } else if(splitted_line[0].equals("seat")){
                User owner = null;
                if (!splitted_line[5].equals("null")){
                    for (User user: users){
                        if (splitted_line[5].equals(user.getUsername())){
                            owner = user;
                        }
                    }
                }
                currentHall.seats[Integer.parseInt(splitted_line[3])][Integer.parseInt(splitted_line[4])] = new Seat(currentMovie, currentHall, Integer.parseInt(splitted_line[3]), Integer.parseInt(splitted_line[4]), owner, Integer.parseInt(splitted_line[6]));
            }
        }
        propertyReader();

    }
    static void propertyReader() throws IOException {
        String line;
        FileReader propertyFile = new FileReader("assets/data/properties.dat");
        BufferedReader reader = new BufferedReader(propertyFile);
        while ((line = reader.readLine()) != null){
            if (line.startsWith("maximum-error-without-getting-blocked")){
                String[] splitted_line = line.split("=");
                maxError = Integer.parseInt(splitted_line[1]);
            } else if (line.startsWith("title")){
                String[] splitted_line = line.split("=");
                title = splitted_line[1];
            } else if (line.startsWith("discount-percentage")){
                String[] splitted_line = line.split("=");
                discountPercentage = Integer.parseInt(splitted_line[1]);
            } else if (line.startsWith("block-time")){
                String[] splitted_line = line.split("=");
                blockTime = Integer.parseInt(splitted_line[1]);
            }
        }


    }
}
