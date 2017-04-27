package com.etherdungeons.engine.relations;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TeamMemberOf implements EntityComponent {

    private final EntityId team;

    public TeamMemberOf(EntityId team) {
        this.team = team;
    }

    public EntityId getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "team=" + team + '}';
    }
}
