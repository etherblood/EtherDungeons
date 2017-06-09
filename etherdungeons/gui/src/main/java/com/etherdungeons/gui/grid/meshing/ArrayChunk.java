package com.etherdungeons.gui.grid.meshing;

import java.util.Arrays;
import java.util.EnumMap;

/**
 *
 * @author Philipp
 */
public class ArrayChunk {
    private final byte[] blocks;
    private final ChunkSize size;
    private final EnumMap<Direction, ArrayChunk> neighbors = new EnumMap<>(Direction.class);

    public ArrayChunk(ChunkSize size) {
        if(size == null) {
            throw new NullPointerException("size of " + ArrayChunk.class.getSimpleName() + " must not be NULL.");
        }
        this.size = size;
        blocks = new byte[size.getX() * size.getY() * size.getZ()];
    }
    public final byte getBlock(int x, int y, int z) {
        checkInBounds(x, y, z);
        return getBlockFast(index(x, y, z));
    }
    
    public final void setBlock(int x, int y, int z, byte value) {
        checkInBounds(x, y, z);
        setBlockFast(index(x, y, z), value);
    }
    
    public final void setLayerBlocks(int y, byte value) {
        setLayersBlocks(y, y + 1, value);
    }
    public final void setLayersBlocks(int startY, int endY, byte value) {
        int startIndex = indexY(startY);
        int endIndex = indexY(endY);
        setBlocksFast(startIndex, endIndex, value);
    }

    public void setNeighbor(Direction direction, ArrayChunk neighbor) {
        neighbors.put(direction, neighbor);
    }

    public ArrayChunk getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }
    
    public final ChunkSize getSize() {
        return size;
    }
    
    public final void clear() {
        fill((byte)0);
    }
    public final void fill(byte block) {
        setBlocksFast(0, blocks.length, block);
    }

    public final byte[] getDataXZY() {
        return blocks;
    }
    private void checkInBounds(int x, int y, int z) {
        if(!size.contains(x, y, z)) {
            throw new RuntimeException("local position not contained in chunk.");
        }
    }

    public byte getBlockFast(int index) {
        return blocks[index];
    }

    public void setBlockFast(int index, byte value) {
        blocks[index] = value;
    }

    private void setBlocksFast(int startIndex, int endIndex, byte value) {
        Arrays.fill(blocks, startIndex, endIndex, value);
    }
    
    private int index(int x, int y, int z) {
        int index = y;
        index *= size.getZ();
        index += z;
        index *= size.getX();
        index += x;
        return index;
    }
    private int indexY(int y) {
        return size.getX() * size.getY() * y;
    }
    
}