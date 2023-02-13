import javafx.scene.control.Button;

public class SeatButton extends Button {
    public Seat getSeat() {
        return seat;
    }

    private Seat seat;
    public SeatButton(Seat seat){
        this.seat = seat;
    }
    public String processLabelText(){
        if (seat.owner == null){
            return "Not bought yet!";
        } else {
            return "Bought by " + seat.owner.getUsername() + " for " + seat.price + " TL!";
        }
    }
}
