import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Documentation {

    private String documentation = "Documentation\\RSAHelp.txt";
    private String KeyFileDocumentation = "Documentation\\CreatingKeyFile.txt";
    public void getDocumentation(){
        readLines(documentation);
    }
    public void getKeyFileDocumentation(){
        readLines(KeyFileDocumentation);
    }

    private void readLines(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
