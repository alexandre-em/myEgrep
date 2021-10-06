import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Egrep {
    public static void main(String arg[]){
        ArrayList<String> text=new ArrayList<>();
        try {
            File myObj = new File("/home/alexandre/Downloads/Cours/RES2/DAAR/project1/Egrep/input/56667-0.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                text.add(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
