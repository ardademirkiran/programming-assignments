import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOutput {
    public static String output_text = "";

    public static void add_text(String text){
        output_text += text;
    }
    public static void add_seperator(){
        output_text += "-----------------------------------------------------------------------------------------------------------\n";
    }
    public static void write_to_file() throws IOException {
        FileWriter output_file = new FileWriter("output.txt");
        BufferedWriter writer = new BufferedWriter(output_file);
        writer.write(output_text);
        writer.close();
    }
}

