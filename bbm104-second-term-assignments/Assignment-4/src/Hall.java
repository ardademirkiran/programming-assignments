public class Hall {
    Movie movie;
    String hallName;
    int seatPrice, rowNum, columnNum;
    Seat[][] seats;

    public Hall(Movie movie, String hallName, int seatPrice, int rowNum, int columnNum){
        this.movie = movie;
        this.hallName = hallName;
        this.seatPrice = seatPrice;
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        seats = new Seat[rowNum][columnNum];
    }
    @Override
    public String toString(){
        return hallName;
    }
    public void createSeats(){
        for(int i = 0; i < rowNum; i++){
            for (int x = 0; x < columnNum; x++){
                seats[i][x] = new Seat(movie, this, i, x, null, 0);
            }
        }
    }
    public String backupFileText(){
        return "hall\t" + movie.toString() + "\t" + hallName + "\t" + seatPrice + "\t" + rowNum+ "\t" + columnNum;
    }
}
