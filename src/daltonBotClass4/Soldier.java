package daltonBotClass4;

import battlecode.common.*;

public class Soldier extends Robot {

    public void onUpdate() {
        Direction soldierDir = null;
        Team enemy = robotController.getTeam().opponent();

        while (true) {
            try {
                Direction dir = randomDirection();

                if (soldierDir == null) {
                    soldierDir = dir;
                }

                RobotInfo[] robots = robotController.senseNearbyRobots(-1, enemy);

                if (robots.length > 0) {
                    if (robotController.canFireSingleShot()) {
                        robotController.fireSingleShot(robotController.getLocation().directionTo(robots[0].location));
                    }
//                    tryMove(robotController.getLocation().directionTo(robots[0].location));
                }
                else {
                    if (!tryMove(soldierDir)) {
                        soldierDir = randomDirection();
                        tryMove(soldierDir);
                    }
                }

                Clock.yield();

            } catch (Exception e) {
                System.out.println("A robotController Exception");
                e.printStackTrace();
            }
        }
    }
}
