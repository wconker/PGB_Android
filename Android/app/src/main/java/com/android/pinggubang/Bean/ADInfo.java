package com.android.pinggubang.Bean;

/**
 * 描述：广告信息</br>
 */
public class ADInfo {
	
	String id = "";
	String url = "";
	String content = "";
	String type = "";
	Integer drable=0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setdrawable(int _drable){
		this.drable=_drable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getdrawable() {
		return drable;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
