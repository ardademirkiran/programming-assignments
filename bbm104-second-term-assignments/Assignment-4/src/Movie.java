import java.util.ArrayList;

public class Movie {
    public String getFilmName() {
        return filmName;
    }

    public int getDuration() {
        return duration;
    }

    private String filmName;

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    private String trailerPath;
    private int duration;
    private ArrayList<Hall> halls = new ArrayList<>();

    public ArrayList<Hall> getHalls() {
        return halls;
    }



    public Movie(String filmName, String trailerPath, int duration){
        this.filmName = filmName;
        this.trailerPath = trailerPath;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return filmName;
    }

    public String backupFileText(){
        return "film\t" + filmName + "\t" + trailerPath + "\t" + duration;
    }
}
