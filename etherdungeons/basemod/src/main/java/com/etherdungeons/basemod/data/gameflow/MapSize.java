package com.etherdungeons.basemod.data.gameflow;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class MapSize implements EntityComponent {
    private final int width, height;

    public MapSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int hashCode() {
        return 29 * (29 * 5 + this.width) + this.height;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MapSize)) {
            return false;
        }
        final MapSize other = (MapSize) obj;
        return this.width == other.width && this.height == other.height;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{width=" + width + ", height=" + height + '}';
    }
}
