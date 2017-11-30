package NoelBot;

import battlecode.common.*;

class Soldier extends Robot {

    public void onUpdate() {
        Direction soldierDir = null;

        while (true) {
            try {
                int tryMoveCount = 0;
                Direction originalDir = null;
                Direction dir = randomDirection();
                float teamBullets = robotController.getTeamBullets();
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
                    if (teamBullets > 350 && robotController.canFirePentadShot()) {
                        robotController.firePentadShot(robotController.getLocation().directionTo(robots[0].location));
                    } else if(teamBullets > 250 && robotController.canFireTriadShot()) {
                        robotController.fireTriadShot(robotController.getLocation().directionTo(robots[0].location));
                    } else if (robotController.canFireSingleShot()) {
                        // ...Then fire a bullet in the direction of the enemy.
                        robotController.fireSingleShot(robotController.getLocation().directionTo(robots[0].location));
                    } else {
                        for (BulletInfo bullet : bullets) {
                            if (willCollideWithMe(bullet)) {
                                // Move away from the bullet somehow
                                soldierDir = bullet.dir.rotateLeftDegrees(90);
//                    } else if (willCollideWithMe(bullets[0])) {
//                            soldierDir = bullets[0].dir.rotateLeftDegrees(90);
                                if (!robotController.hasMoved()) {
                                    originalDir = soldierDir;
                                    while (!tryMove(soldierDir)) {
                                        if (tryMoveCount < 6) {
                                            soldierDir = soldierDir.rotateLeftDegrees(15);
                                        } else if (tryMoveCount == 6) {
                                            soldierDir = originalDir.rotateRightDegrees(15);
                                        } else if (tryMoveCount < 12) {
                                            soldierDir = soldierDir.rotateRightDegrees(15);
                                        } else {
                                            soldierDir = randomDirection();
                                        }
                                        tryMoveCount++;
                                    }
//                                    if (tryMove(soldierDir)) {
//                                        System.out.println("Moved");
//                                    } else {
//                                        soldierDir = randomDirection();
//                                        tryMove(soldierDir);
//                                    }
                                }
                                break;
                            }
                        }
                    }
//                } else if (!robotController.hasMoved()) {
//                    if (tryMove(soldierDir)) {
//                        System.out.println("Moved");
//                    } else {
//                        soldierDir = randomDirection();
//                        tryMove(soldierDir);
//                    }
//                }
                } else if (!robotController.hasMoved()) {
                    originalDir = soldierDir;
                    while(!tryMove(soldierDir)) {
                        if (tryMoveCount < 6) {
                            soldierDir = soldierDir.rotateLeftDegrees(15);
                        } else if (tryMoveCount == 6) {
                            soldierDir = originalDir.rotateRightDegrees(15);
                        } else if (tryMoveCount < 12) {
                            soldierDir = soldierDir.rotateRightDegrees(15);
                        } else {
                            soldierDir = randomDirection();
                        }
                        tryMoveCount++;
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
