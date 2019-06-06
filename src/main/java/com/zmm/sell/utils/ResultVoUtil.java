package com.zmm.sell.utils;

import com.zmm.sell.vo.ResultVo;

/**
 * @Name ResultVoUtil
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
public class ResultVoUtil {

	/**
	 * 成功
	 *
	 * @param object
	 * @return
	 */
	public static ResultVo success(Object object) {
		ResultVo resultVO = new ResultVo();
		resultVO.setDate(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}

	public static ResultVo success() {
		return success(null);
	}

	/**
	 * 失败
	 *
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResultVo error(Integer code, String msg) {
		ResultVo resultVO = new ResultVo();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}
}
