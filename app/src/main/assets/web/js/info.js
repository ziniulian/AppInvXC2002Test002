function init() {
	rfid.flushTim();
	dat.tag = JSON.parse(dat.getUrlReq().tag);
	dat.flush();

	// 循环测试
	setTimeout (function () {
		location.href = "read.html";
	}, 300);
}

dat = {
	tag: null,

	getUrlReq: function () {
		var url = location.search;
		var theRequest = {};
		if (url.indexOf("?") != -1) {
			url = url.substr(1).split("&");
			for(var i = 0; i < url.length; i ++) {
				var str = url[i].split("=");
				theRequest[str[0]] = decodeURIComponent(str[1]);
			}
		}
		return theRequest;
	},

	sav: function () {
		if (dat.tag.xiu) {
			rfid.dbSav(dat.tag.cod, dat.tag.xiu.src);
		} else {
			rfid.dbSav(dat.tag.cod, "");
		}
		location.href = "read.html";
	},

	back: function () {
		location.href = "home.html";
	}

};
