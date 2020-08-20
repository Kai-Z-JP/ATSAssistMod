package jp.kaiz.atsassistmod.ifttt;

public class IFTTTType {

	public enum This implements IFTTTEnumBase {
		Select(100, "選択");


		private final int id;
		private final String name;

		This(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

		public enum Minecraft implements IFTTTEnumBase {
			RedStoneInput(110, "RS信号入力"),
			Time(111, "Minecraft時間"),
			Light(112, "周辺光"),
			;

			private final int id;
			private final String name;

			Minecraft(int id, String name) {
				this.id = id;
				this.name = name;
			}

			@Override
			public int getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}
		}

		public enum RTM implements IFTTTEnumBase {
			OnTrain(120, "単純車両検知"),
			Cars(121, "両数"),
			Speed(122, "速度"),
			TrainState(123, "TrainState"),
			DataMap(124, "DataMap"),
			;

			private final int id;
			private final String name;

			RTM(int id, String name) {
				this.id = id;
				this.name = name;
			}

			@Override
			public int getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}
		}

		public enum ATSAssist implements IFTTTEnumBase {
			CODD(130, "踏切障検"),
			;

			private final int id;
			private final String name;

			ATSAssist(int id, String name) {
				this.id = id;
				this.name = name;
			}


			@Override
			public int getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}
		}


	}

	public enum That implements IFTTTEnumBase {
		Select(200, "選択");

		private final int id;
		private final String name;

		That(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

		public enum Minecraft implements IFTTTEnumBase {
			RedStoneOutput(210, "RS信号出力"),
			PlaySound(211, "音声再生"),
			ExecuteCommand(212, "コマンド実行"),
			;

			private final int id;
			private final String name;

			Minecraft(int id, String name) {
				this.id = id;
				this.name = name;
			}

			@Override
			public int getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}
		}

		public enum RTM implements IFTTTEnumBase {
			TrainState(220, "TrainState"),
			DataMap(221, "DataMap"),
			;

			private final int id;
			private final String name;

			RTM(int id, String name) {
				this.id = id;
				this.name = name;
			}

			@Override
			public int getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}
		}

		public enum ATSAssist implements IFTTTEnumBase {
			;

			private final int id;
			private final String name;

			ATSAssist(int id, String name) {
				this.id = id;
				this.name = name;
			}

			@Override
			public int getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}
		}
	}

	public interface IFTTTEnumBase {
		int getId();

		String getName();
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
