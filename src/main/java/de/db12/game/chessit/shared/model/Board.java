package de.db12.game.chessit.shared.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.db12.game.chessit.client.online.BoardPresenter.Player;
import de.db12.game.chessit.shared.model.Stone.Type;

public class Board {
    Map<Integer, Field> fields = new HashMap<Integer, Field>();
    private final int size;

    public Board(int size) {
        this.size = size;
    }

    public void addStone(int x, int y, Stone stone) {
        Field target = fields.get(x * size + y);
        if (target == null || target.getStone().getType() == Type.empty)
            fields.put(x * size + y, new Field(x, y, stone));
        checkNeighbours(x, y);

    }

    private void checkNeighbours(int x, int y) {
        checkEmpty(x - 1, y - 1);
        checkEmpty(x - 1, y);
        checkEmpty(x - 1, y + 1);
        checkEmpty(x, y - 1);
        checkEmpty(x, y + 1);
        checkEmpty(x + 1, y - 1);
        checkEmpty(x + 1, y);
        checkEmpty(x + 1, y + 1);
    }

    private void checkEmpty(int x, int y) {
        if (fields.get(x * size + y) == null)
            fields.put(x * size + y, new Field(x, y, new Stone(Type.empty, Player.none)));
    }

    public int getXOffset() {
        int xmin = size;
        for (Field field : fields.values()) {
            if (xmin > field.getX())
                xmin = field.getX();
        }
        return xmin;
    }

    public int getYOffset() {
        int ymin = size;
        for (Field field : fields.values()) {
            if (ymin > field.getY())
                ymin = field.getY();
        }
        return ymin;
    }

    public Collection<Field> getFields() {
        Iterator<Entry<Integer, Field>> iter = fields.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Integer, Field> entry = iter.next();
            if (entry.getValue().getStone() == null || entry.getValue().getStone().getType() == Type.empty)
                iter.remove();
        }
        List<Integer> empties = new ArrayList<Integer>();
        for (Map.Entry<Integer, Field> entry : fields.entrySet()) {
            empties.add(entry.getKey());
        }
        for (Integer key : empties) {
            checkNeighbours(key / size, key % size);
        }
        return fields.values();
    }
}
