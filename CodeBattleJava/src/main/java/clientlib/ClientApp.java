package clientlib;



public class ClientApp {

    private final static String URL = "localhost:8080"; // 52.232.32.105
    private final static String USERNAME = "name@mail.ru";
    private final static String PASSWORD = "16957924211070354251";


    public static void main(String[] args) {
        try {
            WebSocketRunner.run(URL, USERNAME, PASSWORD, new SampleSolver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
