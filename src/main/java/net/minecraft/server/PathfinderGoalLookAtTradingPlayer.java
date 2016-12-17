package net.minecraft.server;

public class PathfinderGoalLookAtTradingPlayer extends PathfinderGoalLookAtPlayer {

    private final EntityVillager e;

    public PathfinderGoalLookAtTradingPlayer(EntityVillager entityvillager) {
        super(entityvillager, EntityHuman.class, 8.0F);
        this.e = entityvillager;
    }

    public boolean a() {
        if (this.e.dd()) {
            this.b = this.e.getTrader();
            return true;
        } else {
            return false;
        }
    }
}
