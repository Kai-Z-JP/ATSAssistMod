package jp.kaiz.atsassistmod.ifttt;

import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.utils.ComparisonManager;
import jp.kaiz.atsassistmod.utils.KaizUtils;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.entity.train.parts.EntityFloor;
import jp.ngt.rtm.modelpack.state.DataMap;
import jp.ngt.rtm.modelpack.state.DataType;
import jp.ngt.rtm.modelpack.state.ResourceState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;

import java.io.Serializable;

public abstract class IFTTTContainer implements Serializable {

	private static final long serialVersionUID = -2781244534093360974L;
	protected boolean once;

	public IFTTTContainer() {
	}

	public abstract IFTTTType.IFTTTEnumBase getType();

	public String getTitle() {
		return this.getType().getName();
	}

	public abstract String[] getExplanation();

	public void setOnce(boolean once) {
		this.once = once;
	}

	public boolean isOnce() {
		return once;
	}

	public abstract static class This extends IFTTTContainer {

		private static final long serialVersionUID = 4458077452851594500L;

		public abstract boolean isCondition(TileEntityIFTTT tile, EntityTrainBase train);

		public abstract static class Minecraft {
			public static class RedStoneInput extends This {
				private static final long serialVersionUID = 2180620082205172167L;

				public enum ModeType {
					ON("ON", false),
					OFF("OFF", false),
					EQUAL("==", true),
					GREATER_THAN(">", true),
					GREATER_EQUAL(">=", true),
					LESS_THAN("<", true),
					LESS_EQUAL("<=", true),
					NOT_EQUAL("!=", true);
					public final String name;
					public final boolean needStr;

					ModeType(String name, boolean needStr) {
						this.name = name;
						this.needStr = needStr;
					}
				}

				private int value;
				private ModeType mode;

				//0: ON
				//1: OFF
				//2: ==
				//3: >
				//4: >=
				//5: <
				//6: <=
				//7: !=

				public RedStoneInput() {
					this.mode = ModeType.ON;
					this.value = 0;
				}

				public ModeType getMode() {
					return this.mode;
				}

				public int getValue() {
					return this.value;
				}

				public void setMode(ModeType mode) {
					this.mode = mode;
				}

				public void setValue(int value) {
					this.value = value;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.This.Minecraft.RedStoneInput;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{"RSInput" + this.mode.name + (this.mode.needStr ? this.value : "")};
				}

				@Override
				public boolean isCondition(TileEntityIFTTT tile, EntityTrainBase train) {
					int power = tile.getWorldObj().getStrongestIndirectPower(tile.xCoord, tile.yCoord, tile.zCoord);
					switch (this.mode) {
						case ON:
							return power > 0;
						case OFF:
							return power == 0;
						case EQUAL:
							return power == this.value;
						case GREATER_THAN:
							return power > this.value;
						case GREATER_EQUAL:
							return power >= this.value;
						case LESS_THAN:
							return power < this.value;
						case LESS_EQUAL:
							return power <= this.value;
						case NOT_EQUAL:
							return power != this.value;
						default:
							return false;
					}
				}
			}
		}

		public abstract static class RTM {
			public static class SimpleDetectTrain extends This {
				private static final long serialVersionUID = -6173509528806558810L;

				public enum DetectMode {
					All("ATSAssistMod.IFTTT.DeteceMode.0"),
					FirstCar("ATSAssistMod.IFTTT.DeteceMode.1"),
					LastCar("ATSAssistMod.IFTTT.DeteceMode.2");

					private final String name;

					DetectMode(String name) {
						this.name = name;
					}

					public String getDisplayName() {
						return I18n.format(this.name);
					}
				}

				private DetectMode detectMode;

				public SimpleDetectTrain() {
					this.detectMode = DetectMode.All;
				}

				public DetectMode getDetectMode() {
					return detectMode;
				}

				public void setDetectMode(DetectMode detectMode) {
					this.detectMode = detectMode;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.This.RTM.OnTrain;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{I18n.format("ATSAssistMod.IFTTT.DeteceMode.name") + ": " + I18n.format(this.detectMode.name)};
				}

				@Override
				public boolean isCondition(TileEntityIFTTT tile, EntityTrainBase train) {
					switch (this.detectMode) {
						case All:
							return train != null;
						case FirstCar:
							return train != null && train.isControlCar();
						case LastCar:
							return train != null && (train.getFormation().size() == 1 || (!train.isControlCar() && (train.getConnectedTrain(0) == null || train.getConnectedTrain(1) == null)));
					}
					return false;
				}
			}

