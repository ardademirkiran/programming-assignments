import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        String[] splitStartTime = getStartTime().split(":");
        int hour = (Integer.parseInt(splitStartTime[0]) + duration) % 24;
        String val = (hour < 10 ? "0" + hour: String.valueOf(hour)) + ":" + splitStartTime[1];
        return val;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        return (this.importance * (urgent ? 2000 : 1)) / this.duration;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        String[] thisObjectFinishTime = this.getFinishTime().split(":");
        String[] taskToCompareFinishTime = ((Task) o).getFinishTime().split(":");
        LocalTime thisObjectLt = LocalTime.of(Integer.parseInt(thisObjectFinishTime[0]), Integer.parseInt(thisObjectFinishTime[1]));
        LocalTime taskToCompareLt = LocalTime.of(Integer.parseInt(taskToCompareFinishTime[0]), Integer.parseInt(taskToCompareFinishTime[1]));
        return thisObjectLt.compareTo(taskToCompareLt);
    }

    public boolean isCompatible(Task taskToCompare){
        String[] taskToCompareFinishTime = taskToCompare.getFinishTime().split(":");
        String[] thisTaskStartTime = this.start.split(":");
        LocalTime thisTaskStartTimeLt = LocalTime.of(Integer.parseInt(thisTaskStartTime[0]), Integer.parseInt(thisTaskStartTime[1]));
        LocalTime taskToCompareFinishTimeLt = LocalTime.of(Integer.parseInt(taskToCompareFinishTime[0]), Integer.parseInt(taskToCompareFinishTime[1]));
        if(thisTaskStartTimeLt.compareTo(taskToCompareFinishTimeLt) >= 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "At " + start + ", " + name + ".";
    }
}
