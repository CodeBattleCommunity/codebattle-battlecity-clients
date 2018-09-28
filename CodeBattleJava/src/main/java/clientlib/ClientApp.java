package clientlib;



public class ClientApp {

    private final static String URL = "localhost:8080"; // 52.232.32.105
    private final static String USERNAME = "name1@mail.com";
    private final static String PASSWORD = "11458428841070354251";


    public static void main(String[] args) {
        try {
            WebSocketRunner.run(URL, USERNAME, PASSWORD, new SampleSolver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
