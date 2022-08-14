window.onload = function() {
	setTimeout(function() {
		rain.ajax({
			method: 'GET',
			url: './data.json',
			success: function(res) {
				var data = JSON.parse(res);
				rain.start(data);
			}
		});
	}, 8600);
	var p = document.getElementsByClassName("p");
	var i = 0;
	var t = setInterval(move, 1200);

	function move() {
		for(var j = 0; j < p.length; j++) {
			p[j].style.display = "none";
		}
		if(i >= p.length) {
			clearInterval(t);
			document.querySelector("#mid-box").style.display = "none";
			return false;
		}
		p[i].style.display = "block";
		p[i].classList.add("bounceIn");
		i++;
	}
}