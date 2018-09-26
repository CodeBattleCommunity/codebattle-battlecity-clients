package clientlib;


public enum Action {
    NONE("", ""),
    BEFORE_TURN("ACT,", ""),
    AFTER_TURN("", ",ACT");

    private String preTurn;
    private String postTurn;

    Action(String preTurn, String postTurn) {
        this.preTurn = preTurn;
        this.postTurn = postTurn;
    }

    public String getPreTurn() {
        return preTurn;
    }

    public String getPostTurn() {
        return postTurn;
    }
}
