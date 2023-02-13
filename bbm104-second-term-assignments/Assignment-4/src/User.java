public class User {
    public String getUsername() {
        return username;
    }

    private String username;

    public String getPassword() {
        return password;
    }

    private String password;

    public Boolean getCheck_admin() {
        return check_admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCheck_admin(Boolean check_admin) {
        this.check_admin = check_admin;
    }

    public void setCheck_cm(Boolean check_cm) {
        this.check_cm = check_cm;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Boolean check_cm, check_admin;

    public Boolean getCheck_cm() {
        return check_cm;
    }

    public User(String username, String password, Boolean check_cm, Boolean check_admin){
        this.username = username;
        this.password = password;
        this.check_cm = check_cm;
        this.check_admin = check_admin;

    }
    @Override
    public String toString(){
        return username;
    }

    public String backupFileText(){
        return "user\t" + username + "\t" + password + "\t" + check_cm + "\t" + check_admin;
    }

    public String titleText(){
        if(check_admin == false && check_cm == false){
            return "";
        } else if (check_admin == true && check_cm == false){
            return "(Admin)";
        } else if (check_admin == false && check_cm == true){
            return "(Club Member)";
        } else {
            return "(Admin - Club Member)";
        }
    }
}
