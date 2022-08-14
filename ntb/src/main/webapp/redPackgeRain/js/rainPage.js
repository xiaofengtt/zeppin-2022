var g = function(a) {
	return document.getElementById(a)
};
var ua = navigator.userAgent.toLowerCase();
var el = g("box"),
	redCount = 0,
	redIdArr = [],
	redAmountArr = [],
	allAmount = 0;
var rain = new redPack({
	el: el,
	speed: 10,
	density: 200,
	callback: function(a) {
		redIdArr.push(a.target.getAttribute("redId"));
		redAmountArr.push(a.target.getAttribute("redAmount"));
		allAmount += Number(a.target.getAttribute("redAmount"));
		a.target.innerHTML = "<span style='color:#faee00'>+" + a.target.getAttribute("redAmount") + "元</span>";
		a.target.style.backgroundImage = "none";
		redCount++;
		g("redCount").innerText = (allAmount).toFixed(2);
	}
});
var count = 15
var timer;
setTimeout(function() {
	timer = setInterval(Count, 1000);
}, 7500);

function Count() {
	g("countDown").innerText = count;
	count--;
	if(count < 0) {
		clearInterval(timer);
		rain.stop();
		rain.clear();
		showPop()
	}
}

function showPop() {
	g("mask").style.display = "block";
	g("pop").style.display = "block";
	g("redCount1").innerText = redCount;

	function a(b) {
		allAmount = 0;
		if(b.length === 0) {
			return 0
		} else {
			if(b.length === 1) {
				allAmount += Number(b[0]);
				return allAmount
			} else {
				for(var c = 0; c < b.length; c++) {
					allAmount += Number(b[c])
				}
				return allAmount
			}
		}
	}
	a(redAmountArr);
	g("allAmount").innerText = (allAmount).toFixed(2);
	var enResult = strEnc(g("allAmount").innerText, "fuck");

	document.getElementById("shareBtn").onclick = function() {
		window.location.href = "./register.html?price=" + enResult;
	}
}
g("closeBtn").addEventListener("click", function() {
	closePop()
});

function closePop() {
	g("mask").style.display = "none";
	g("pop").style.display = "none"
};