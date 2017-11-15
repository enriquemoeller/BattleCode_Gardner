package NoelBot;

import battlecode.common.*;

class Soldier extends Robot {

    public void onUpdate() {
        Direction soldierDir = null;

        while (true) {
            try {
                Direction dir = randomDirection();
                if (soldierDir == null) {
                    soldierDir = dir;
                }

                // See if there are any nearby enemy robots
                RobotInfo[] robots = robotController.senseNearbyRobots(-1, enemy);

                // See if there are any bullets nearby
                BulletInfo[] bullets = robotController.senseNearbyBullets(robotController.getType().bodyRadius * 2);

                // If robots nearby
                if (robots.length > 0) {
                    // And we have enough bullets, and haven't attacked yet this turn...
                    if (robotController.canFireSingleShot()) {
                        // ...Then fire a bullet in the direction of the enemy.
                        robotController.fireSingleShot(robotController.getLocation().directionTo(robots[0].location));
                    }
//                    if (willCollideWithMe(bullets[0])) {
//                        soldierDir = bullets[0].dir.rotateLeftDegrees(90);
//                        if (!robotController.hasMoved()) {
//                            if (tryMove(soldierDir)) {
//                                System.out.println("Moved");
//                            } else {
//                                soldierDir = randomDirection();
//                                tryMove(soldierDir);
//                            }
//                        }
//                    }
                }else if (!robotController.hasMoved()) {
                    if (tryMove(soldierDir)) {
                        System.out.println("Moved");
                    } else {
                        soldierDir = randomDirection();
                        tryMove(soldierDir);
                    }
                }

                // If bullets nearby
//                for (BulletInfo bullet : bullets) {
//                    if (willCollideWithMe(bullet)) {
//                        // Move away from the bullet somehow
//                        soldierDir = bullet.dir.rotateLeftDegrees(90);
//                    }
//                }


                Clock.yield();
            } catch (Exception e) {
                System.out.println("A robotController Exception");
                e.printStackTrace();
            }
        }
    }
}
