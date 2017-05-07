package com.etherdungeons.engine.gameflow.triggers.cost;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class HasTriggerCost implements EntityComponent {

    private final EntityId investor;

    public HasTriggerCost(EntityId investor) {
        this.investor = investor;
    }

    public EntityId getInvestor() {
        return investor;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{investor=" + investor + '}';
    }
}
