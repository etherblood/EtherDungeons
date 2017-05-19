package com.etherdungeons.engine.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Optional;

/**
 *
 * @author Philipp
 */
public class PostResolveTriggerSystem implements Runnable {
    private final EntityData data;

    public PostResolveTriggerSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        if(!data.streamEntities(TriggerRequest.class).findAny().isPresent()) {
            Optional<EntityId> first = data.streamEntities(PostResolveTriggerRequest.class).sorted().findFirst();
            if(first.isPresent()) {
                EntityId triggerArgs = first.get();
                EntityId effect = data.remove(triggerArgs, PostResolveTriggerRequest.class).getTrigger();
                data.set(triggerArgs, new TriggerRequest(effect));
            }
        }
    }
    
}
