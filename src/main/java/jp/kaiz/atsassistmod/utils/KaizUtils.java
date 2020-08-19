package jp.kaiz.atsassistmod.utils;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public class KaizUtils {
	public static Enum getNextEnum(Enum e) {
		List enumList = EnumUtils.getEnumList(e.getClass());
		int index = enumList.indexOf(e);
		return (Enum) enumList.get(enumList.size() > index + 1 ? index + 1 : 0);
	}
}
