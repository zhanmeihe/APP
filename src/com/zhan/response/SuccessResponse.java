package com.zhan.response;

import com.zhan.utils.Const;

 

public class SuccessResponse extends CommonResponse {

	private static final long serialVersionUID = -7927296760107523634L;

	public SuccessResponse() {
		this.setCode(Const.RESPONSE_CODE_SUCCESS);
		this.setMsg(Const.RESPONSE_CODE_SUCCESS_MESSAGE);
	}

}
