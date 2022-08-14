$(document).ready(function() {
	banner();
	getList();
	function getList() {
		$.ajax({
				type: "get",
				url: api + "/product/list",
				async: true,
				data: {
					"token": token(),
					"pageSize":1000,
					"pageNum":"1",
					"sorts":"target_annualized_return_rate-desc"
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					if(r.totalResultCount > 0){
						var template = $.templates("#queboxTpl");
						var html = template.render(r.data);
						$("#queboxCnt").html(html);
					}else{
						$("#queboxCnt").html("<img class='no-data-img' src='./img/ic_empty.png'><p class='no-data-msg'>暂时没有任何数据</p>");
					}
					
					appendOpenId();
				} else {
					if(r.status != "ERROR") {
						layerIn(r.message);
					} else {
						isWechat();
					}
				}
				loadingOut();
			})
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
			});
	}

	
}); //$(document).ready

function banner() {

	//1 获取变量
	// 屏幕的宽度
	var width = document.body.offsetWidth;
	// console.log(width);\

	//  获取 轮播图的ul
	var moveUl = document.querySelector('.banner_images');

	// 添加过度效果 由于后面已经设置了 所以 这里 已经没有意义了
	// moveUl.style.transition = 'all .3s';

	// 索引的li标签
	var indexLiArr = document.querySelectorAll('.banner_index li');

	// 定义 index 记录 当前的 索引值
	// 默认 我们的ul 已经 往左边 移动了 一倍的宽度
	// (为什么 一位 最左边的图片 是用来做无限轮播的 不希望用户看到) 所以 index =1
	var index = 1;

	// 开启定时器
	var timeId = setInterval(function() {
		// 累加
		index++;

		// 只要进来 就开启过渡 保证 过渡效果一直存在
		moveUl.style.webkitTransition = 'all .3s';

		// 修改 ul的位置
		moveUl.style.webkitTransform = 'translateX(' + index * width * -1 + 'px)';

	}, 3000);

	// 过渡 结束事件 用来 修正 index的值 并修改索引
	moveUl.addEventListener('webkitTransitionEnd', function() {
		//		console.log('过渡结束');

		//  如果 index 太大了 
		if(index > 3) {
			index = 1;

			// 关闭过渡
			moveUl.style.webkitTransition = '';

			// 瞬间 修改一下 ul 的位置
			moveUl.style.webkitTransform = 'translateX(' + index * width * -1 + 'px)';
		} else if(index < 1) {
			// 跳到倒数第二张
			index = 3;

			// 关闭过渡
			moveUl.style.webkitTransition = '';

			// 瞬间 修改一下 ul 的位置
			moveUl.style.webkitTransform = 'translateX(' + index * width * -1 + 'px)';
		}

		// 修改 索引li标签的 class
		for(var i = 0; i < indexLiArr.length; i++) {
			indexLiArr[i].className = '';
		}

		// 有一个 1的 差值
		indexLiArr[index - 1].className = 'current';

	})

	// 注册 三个 touch事件

	// 定义变量 记录 开始的X
	var startX = 0;

	// 记录移动的值
	var moveX = 0;

	// 记录 distanceX
	var distanceX = 0;

	// 触摸开始
	moveUl.addEventListener('touchstart', function(event) {
		$("body").on('touchmove', function(event) {
			event.preventDefault();
		});
		// 关闭定时器
		clearInterval(timeId);

		// 关闭过渡效果
		moveUl.style.webkitTransition = '';

		// 记录开始值
		startX = event.touches[0].clientX;

	});

	// 触摸中
	moveUl.addEventListener('touchmove', function(event) {
		// 计算移动的值
		moveX = event.touches[0].clientX - startX;

		// 移动ul
		// 默认的移动值是 index*-1*width 
		moveUl.style.webkitTransform = 'translateX(' + (moveX + index * -1 * width) + 'px)';
	})

	// 触摸结束
	/*
		手指松开的时候 判断 移动的距离 进行 是否吸附
			由于 不需要考虑 正负 只需要考虑 距离 Math.abs()
				吸附回的值是 index*-1*width
			如果移动的距离较大
				需要判断正负
					index++;
					index--;
					 index*-1*width
	*/
	moveUl.addEventListener('touchend', function(event) {
		$("body").off('touchmove');
		// 定义 最大的 偏移值
		var maxDistance = width / 3;

		// 判断 是否超过
		if(Math.abs(moveX) > maxDistance) {
			// 判断 到底是 往左 还是往右移动
			if(moveX > 0) {
				index--;
			} else {
				index++;
			}
			// 为了好看 将 过渡效果开启
			moveUl.style.webkitTransition = 'all .3s';

			// 吸附 一整页
			moveUl.style.webkitTransform = 'translateX(' + (index * -1 * width) + 'px)';

		} else {
			// 如果 进到这里了 说明 没有超过 我们定义的 最大偏移值 吸附回去即可

			// 为了好看 将 过渡效果开启
			moveUl.style.webkitTransition = 'all .3s';

			// 吸附回去
			moveUl.style.webkitTransform = 'translateX(' + (index * -1 * width) + 'px)';
		}

		// 记录结束值

		// 开启定时器
		timeId = setInterval(function() {
			// 累加
			index++;

			// 将 过渡开启 管你三七二十一 只要进来 就开启过渡 保证 过渡效果一直存在
			moveUl.style.webkitTransition = 'all .3s';

			// 修改 ul的位置
			moveUl.style.webkitTransform = 'translateX(' + index * width * -1 + 'px)';
		}, 3000)
	})

}