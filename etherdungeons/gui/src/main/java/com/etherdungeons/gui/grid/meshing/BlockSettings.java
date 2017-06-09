package com.etherdungeons.gui.grid.meshing;

import com.etherdungeons.gui.grid.meshing.helpers.BoolArrayList;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 *
 * @author Philipp
 */
public class BlockSettings {

    public final static int INVALID_TILE = -1;
    public static byte NUM_BLOCKS = 0;
    public static final byte AIR = NUM_BLOCKS++;
    public static final byte WATER = NUM_BLOCKS++;
    public static final byte LEAFES = NUM_BLOCKS++;
    public static final byte GLASS = NUM_BLOCKS++;
    public static final byte DIRT = NUM_BLOCKS++;
    public static final byte GRASS = NUM_BLOCKS++;
    public static final byte STONE = NUM_BLOCKS++;
    public static final byte SAND = NUM_BLOCKS++;
    public static final byte WOOD = NUM_BLOCKS++;
    public static final byte SNOW = NUM_BLOCKS++;
    private final ArrayList<EnumMap<Direction, Integer>> blockTiles = new ArrayList<>();
    private final BoolArrayList tileOpaque = new BoolArrayList();
    private final BoolArrayList blockOpaque = new BoolArrayList();

    public BlockSettings() {
        registerTile(8, false);
        registerTile(24, false);
        registerTile(39, false);
        registerTile(40, false);
        //TODO read tile transparency from texture

        //EAST, WEST, UP, DOWN, SOUTH, NORTH
        registerSimpleBlock(AIR, INVALID_TILE);
        registerBlock(GRASS, 1, 1, 0, 2, 1, 1);
        registerSimpleBlock(GLASS, 8);
        registerSimpleBlock(LEAFES, 39);
        registerSimpleBlock(SAND, 18);
        registerSimpleBlock(STONE, 23);//9
        registerBlock(SNOW, 17, 17, 16, 2, 17, 17);
        registerSimpleBlock(WATER, 40);
        registerBlock(WOOD, 3, 3, 4, 4, 3, 3);
        registerSimpleBlock(DIRT, 2);
    }

    private void registerSimpleBlock(byte block, int tile) {
        registerBlock(block, tile, tile, tile, tile, tile, tile);
    }

    private void registerBlock(byte block, int... tiles) {
        int blockIndex = block & 0xFF;
        if (tiles.length != Direction.values().length) {
            throw new RuntimeException("must define a tile for each direction");
        }
        while (numBlocks() <= blockIndex) {
            blockTiles.add(null);
            blockOpaque.add(false);
        }
        EnumMap<Direction, Integer> map = new EnumMap<>(Direction.class);
        int i = 0;
        for (Direction direction : Direction.values()) {
            map.put(direction, tiles[i++]);
        }
        blockTiles.set(blockIndex, map);

        boolean opaque = true;
        for (int tile : tiles) {
            if (tile == INVALID_TILE || isTileTransparent(tile)) {
                opaque = false;
                break;
            }
        }
        blockOpaque.set(blockIndex, opaque);
    }

    private void registerTile(int tile, boolean opaque) {
        while (this.tileOpaque.size() <= tile) {
            this.tileOpaque.add(true);
        }
        this.tileOpaque.set(tile, opaque);
    }

    public int tileFromBlock(byte block, Direction face) {
        int blockIndex = block & 0xFF;
        return blockTiles.get(blockIndex).get(face);
    }

    public boolean isTileOpaque(int tile) {
        return tileOpaque.get(tile);
    }

    public boolean isTileTransparent(int tile) {
        return !isTileOpaque(tile);
    }

    private int numBlocks() {
        return blockTiles.size();
    }

    public boolean isBlockOpaque(byte block) {
        int blockIndex = block & 0xFF;
        return blockOpaque.get(blockIndex);
    }

    public int invalidTile() {
        return INVALID_TILE;
    }

    public boolean isBlockTransparent(byte block) {
        return !isBlockOpaque(block);
    }

    public boolean isBlockVisible(byte block) {
        return block != AIR;
    }
}
