package jp.kaiz.atsassistmod.block;

import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;

public enum GroundUnitType {
    //多分tileでid返したほうがいいけど見た目的にこのほうがいい
    None(0) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.None();
        }
    }, ATC_SpeedLimit_Notice(1) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ATCSpeedLimitNotice();
        }
    }, ATC_SpeedLimit_Cancel(2) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ATCSpeedLimitCancel();
        }
    }, ATC_SpeedLimit_Reset(3) {
       @Override
       public TileEntityGroundUnit getNewInstance() {return new TileEntityGroundUnit.ATCSpeedLimitReset();}
    }, TASC_StopPotion_Notice(4) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.TASCStopPositionNotice();
        }
    }, TASC_Cancel(5) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.TASCDisable();
        }
    }, TASC_StopPotion_Correction(6) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.TASCStopPositionCorrection();
        }
    }, TASC_StopPotion(7) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.TASCStopPosition();
        }
    }, ATO_Departure_Signal(9) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ATODepartureSignal();
        }
    }, ATO_Cancel(10) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ATODisable();
        }
    }, ATO_Change_Speed(11) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ATOChangeSpeed();
        }
    }, TrainState_Set(13) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.TrainStateSet();
        }
    }, CHANGE_TP(14) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ChangeTrainProtection();
        }
    }, ATACS_Disable(15) {
        @Override
        public TileEntityGroundUnit getNewInstance() {
            return new TileEntityGroundUnit.ATACSDisable();
        }
    };

    public final int id;

    GroundUnitType(int id) {
        this.id = id;
    }

    public static GroundUnitType getType(int par1) {
        for (GroundUnitType type : GroundUnitType.values()) {
            if (type.id == par1) {
                return type;
            }
        }
        return None;
    }

    public abstract TileEntityGroundUnit getNewInstance();
}
