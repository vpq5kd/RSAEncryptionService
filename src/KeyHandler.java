import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KeyHandler {
    private String KeyFile;
    private BigInteger d;
    private BigInteger e;
    private BigInteger n;
    public KeyHandler(){
        KeyFile = "Keys\\KeyFile.txt";
    }
    public KeyHandler(BigInteger d, BigInteger e, BigInteger n){
        this.d = d;
        this.e = e;
        this.n = n;
    }
    public KeyHandler(String fileName){
        this.KeyFile = fileName;
    }
    public List<BigInteger> returnKeys(){
        List<BigInteger> keys = new ArrayList<>();
        keys.add(d);
        keys.add(e);
        keys.add(n);
        return keys;
    }
    public List<BigInteger> handleKeys() {
        List<BigInteger> key = new ArrayList<>();
        String hasKey = "";
        System.out.println("Do you have a key? (yes/no)");
        while(!hasKey.equals("yes")&&!hasKey.equals("no")){
            Scanner scanner = new Scanner(System.in);
            String toHasKey = scanner.nextLine();
            boolean notYesOrNo = !toHasKey.equals("yes")&&!toHasKey.equals("no");
            if(notYesOrNo){
                System.out.println(toHasKey+ " is not yes or no, please try again");
            }
            else{
                hasKey = toHasKey;
                break;
            }
        }
        if(hasKey.equals("yes")){
            System.out.println("If you have already used --getKeys or included your key file in Keys as KeyFile.txt enter 1. Otherwise, enter your key file name:");
            Scanner scanner = new Scanner(System.in);
            String fileName = "";
            while(fileName.equals("")) {
                fileName = scanner.nextLine();
                if (fileName.equals("1")) {
                    try {
                        this.getKeysFromFile();
                    }catch (IOException e){
                        continue;
                    }
                    key = this.returnKeys();
                    break;

                } else {
                    this.KeyFile = "Keys\\"+fileName;
                    try {
                        this.getKeysFromFile();
                        key = this.returnKeys();
                    }catch (IOException e){
                        System.out.println("File not found, please ensure your file is in the Keys folder");
                        fileName = "";
                    }catch (RuntimeException e){
                        System.out.println("Incorrect key file format, program restarting... Please use --KeyFileFormat for more information and try again.");
                        throw new IllegalArgumentException();
                    }
                }
            }
        }else if(hasKey.equals("no")){
            KeyGenerator keyGenerator = new KeyGenerator();
            keyGenerator.generateKeys();
            try {
                this.getKeysFromFile();
            }catch (IOException e){}
            key = this.returnKeys();
        }
        return key;
    }
    public void getKeysFromFile() throws IOException {
        List<String> keyFileLines = readLines();
        String [] publicKey = keyFileLines.get(0).split(" ");
        String [] privateKey = keyFileLines.get(1).split(" ");
        e = new BigInteger(publicKey[0]);
        d = new BigInteger(privateKey[0]);
        n = new BigInteger(publicKey[1]);
    }

    private List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(KeyFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new IOException();
        }
        if (lines.size()!=2){
            throw new RuntimeException("Your key file must have two lines, please use --help to see the documentation on creating a key file");
        }
        return lines;
    }

}
