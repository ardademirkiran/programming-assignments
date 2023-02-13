import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteBackup {
    public static void writeBackupFile() throws IOException {
        FileWriter backupFile = new FileWriter("assets/data/backup.dat");
        BufferedWriter writer = new BufferedWriter(backupFile);
        for (User user: ReadData.users){
            writer.write(user.backupFileText() + "\n");
        }
        for(Movie movie: ReadData.movies){
            writer.write(movie.backupFileText() + "\n");
            for(Hall hall: movie.getHalls()){
                writer.write(hall.backupFileText() + "\n");
                for(int i = 0; i < hall.seats.length; i++){
                    for (int x = 0; x < hall.seats[i].length; x++){
                        writer.write(hall.seats[i][x].backupFileText() + "\n");
                    }
                }
            }
        }
        writer.close();
    }
}
