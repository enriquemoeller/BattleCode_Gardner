package NoelBot;

import battlecode.common.*;

class Gardener extends Robot {

    public void onUpdate() {
        boolean settled = false;
        Direction gardnerDir = null;
        int soldiersBuilt = 0;
        int treesPlanted = 0;

        while (true) {
            try {

                Direction dir = randomDirection();
                int treeCount = robotController.getTreeCount();

                if (gardnerDir == null) {
                    gardnerDir = dir;
                }

                if (settled) {
                    if (!(soldiersBuilt % 3 == 0) && treeCount > 20) {
                        if (robotController.canBuildRobot(RobotType.SOLDIER, gardnerDir)) {
                            robotController.buildRobot(RobotType.SOLDIER, gardnerDir);
                            soldiersBuilt++;
                        } else {
                            if (robotController.canBuildRobot(RobotType.SOLDIER, dir)) {
                                robotController.buildRobot(RobotType.SOLDIER, dir);
                                soldiersBuilt++;
                            }
                        }
                    } else if (treesPlanted > 0 && treesPlanted < 5) {
                        gardnerDir = gardnerDir.rotateLeftDegrees(60);
                        if (robotController.canPlantTree(gardnerDir)) {
                            robotController.plantTree(gardnerDir);
                            treesPlanted++;
                            if (treeCount > 20) {
                                soldiersBuilt++;
                            }
                        }
                    } else if (treesPlanted == 5) {
                        gardnerDir = gardnerDir.rotateLeftDegrees(60);
                        if (robotController.canBuildRobot(RobotType.SOLDIER, gardnerDir)) {
                            robotController.buildRobot(RobotType.SOLDIER, gardnerDir);
                            soldiersBuilt++;
                        }
                    } else if (treesPlanted > 5) {
                        if (robotController.canBuildRobot(RobotType.SOLDIER, gardnerDir)) {
                            robotController.buildRobot(RobotType.SOLDIER, gardnerDir);
                            soldiersBuilt++;
                        }
                    } else {
                        if (robotController.canPlantTree(dir)) {
                            robotController.plantTree(dir);
                            treesPlanted++;
                            gardnerDir = dir;
                            if (treeCount > 20) {
                                soldiersBuilt++;
                            }
                        } else {
                            if (robotController.canBuildRobot(RobotType.SOLDIER, dir)) {
                                robotController.buildRobot(RobotType.SOLDIER, dir);
                                soldiersBuilt++;
                            }
                        }
                    }
                }

                if (!(robotController.isCircleOccupiedExceptByThisRobot(robotController.getLocation(), robotController.getType().bodyRadius * 4.0f)) && !settled) {
                    settled = true;

                    if (robotController.canPlantTree(dir)) {
                        robotController.plantTree(dir);
                        gardnerDir = dir;
                        treesPlanted++;
                    }
                }

                TreeInfo[] trees = robotController.senseNearbyTrees(robotController.getType().bodyRadius * 2, robotController.getTeam());
                TreeInfo minHealthTree = null;

                for (TreeInfo tree : trees) {
                    if (tree.health < 70) {
                        if (minHealthTree == null || tree.health < minHealthTree.health) {
                            minHealthTree = tree;
                        }
                    }
                }

                if (minHealthTree != null) {
                    robotController.water(minHealthTree.ID);
                }


                if (!settled) {
                    if (robotController.canBuildRobot(RobotType.SOLDIER, dir)) {
                        robotController.buildRobot(RobotType.SOLDIER, dir);
                        soldiersBuilt++;
                    }

                    if (tryMove(gardnerDir)) {
                        System.out.println("moved");
                    } else {
                        gardnerDir = randomDirection();
                        tryMove(gardnerDir);
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
