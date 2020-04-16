package jp.kaiz.atsassistmod.controller.trainprotection;

import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.rail.TileEntityLargeRailBase;
import jp.ngt.rtm.rail.TileEntityLargeRailCore;
import jp.ngt.rtm.rail.TileEntityLargeRailSwitchCore;
import jp.ngt.rtm.rail.util.Point;
import jp.ngt.rtm.rail.util.RailMap;
import jp.ngt.rtm.rail.util.RailPosition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class ATACSController {
//	private int count;

	private TileEntityLargeRailCore savedRail;
	private RailPosition nowRP;
	private RailMap nowRM;

	private EntityTrainBase train;

	private double movedDistance;

	private int speed0 = Integer.MAX_VALUE;
	private int speed1 = Integer.MAX_VALUE;
	private int speed2 = Integer.MAX_VALUE;

	public void onTick(EntityTrainBase train, double distance) {
		this.train = train;
		double trainX = train.posX/*(train.boundingBox.maxX + train.boundingBox.minX) / 2d*/;
		double trainY = train.posY/*(train.boundingBox.maxY + train.boundingBox.minY) / 2d*/;
		double trainZ = train.posZ/*(train.boundingBox.maxZ + train.boundingBox.minZ) / 2d*/;
		TileEntityLargeRailBase nowRailBase = TileEntityLargeRailBase.getRailFromCoordinates(train.worldObj, trainX, trainY, trainZ);
		if (nowRailBase != null) {
			TileEntityLargeRailCore nowRailCore = nowRailBase.getRailCore();
			if (nowRailCore != null) {
				if (this.savedRail == null) {
					this.savedRail = nowRailCore;
					return;
				} else if (this.savedRail != nowRailCore) {
					this.savedRail = nowRailCore;
					movedDistance = 0d;
					this.nowRM = this.getNearRailMap(nowRailCore);
					this.nowRP = this.getNearRailPoint(this.nowRM);
				} else if (this.nowRP == null) {
					return;
				}
				movedDistance = movedDistance + distance;

//				if (this.count < 20) {
//					this.count++;
//					return;
//				} else {
//					this.count = 0;
//				}

				double necessaryDistance = this.getBreakingDistance(train.getSpeed());
				double anotherTrainDistance = this.getAnotherTrainDistance(necessaryDistance + 100d);
				if (anotherTrainDistance == -1d) {
					speed0 = Integer.MAX_VALUE;
					speed1 = Integer.MAX_VALUE;
					speed2 = Integer.MAX_VALUE;
				} else {
					double nowRailLength = this.nowRM.getLength();
					anotherTrainDistance = anotherTrainDistance + nowRailLength - movedDistance;

					if (anotherTrainDistance < 0d) {
//						this.count = 20;
						return;
					}

					if (anotherTrainDistance > 100d) {
						speed0 = (int) this.getPattern(anotherTrainDistance - 120d);
						speed1 = (int) this.getPattern(anotherTrainDistance - 110d);
						speed2 = (int) this.getPattern(anotherTrainDistance - 100d);
					} else {
						speed0 = 0;
						speed1 = 0;
						speed2 = 0;
					}
				}
			}
		}
	}

	public int getDisplaySpeed() {
		return speed0;
	}

	public int getPatternSpeed() {
		return speed1;
	}

	public int getEmergencySpeed() {
		return speed2;
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
		RailMap _tempMap = this.nowRM;
		while (true) {
			if (distance >= searchDistance) {
				return -1d;
			}
			TileEntityLargeRailBase railBase = this.getNextRailBase(_tempRail, _tempRailPosition, _tempMap);
			if (railBase == null) {
				return -1d;
			}
			_tempRail = railBase.getRailCore();
			if (_tempRail.isTrainOnRail()) {
				List<TileEntityLargeRailCore> tileList = new ArrayList<>();
				int x0 = MathHelper.floor_double(train.boundingBox.minX + 0.01D);
				int y0 = MathHelper.floor_double(train.boundingBox.minY + 0.01D);
				int z0 = MathHelper.floor_double(train.boundingBox.minZ + 0.01D);
				int x1 = MathHelper.floor_double(train.boundingBox.maxX - 0.01D);
				int y1 = MathHelper.floor_double(train.boundingBox.maxY - 0.01D);
				int z1 = MathHelper.floor_double(train.boundingBox.maxZ - 0.01D);
				for (int x = x0; x <= x1; ++x) {
					for (int y = y0; y <= y1; ++y) {
						for (int z = z0; z <= z1; ++z) {
							TileEntityLargeRailBase rail = TileEntityLargeRailBase.getRailFromCoordinates(train.worldObj, x, y, z);
							if (rail != null) {
								TileEntityLargeRailCore railCore = rail.getRailCore();
								if (railCore != null) {
									tileList.add(railCore);
								}
							}
						}
					}
				}
				if (!tileList.contains(_tempRail)) {
					break;
				}
			}
			_tempMap = this.getNearRailMap(_tempRail, railBase);
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

	private TileEntityLargeRailBase getNextRailBase(TileEntityLargeRailCore rail, RailPosition railPosition, RailMap railMap) {
		TileEntityLargeRailBase nextRail;
		int x = (int) railPosition.posX;
		int y = (int) railPosition.posY;
		int z = (int) railPosition.posZ;
		for (int dx = -1; dx < 2; dx++) {
			for (int dy = -1; dy < 2; dy++) {
				for (int dz = -1; dz < 2; dz++) {
					if ((nextRail = getRailBaseFromLocation(x + dx, y + dy, z + dz, rail)) != null) {
						for (RailMap _RailMap : nextRail.getRailCore().getAllRailMaps()) {
							if (_RailMap.canConnect(railMap)) {
								return nextRail;
							}
						}
					}
				}
			}
		}
		return null;
	}

	private TileEntityLargeRailBase getRailBaseFromLocation(int x, int y, int z, TileEntityLargeRailCore nowRail) {
		TileEntity tile;
		if ((tile = nowRail.getWorldObj().getTileEntity(x, y, z)) != null) {
			if (tile instanceof TileEntityLargeRailBase) {
				TileEntityLargeRailBase anotherRailTile = (TileEntityLargeRailBase) tile;
				if (anotherRailTile.getRailCore() != nowRail) {
					return anotherRailTile;
				}
			}
		}
		return null;
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
