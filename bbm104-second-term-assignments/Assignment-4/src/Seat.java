public class Seat {
 Movie movie;
 Hall hall;
 int rowNum, columnNum, price;
 User owner;

 public Seat(Movie movie, Hall hall, int rowNum, int columnNum, User owner, int price){
     this.movie = movie;
     this.hall = hall;
     this.rowNum = rowNum;
     this.columnNum = columnNum;
     this.price = price;
     this.owner = owner;

 }
  public String toCoordinate(){
     return (rowNum + 1) + "-" + (columnNum + 1);
  }

    public String backupFileText(){
     String ownerText = (owner == null) ? "null" : owner.getUsername();
        return "seat\t" + hall.movie.getFilmName() + "\t" + hall.hallName + "\t" + rowNum+ "\t" + columnNum + "\t" + ownerText + "\t" + price;
    }
}
