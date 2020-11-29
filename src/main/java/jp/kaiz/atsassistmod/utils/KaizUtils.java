package jp.kaiz.atsassistmod.utils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public class KaizUtils {
	public static Enum<?> getNextEnum(Enum<?> e) {
		List<?> enumList = EnumUtils.getEnumList(e.getDeclaringClass());
		int index = enumList.indexOf(e);
		return (Enum<?>) enumList.get(enumList.size() > index + 1 ? index + 1 : 0);
	}

	public static boolean isServer() {
		return FMLCommonHandler.instance().getSide() == Side.SERVER;
	}
}
