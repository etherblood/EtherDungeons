package com.etherdungeons.gui.grid.meshing;

/**
 *
 * @author Philipp
 */
public final class ChunkSize {

    private final int x, y, z;

    public ChunkSize(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean contains(int x, int y, int z) {
        return 0 <= x && x < this.x && 0 <= y && y < this.y && 0 <= z && z < this.z;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        hash = 97 * hash + this.z;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return equals((ChunkSize) obj);
    }

    public boolean equals(ChunkSize size) {
        return this.x == size.x && this.y == size.y && this.z == size.z;
    }
}
