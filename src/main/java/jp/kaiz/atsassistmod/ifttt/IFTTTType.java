package jp.kaiz.atsassistmod.ifttt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;

public class IFTTTType {

    public enum This implements IFTTTEnumBase {
        Select(100);

        private final int id;

        This(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }

        public enum Minecraft implements IFTTTEnumBase {
            RedStoneInput(110),
//            Time(111),
//            Light(112),
            ;

            private final int id;

            Minecraft(int id) {
                this.id = id;
            }

            @Override
            public int getId() {
                return id;
            }
        }

        public enum RTM implements IFTTTEnumBase {
            OnTrain(120),
            Cars(121),
            Speed(122),
            //            TrainState(123),
            TrainDataMap(124),
            TrainDirection(125),
            ;

            private final int id;

            RTM(int id) {
                this.id = id;
            }

            @Override
            public int getId() {
                return id;
            }
        }

        public enum ATSAssist implements IFTTTEnumBase {
            CODD(130),
//            JavaScript(131)
            ;

            private final int id;

            ATSAssist(int id) {
                this.id = id;
            }

            @Override
            public int getId() {
                return id;
            }
        }
    }

    public enum That implements IFTTTEnumBase {
        Select(200);

        private final int id;

        That(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }

        public enum Minecraft implements IFTTTEnumBase {
            RedStoneOutput(210),
            PlaySound(211),
            ExecuteCommand(212),
            SetBlock(213),
            ;

            private final int id;

            Minecraft(int id) {
                this.id = id;
            }

            @Override
            public int getId() {
                return id;
            }
        }

        public enum RTM implements IFTTTEnumBase {
//            TrainState(220),
            TrainDataMap(221),
            //            BlockDataMap(222),
            Signal(223),
            ;

            private final int id;

            RTM(int id) {
                this.id = id;
            }

            @Override
            public int getId() {
                return id;
            }
        }

        public enum ATSAssist implements IFTTTEnumBase {
            JavaScript(230),
            ;

            private final int id;

            ATSAssist(int id) {
                this.id = id;
            }

            @Override
            public int getId() {
                return id;
            }
        }
    }

    public interface IFTTTEnumBase {
        int getId();

        @SideOnly(Side.CLIENT)
        default String getName() {
            return I18n.format("ATSAssistMod.IFTTTType." + this.getId());
        }
    }

    public static IFTTTEnumBase getType(int par1) {
        if (par1 >= 100 && par1 < 106) {
            return This.Select;
        }
        for (IFTTTEnumBase type : This.Minecraft.values()) {
            if (type.getId() == par1) {
                return type;
            }
        }
        for (IFTTTEnumBase type : This.RTM.values()) {
            if (type.getId() == par1) {
                return type;
            }
        }
        for (IFTTTEnumBase type : This.ATSAssist.values()) {
            if (type.getId() == par1) {
                return type;
            }
        }
        if (par1 >= 200 && par1 < 206) {
            return That.Select;
        }
        for (IFTTTEnumBase type : That.Minecraft.values()) {
            if (type.getId() == par1) {
                return type;
            }
        }
        for (IFTTTEnumBase type : That.RTM.values()) {
            if (type.getId() == par1) {
                return type;
            }
        }
        for (IFTTTEnumBase type : That.ATSAssist.values()) {
            if (type.getId() == par1) {
                return type;
            }
        }
        return null;
    }
}
