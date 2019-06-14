package com.zmm.sell.utils;

import com.zmm.sell.enums.CodeEnum;

/**
 * @Name EnumUtil
 * @Author 900045
 * @Created by 2019/6/13 0013
 */
public class EnumUtil {

	public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
		for (T each : enumClass.getEnumConstants()) {
			if (code.equals(each.getCode())) {
				return each;
			}
		}
		return null;
	}
}
