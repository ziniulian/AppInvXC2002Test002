package com.invengo.train.xc2002test002.enums;

/**
 * Created by ziniulian on 2017/10/17.
 */

public enum EmUrl {
	Read("file:///android_asset/web/read.html"),
	Info("file:///android_asset/web/infoT.html"),
	HdRead("javascript: rfid.hdRead(<0>);"),
	Tim("javascript: rfid.tim(\"<0>\");"),
	ReadNull("javascript: dat.readNull();"),
	Reading("javascript: dat.reading();"),
	FlashLight("javascript: dat.flashlight(<0>);"),
	Err("file:///android_asset/web/err.html");

	private final String url;
	EmUrl(String u) {
		url = u;
	}
	@Override
	public String toString() {
		return url;
	}
}
