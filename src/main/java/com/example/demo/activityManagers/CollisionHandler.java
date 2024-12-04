package com.example.demo.activityManagers;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.FighterPlane;
import com.example.demo.actors.Projectiles.Projectile;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.FuelToken;
import com.example.demo.levels.LevelParent;

import java.util.List;


public class CollisionHandler {

    private LevelParent levelParent;

    public CollisionHandler(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    public void checkCollisions(List<ActiveActorDestructible> activeActors) {
        for (int i = 0; i < activeActors.size(); i++) {
            ActiveActorDestructible actor1 = activeActors.get(i);

            for (int j = i + 1; j < activeActors.size(); j++) {
                ActiveActorDestructible actor2 = activeActors.get(j);

                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    if (actor1.isCollectible() ^ actor2.isCollectible()) {
                        handleCollectibleInteraction(actor1, actor2);
                    } else if (!actor1.isCollectible() && !actor2.isCollectible()){
                        if ((actor1.isFriendly() ^ actor2.isFriendly())) {
                            handleNonCollectibleCollision(actor1, actor2);
                        }
                    }
                }
            }
        }
    }

    protected void handleCollectibleInteraction(ActiveActorDestructible actor1, ActiveActorDestructible actor2) {
        if (actor1.isCollectible() && !actor2.isCollectible()) {
            actor1.takeDamage();
            handleCollectibleEffect(actor1);
        } else if (!actor1.isCollectible() && actor2.isCollectible()) {
            actor2.takeDamage();
            handleCollectibleEffect(actor2);
        }
    }

    protected void handleNonCollectibleCollision(ActiveActorDestructible actor1, ActiveActorDestructible actor2) {
        if (actor1 instanceof Projectile && actor2 instanceof Projectile) {
            return;
        }

        if ((actor1 instanceof FighterPlane && actor2 instanceof FighterPlane)||
                (actor1 instanceof UserProjectile && actor2 instanceof FighterPlane) ||
                (actor1 instanceof FighterPlane && actor2 instanceof UserProjectile)){
            levelParent.getUser().incrementKillCount();
            levelParent.currentNumberOfEnemies--;
        }

        actor1.takeDamage();
        actor2.takeDamage();
    }

    protected void handleCollectibleEffect(ActiveActorDestructible actor) {
        if (actor instanceof Coins) {
            levelParent.getUser().incrementScore();
        } else if (actor instanceof FuelToken) {
            levelParent.getUser().incrementFuel();
        }
    }

}