			public static class TrainDataMap extends This {
				private static final long serialVersionUID = -8546481139822877274L;
				private DataType dataType;
				private String key;
				private Object value;
				private ComparisonManager.ComparisonBase comparisonType;

				public TrainDataMap() {
					this.setDataType(DataType.BOOLEAN);
					this.key = "";
					this.value = "";
				}

				public DataType getDataType() {
					return dataType;
				}

				public void setDataType(DataType dataType) {
					this.dataType = dataType;
					switch (dataType) {
						case HEX:
						case INT:
							this.comparisonType = ComparisonManager.Integer.EQUAL;
							break;
						case DOUBLE:
							this.comparisonType = ComparisonManager.Double.EQUAL;
							break;
						case STRING:
							this.comparisonType = ComparisonManager.String.EQUAL;
							break;
						case VEC:
							this.comparisonType = ComparisonManager.Vec3.EQUAL;
							break;
						case BOOLEAN:
						default:
							this.comparisonType = ComparisonManager.Boolean.TRUE;
							break;
					}
				}

				public void nextDataType() {
					this.setDataType((DataType) KaizUtils.getNextEnum(this.dataType));
				}

				public void nextComparisonType() {
					this.comparisonType = (ComparisonManager.ComparisonBase)
							KaizUtils.getNextEnum((Enum) this.comparisonType);
				}

				public ComparisonManager.ComparisonBase getComparisonType() {
					return this.comparisonType;
				}

				public String getKey() {
					return key;
				}

				public void setKey(String key) {
					this.key = key;
				}

				public Object getValue() {
					return value;
				}

				public void setValue(String value) {
					this.value = this.comparisonType.parseT(value);
				}

				@Override
				public String getTitle() {
					return this.getType().getName() + " " + this.dataType.key;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.This.RTM.TrainDataMap;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{
							"Key: " + this.key,
							"Value" + this.comparisonType.getName() + ((this.dataType == (DataType.BOOLEAN)) ? "" : this.value)
					};
				}

				@Override
				public boolean isCondition(TileEntityIFTTT tile, EntityTrainBase train) {
					if (train != null) {
						ResourceState resourceState = train.getResourceState();
						if (resourceState == null) {
							return false;
						}
						DataMap dataMap = resourceState.getDataMap();
						Object dv;
						switch (this.dataType) {
							case HEX:
								dv = dataMap.getHex(this.key);
								break;
							case INT:
								dv = dataMap.getInt(this.key);
								break;
							case DOUBLE:
								dv = dataMap.getDouble(this.key);
								break;
							case STRING:
								dv = dataMap.getString(this.key);
								break;
							case VEC:
								dv = dataMap.getVec(this.key);
								break;
							case BOOLEAN:
								dv = dataMap.getBoolean(this.key);
								break;
							default:
								return false;
						}
						return this.comparisonType.isTrue(dv, this.value);
					}
					return false;
				}
			}
		}

		public abstract static class ATSAssist {
			public static class CrossingObstacleDetection extends This {
				private static final long serialVersionUID = -2345201548431087396L;
				private int[] startCC = new int[]{0, 0, 0};
				private int[] endCC = new int[]{0, 0, 0};

				public void setStartCC(int x, int y, int z) {
					this.startCC = new int[]{x, y, z};
				}

				public int[] getStartCC() {
					return this.startCC;
				}

				public void setEndCC(int x, int y, int z) {
					this.endCC = new int[]{x, y, z};
				}

				public int[] getEndCC() {
					return this.endCC;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.This.ATSAssist.CODD;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{
							java.lang.String.format("x:%s, y:%s, z:%s", this.startCC[0], this.startCC[1], this.startCC[2]),
							java.lang.String.format("x:%s, y:%s, z:%s", this.endCC[0], this.endCC[1], this.endCC[2])
					};
				}

				@Override
				public boolean isCondition(TileEntityIFTTT tile, EntityTrainBase train) {
					return tile.getWorldObj().getEntitiesWithinAABB(EntityLiving.class.getSuperclass(), AxisAlignedBB.getBoundingBox(
							Math.min(this.startCC[0], this.endCC[0]), Math.min(this.startCC[1], this.endCC[1]), Math.min(this.startCC[2], this.endCC[2]),
							Math.max(this.startCC[0], this.endCC[0]), Math.max(this.startCC[1], this.endCC[1]), Math.max(this.startCC[2], this.endCC[2])
					)).stream().anyMatch(o ->
							!((((Entity) o).ridingEntity instanceof EntityTrainBase) || (((Entity) o).ridingEntity instanceof EntityFloor)));
				}
			}
		}
	}

