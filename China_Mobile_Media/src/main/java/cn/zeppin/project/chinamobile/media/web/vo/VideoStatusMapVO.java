package cn.zeppin.project.chinamobile.media.web.vo;

import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;

public class VideoStatusMapVO implements Entity {

	private static final long serialVersionUID = -8723547841304132957L;
	private Integer unchecked;
	private Integer checked;
	private Integer transcoding;
	private Integer uploaded;
	private Integer failed;
	private Integer deleted;
	
	public Integer getUnchecked() {
		return unchecked;
	}

	public void setUnchecked(Integer unchecked) {
		this.unchecked = unchecked;
	}
	
	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	
	public Integer getUploaded() {
		return uploaded;
	}

	public void setUploaded(Integer uploaded) {
		this.uploaded = uploaded;
	}
	
	public Integer getTranscoding() {
		return transcoding;
	}

	public void setTranscoding(Integer transcoding) {
		this.transcoding = transcoding;
	}
	
	public Integer getFailed() {
		return failed;
	}

	public void setFailed(Integer failed) {
		this.failed = failed;
	}
	
	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
