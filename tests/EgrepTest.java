import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EgrepTest {
    @Test
    public void EgrepSearchTest(){
        ArrayList<String> text=new ArrayList<>();
        try {
            File myObj = new File("./input/56667-0.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                text.add(myReader.nextLine());
            myReader.close();
            String regex = "S(a|g|r)*on";
            ArrayList<String> searchResult = Egrep.search(text, regex);
            assertEquals(searchResult.size(), 30);
            assertEquals(searchResult.stream().filter(val -> val.contains("state--Sargon and Merodach-baladan--Sennacherib's attempt")).collect(Collectors.toList()).size(), 1);
            assertEquals(searchResult.stream().filter(val -> val.contains(text.get(0))).collect(Collectors.toList()).size(), 0);
            System.out.println("Regex : S(a|g|r)*on");
            System.out.println(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
