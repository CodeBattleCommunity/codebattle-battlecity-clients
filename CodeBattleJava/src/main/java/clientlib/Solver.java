package clientlib;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static clientlib.Action.NONE;
import static java.lang.String.format;

public abstract class Solver {

    protected int size;
    protected Elements[][] field;
    protected Map<Elements, List<Point>> mapElements;

    /**
     * Метод парсинга игрового поля. Вызывается после ответа сервера
     *
     * @param boardString игровое поле
     */
    public Solver parseField(String boardString) {

        String board = boardString.replaceAll("\n", "");
        size = (int) Math.sqrt(board.length());
        field = new Elements[size][size];
        mapElements = new HashMap<>();

        board = boardString.replaceAll("\n", "");

        char[] temp = board.toCharArray();
        for (int y = 0; y < size ; y++) {
            int dy = y * size;
            for (int x = 0; x < size; x++) {
                field[x][y] = Elements.valueOf(temp[dy + x]);
            }
        }

        return this;
    }

    protected final String up(Action action) {
        return act("UP", action);
    }

    protected final String down(Action action) {
        return act("DOWN", action);
    }

    protected final String left(Action action) {
        return act("LEFT", action);
    }

    protected final String right(Action action) {
        return act("RIGHT", action);
    }

    protected final String act() {
        return act("ACT", NONE);
    }

    protected final String up() {
        return act("UP", NONE);
    }

    protected final String down() {
        return act("DOWN", NONE);
    }

    protected final String left() {
        return act("LEFT", NONE);
    }

    protected final String right() {
        return act("RIGHT", NONE);
    }



    public abstract String move();

    private String act(String direction, Action action) {
        action = action == null ? NONE : action;
        return format("%s%s%s", action.getPreTurn(), direction, action.getPostTurn());
    }
}
