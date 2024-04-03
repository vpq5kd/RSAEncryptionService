public class UI_Main {

    public static void main(String args[]){
        System.out.println("Welcome to Sophia Spaner's RSA Encryption Service!\nThis is a basic application for generating public-private key pairs, as well as encrypting & decrypting files.\nAs a basic application, I do not take any responsibility for information leaks that come as a result of the use of this application.\nPlease type --help for an abridged documentation file and an argument list");
        while(true){
            ArgumentHandler argumentHandler = new ArgumentHandler();
            try {
                argumentHandler.getArgument();
            }catch (IllegalArgumentException e){
                continue;
            }
        }
    }
}
