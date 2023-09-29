package jp.kaiz.atsassistmod.controller.trainprotection;

import jp.ngt.rtm.entity.train.EntityBogie;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.rail.TileEntityLargeRailBase;
import jp.ngt.rtm.rail.TileEntityLargeRailCore;
import jp.ngt.rtm.rail.TileEntityLargeRailSwitchCore;
import jp.ngt.rtm.rail.util.Point;
import jp.ngt.rtm.rail.util.RailMap;
import jp.ngt.rtm.rail.util.RailPosition;
import jp.ngt.rtm.rail.util.RailProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ATACSController extends TrainProtection {
    private int count;

    private TileEntityLargeRailCore savedRail;
    private RailPosition nowRP;
    private RailMap nowRM;

    private double movedDistance;

    private double otherTrainDistance;

    private final int[] speed = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

    @Override
    public void onTick(EntityTrainBase train, double distance) throws Exception {
        super.onTick(train, distance);
        double trainX = train.posX;
        double trainY = train.posY;
        double trainZ = train.posZ;
        TileEntityLargeRailBase nowRailBase = TileEntityLargeRailBase.getRailFromCoordinates(train.worldObj, trainX, trainY, trainZ);
        if (nowRailBase != null) {
            TileEntityLargeRailCore nowRailCore = nowRailBase.getRailCore();
            if (nowRailCore != null) {
                if (this.savedRail == null) {
                    this.savedRail = nowRailCore;
                    return;
                } else if (this.savedRail != nowRailCore) {
                    this.savedRail = nowRailCore;
                    this.movedDistance = 0d;
                    this.nowRM = this.getNearRailMap(nowRailCore);
                    this.nowRP = this.getNearRailPoint(this.nowRM);
                } else if (this.nowRP == null) {
                    return;
                }
                this.movedDistance = this.movedDistance + distance;

                if (this.count > 0) {
                    double nowRailLength = this.nowRM.getLength();
                    double trainDistance = this.otherTrainDistance + nowRailLength - this.movedDistance;

                    if (this.setPatternSpeed(trainDistance, 0)) {

                    } else {
                        this.count--;
                    }
                } else {
                    this.count = 20;
                }

                double necessaryDistance = this.getBreakingDistance(train.getSpeed());
                double trainDistance = this.otherTrainDistance = this.getAnotherTrainDistance(necessaryDistance + 100d);
                if (trainDistance == -1d) {
                    this.speed[0] = Integer.MAX_VALUE;
                    this.speed[1] = Integer.MAX_VALUE;
                    this.speed[2] = Integer.MAX_VALUE;
                } else {
                    double nowRailLength = this.nowRM.getLength();
                    trainDistance = trainDistance + nowRailLength - this.movedDistance;

                    if (this.setPatternSpeed(trainDistance, nowRailLength - this.movedDistance)) {
                        this.count = 0;
                    }
                }
            }
        }
    }

    private boolean setPatternSpeed(double trainDistance, double d0) {
        if (trainDistance - d0 < 1d) {
            return true;
        }

        if (trainDistance > 100d) {
            this.speed[0] = (int) this.getPattern(trainDistance - 120d);
            this.speed[1] = (int) this.getPattern(trainDistance - 110d);
            this.speed[2] = (int) this.getPattern(trainDistance - 100d);
        } else {
            this.speed[0] = 0;
            this.speed[1] = 0;
            this.speed[2] = 0;
        }
        return false;
    }

    public int getDisplaySpeed() {
        return this.speed[0];
    }

    public int getPatternSpeed() {
        return this.speed[1];
    }

    public int getEmergencySpeed() {
        return this.speed[2];
    }

    @Override
    public int getNotch(float speedH) {
        if (speedH > this.getEmergencySpeed()) {
            return -8;
        } else if (speedH > this.getPatternSpeed()) {
            return -7;
        } else if (this.getDisplaySpeed() == 0) {
            return -5;
        } else {
            return 1;
        }
    }

    @Override
    public TrainProtectionType getType() {
        return TrainProtectionType.ATACS;
    }

    //列車から
    private RailMap getNearRailMap(TileEntityLargeRailCore core) {
        RailMap railMap;
        if (core instanceof TileEntityLargeRailSwitchCore) {
            TileEntityLargeRailSwitchCore switchObj = (TileEntityLargeRailSwitchCore) core;
            railMap = switchObj.getSwitch().getNearestPoint(this.train.getBogie(train.getTrainDirection())).getActiveRailMap(this.train.worldObj);
        } else {
            railMap = core.getRailMap(this.train.getBogie(train.getTrainDirection()));
        }
        return railMap;
    }

    //レールから
    private RailMap getNearRailMap(TileEntityLargeRailCore core, TileEntityLargeRailBase base) {
        RailMap railMap;
        if (core instanceof TileEntityLargeRailSwitchCore) {
            TileEntityLargeRailSwitchCore switchObj = (TileEntityLargeRailSwitchCore) core;

            railMap = this.getNearestPoint(base, switchObj.getSwitch().getPoints()).getActiveRailMap(base.getWorldObj());
        } else {
            railMap = core.getRailMap(null);
        }
        return railMap;
    }

    public Point getNearestPoint(TileEntityLargeRailBase entity, Point[] points) {
        Point point = null;
        double distance = Double.MAX_VALUE;
        for (Point p0 : points) {
            double d0 = entity.getDistanceFrom(p0.rpRoot.posX, 0.0D, p0.rpRoot.posZ);
            if (d0 <= distance) {
                point = p0;
                distance = d0;
            }
        }
        return point;
    }

    private double getAnotherTrainDistance(double searchDistance) {
        double distance = 0d;
        RailPosition _tempRailPosition = this.getRailPositionDestination(this.nowRM);
        TileEntityLargeRailCore _tempRail = this.savedRail;
        while (true) {
            if (distance >= searchDistance) {
                return -1d;
            }
            TileEntityLargeRailBase railBase = this.getNextRailBase(_tempRail.getWorldObj(), _tempRailPosition);
            if (railBase == null) {
                return -1d;
            }
            _tempRail = railBase.getRailCore();
            if (_tempRail.isTrainOnRail()) {
                EntityBogie bogie0 = this.train.getBogie(0);
                EntityBogie bogie1 = this.train.getBogie(1);

                int x0 = MathHelper.floor_double(Math.min(bogie0.posX, bogie1.posX));
                int y0 = MathHelper.floor_double(Math.min(bogie0.posY, bogie1.posY));
                int z0 = MathHelper.floor_double(Math.min(bogie0.posZ, bogie1.posZ));
                int x1 = MathHelper.floor_double(Math.max(bogie0.posX, bogie1.posX));
                int y1 = MathHelper.floor_double(Math.max(bogie0.posY, bogie1.posY));
                int z1 = MathHelper.floor_double(Math.max(bogie0.posZ, bogie1.posZ));

                RailProperty rp = _tempRail.getProperty();

                if (Arrays.stream(_tempRail.getAllRailMaps()).map(railMap -> railMap.getRailBlockList(rp)).flatMap(List::stream).noneMatch(pos ->
                        pos[0] >= x0 && pos[0] <= x1 && pos[1] >= y0 - 2 && pos[1] <= y1 + 1 && pos[2] >= z0 && pos[2] <= z1
                )) {
                    break;
                }
            }
            RailMap _tempMap = this.getNearRailMap(_tempRail, railBase);
            _tempRailPosition = this.getFarRailPotion(_tempRailPosition, _tempMap);
            distance = distance + _tempMap.getLength();
        }
        return distance - train.getModelSet().getConfig().trainDistance;
    }

    private double getPattern(double distance) {
        return Math.sqrt((1.4f * 3.6f) * 7.2f * (distance));
    }

    private double getBreakingDistance(float trainSpeedT) {
        float trainSpeedH = trainSpeedT * 72f + 20f;
        return Math.pow(trainSpeedH, 2) / ((0.8f * 3.6f) * 7.2f);
    }

    private RailPosition getRailPositionDestination(RailMap railMap) {
        if (railMap.getStartRP() == this.nowRP) {
            return railMap.getEndRP();
        } else {
            return railMap.getStartRP();
        }
    }

    private TileEntityLargeRailBase getNextRailBase(World world, RailPosition railPosition) {
        TileEntity tile = world.getTileEntity(
                MathHelper.floor_double(railPosition.posX + RailPosition.REVISION[railPosition.direction][0]),
                railPosition.blockY,
                MathHelper.floor_double(railPosition.posZ + RailPosition.REVISION[railPosition.direction][1]));
        return tile instanceof TileEntityLargeRailBase ? (TileEntityLargeRailBase) tile : null;
    }

    private RailPosition getNearRailPoint(RailMap railMap) {
        RailPosition rp0 = railMap.getStartRP();
        RailPosition rp1 = railMap.getEndRP();
        double distance0 = train.getDistance(rp0.blockX, rp0.blockY, rp0.blockZ);
        double distance1 = train.getDistance(rp1.blockX, rp1.blockY, rp1.blockZ);
        return distance0 < distance1 ? rp0 : rp1;
    }

    private RailPosition getFarRailPotion(RailPosition railPosition0, RailMap railMap) {
        RailPosition rp0 = railMap.getStartRP();
        RailPosition rp1 = railMap.getEndRP();
        double distance0 = this.getRPToRP(railPosition0, rp0);
        double distance1 = this.getRPToRP(railPosition0, rp1);
        return distance0 < distance1 ? rp1 : rp0;
    }

    private double getRPToRP(RailPosition railPosition1, RailPosition railPosition2) {
        double rightSquaredValue = 0;
        rightSquaredValue += Math.pow(railPosition1.blockX - railPosition2.blockX, 2);
        rightSquaredValue += Math.pow(railPosition1.blockY - railPosition2.blockY, 2);
        rightSquaredValue += Math.pow(railPosition1.blockZ - railPosition2.blockZ, 2);
        return Math.sqrt(rightSquaredValue);
    }
}
