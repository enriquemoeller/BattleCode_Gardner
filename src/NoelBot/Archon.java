package NoelBot;

import battlecode.common.*;

public class Archon extends Robot {

    @Override
    public void onUpdate() {
        int numberOfGardeners = 0;

        System.out.println("outside While Statement for Archon");

        while (true) {
            System.out.println("inside While Statement for Archon");
            try {
                System.out.println(System.getProperty("bc.testing.seed", "0").hashCode() + 1);
                Direction dir = randomDirection();
                if (robotController.canHireGardener(dir) && Math.random() < .01 && numberOfGardeners < 16) {
                    robotController.hireGardener(dir);
                    System.out.println("number of gardeners " + numberOfGardeners);
                    numberOfGardeners++;
                }

                if (robotController.getTeamBullets() > 1000) {
                    robotController.donate(robotController.getTeamBullets() - 1000);
                }
                tryMove(dir);
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception for master");
                e.printStackTrace();
            }
        }
    }
}