package com.etherdungeons.basemod.data.position.map;

import com.etherdungeons.basemod.data.gameflow.GameState;
import com.etherdungeons.basemod.data.gameflow.MapSize;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MapInitSystem  implements Runnable {
    private final EntityData data;
    private final GameMap map;
    private boolean initialized = false;

    public MapInitSystem(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void run() {
        if (!initialized) {
            EntityId state = data.entity(GameState.class);
            MapSize size = data.get(state, MapSize.class);
            map.setSize(size.getWidth(), size.getHeight());
            initialized = true;
        }
    }

}
