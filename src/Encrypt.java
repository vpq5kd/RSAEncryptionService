
import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class Encrypt {
    private String message = "";
    private String argument = "0";
    private List<BigInteger> key = null;
    public void run(){
        System.out.println("Welcome to my encryption service, enter 1 to write in your own message or enter 2 to encrypt a file:");
        while(!argument.equals("1")&&!argument.equals("2")) {
            Scanner scanner = new Scanner(System.in);
            String toArgument = scanner.nextLine();
            boolean NotOneOrTwo = !toArgument.equals("1") && !toArgument.equals("2");
            if (NotOneOrTwo) {
                System.out.println(toArgument + " is not 1 or 2, please try again:\n");
            }else{
                argument = toArgument;
                break;
            }
        }
        if (argument.equals("1")){
            writeInMessage();
        }else if(argument.equals("2")){
            useFile();
        }
    }
    private void writeInMessage(){
        KeyHandler keyHandler = new KeyHandler();
        key = keyHandler.handleKeys();
        RSAEncryption rsaEncryption = new RSAEncryption(key.get(0),key.get(1),key.get(2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your message:");
        message = scanner.nextLine();
        List<Object> publicKey = rsaEncryption.getPublicKey();
        List<BigInteger> cipherText = rsaEncryption.ENCRYPT(message,publicKey);
        System.out.println("Message encrypted, cipher text is as follows:");
        System.out.println(cipherText.toString());
    }
    private void useFile(){
        KeyHandler keyHandler = new KeyHandler();
        key = keyHandler.handleKeys();
        RSAEncryption rsaEncryption = new RSAEncryption(key.get(0),key.get(1),key.get(2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file you want to encrypt:");
        String fileName = "";
        while(message.equals("")) {
            try {
                fileName = scanner.nextLine();
                message = fileToString(fileName);
            } catch (IOException e) {
                System.out.println("File not found, please try again. Make sure your file is inside the RSAEncryptionService Folder.");
                continue;
            }
        }
        System.out.println("WARNING: Encryption will overwrite "+fileName+"... press enter to acknowledge your file will be overwritten or enter quit to return to safety:");
        String toQuitOrNotToQuit = scanner.nextLine();
        if (toQuitOrNotToQuit.equals("quit")){
            throw new IllegalArgumentException();
        }
        List<Object> publicKey = rsaEncryption.getPublicKey();
        List<BigInteger> cipherText = rsaEncryption.ENCRYPT(message,publicKey);
        encryptFile(fileName,cipherText.toString());
        System.out.println("File encrypted, cipher text is as follows:");
        System.out.println(cipherText.toString());
    }
    private void encryptFile(String fileName, String cipherText){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(cipherText);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileToString(String fileName) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IOException();
        }

        String fileContent = stringBuilder.toString();
        return fileContent;
    }

//    private void handleKeys() {
//        String hasKey = "";
//        System.out.println("Do you have a key? (yes/no)");
//        while(!hasKey.equals("yes")&&!hasKey.equals("no")){
//            Scanner scanner = new Scanner(System.in);
//            String toHasKey = scanner.nextLine();
//            boolean notYesOrNo = !toHasKey.equals("yes")&&!toHasKey.equals("no");
//            if(notYesOrNo){
//                System.out.println(toHasKey+ " is not yes or no, please try again");
//            }
//            else{
//                hasKey = toHasKey;
//                break;
//            }
//        }
//        if(hasKey.equals("yes")){
//            System.out.println("If you have already used --getKeys or included your key file in Keys as KeyFile.txt enter 1. Otherwise, enter your key file name:");
//            Scanner scanner = new Scanner(System.in);
//            String fileName = scanner.nextLine();
//            if (fileName.equals("1")){
//                KeyHandler keyHandler = new KeyHandler();
//                keyHandler.getKeysFromFile();
//                key = keyHandler.returnKeys();
//
//            }else {
//                KeyHandler keyHandler = new KeyHandler(fileName);
//                keyHandler.getKeysFromFile();
//                key = keyHandler.returnKeys();
//            }
//        }else if(hasKey.equals("no")){
//            KeyGenerator keyGenerator = new KeyGenerator();
//            KeyHandler keyHandler = new KeyHandler();
//            keyGenerator.generateKeys();
//            keyHandler.getKeysFromFile();
//            key = keyHandler.returnKeys();
//        }
//    }
}