	public abstract static class That extends IFTTTContainer {
		private static final long serialVersionUID = 3885084343670120809L;

		public abstract void doThat(TileEntityIFTTT tile, EntityTrainBase train, boolean first);

		public abstract static class Minecraft {
			public static class RedStoneOutput extends That {
				private static final long serialVersionUID = -4456412974039197107L;
				private boolean trainCarsOutput;
				private int outputLevel;

				public void setTrainCarsOutput(boolean value) {
					this.trainCarsOutput = value;
				}

				public boolean isTrainCarsOutput() {
					return this.trainCarsOutput;
				}

				public void setOutputLevel(int outputLevel) {
					this.outputLevel = outputLevel;
				}

				public int getOutputLevel() {
					return outputLevel;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.That.Minecraft.RedStoneOutput;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{"Output: " + (this.isTrainCarsOutput() ? "編成両数" : this.outputLevel)};
				}

				@Override
				public void doThat(TileEntityIFTTT tile, EntityTrainBase train, boolean first) {
					if (this.isTrainCarsOutput()) {
						if (train != null) {
							tile.setRedStoneOutput(train.getFormation().entries.length);
						} else {
							tile.setRedStoneOutput(0);
						}
					} else {
						tile.setRedStoneOutput(this.getOutputLevel());
					}
				}
			}

			public static class ExecuteCommand extends That {
				private static final long serialVersionUID = -83401892282647225L;
				private String command = "";
				private String result = "";

				public void setCommand(String command) {
					this.command = command;
				}

				public String getCommand() {
					return command;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.That.Minecraft.ExecuteCommand;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{I18n.format("ATSAssistMod.gui.IFTTTMaterial.212.1") + ": " + this.command};
				}

				@Override
				public void doThat(TileEntityIFTTT tile, EntityTrainBase train, boolean first) {
					if (!this.once || first) {
						new IFTTTCommandSender(tile).executeCommand(this.command);
					}
				}
			}
		}

		public abstract static class RTM {
			public static class DataMap extends That {
				private static final long serialVersionUID = -5927011065086566182L;

				private DataType dataType;
				private String key, value;

				public DataMap() {
					this.dataType = DataType.STRING;
					this.key = "";
					this.value = "";
				}

				public DataType getDataType() {
					return dataType;
				}

				public void setDataType(DataType dataType) {
					this.dataType = dataType;
				}

				public String getKey() {
					return key;
				}

				public void setKey(String key) {
					this.key = key;
				}

				public String getValue() {
					return value;
				}

				public void setValue(String value) {
					this.value = value;
				}

				@Override
				public IFTTTType.IFTTTEnumBase getType() {
					return IFTTTType.That.RTM.TrainDataMap;
				}

				@Override
				public String getTitle() {
					return this.getType().getName() + " " + this.dataType.key;
				}

				@Override
				public String[] getExplanation() {
					return new String[]{"Key: " + this.key, "Value: " + this.value};
				}

				@Override
				public void doThat(TileEntityIFTTT tile, EntityTrainBase train, boolean first) {
					if (train != null) {
						ResourceState resourceState = train.getResourceState();
						if (resourceState == null) {
							return;
						}
						jp.ngt.rtm.modelpack.state.DataMap dataMap = resourceState.getDataMap();
						try {
							switch (this.dataType) {
								case BOOLEAN:
									dataMap.setBoolean(this.key, Boolean.parseBoolean(this.value), 1);
									break;
								case DOUBLE:
									dataMap.setDouble(this.key, Double.parseDouble(this.value), 1);
									break;
								case INT:
									dataMap.setInt(this.key, Integer.parseInt(this.value), 1);
									break;
								case STRING:
									dataMap.setString(this.key, this.value, 1);
									break;
								case VEC:
									String[] vecs = this.value.split(",");
									dataMap.setVec(this.key,
											new jp.ngt.ngtlib.math.Vec3(Double.parseDouble(vecs[0]), Double.parseDouble(vecs[1]), Double.parseDouble(vecs[2])), 1);
									break;
								case HEX:
									dataMap.setHex(this.key, Integer.parseInt(this.value), 1);
									break;
								default:
									break;
							}
						} catch (Exception ignored) {
						}
					}
				}
			}
		}
	}
}
