package com.etherdungeons.engine;

import com.etherdungeons.context.Context;

/**
 *
 * @author Philipp
 */
public class GameImpl implements Game {
    private final Context context;

    public GameImpl(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
