package com.etherdungeons.entitysystem;

/**
 *
 * @author Philipp
 */
public class IncrementalEntityIdFactory implements EntityIdFactory {
    private long nextId = 0;
    
    @Override
    public EntityId createEntity() {
        return new EntityId(nextId++);
    }

    public long getNextId() {
        return nextId;
    }

    public void setNextId(long nextId) {
        this.nextId = nextId;
    }
}
