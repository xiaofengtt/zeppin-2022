function JSKit(path){
	/*private member variable*/
	var _runTimer=null, _importManage=[], _path=path;

	/*public method*/
    JSKit.prototype.Import=function(file,isClass){
        var script;
		var jskit=this;

        if(!_importManage[file]){_importManage[file]="loading"};

        if(_importManage[file]!="loaded"){
			script=document.createElement("script");
			script.src =_path+file+".js";

			script.onreadystatechange=function(){
				if(this.readyState=='complete'||this.readyState=='loaded'){
					var lastClass=file.substring(file.lastIndexOf("/")+1,file.length);

					if(lastClass=="Import"||lastClass=="Run"){return};
					if (isClass){jskit[lastClass]=eval(lastClass);}

					_importManage[file]="loaded";
					script.onreadystatechange=null;
					return
				}
			};

			script.onload=function(){
				var lastClass=file.substring(file.lastIndexOf("/")+1,file.length);

				if(lastClass=="Import"||lastClass=="Run"){return};
				if(isClass!=false){jskit[lastClass]=eval(lastClass);}

				_importManage[file]="loaded";
				script.onload=null;
			};

			document.getElementsByTagName("head")[0].appendChild(script);
		}
    };/*end of Import*/

	JSKit.prototype.Run=function(code){
		var loaded=true;
		var jskit=this;

		for(importFile in _importManage){
			if(_importManage[importFile]=="loading"){loaded=false;break}
		};

		if(!loaded){_runTimer=setTimeout(function(){jskit.Run(code)},200); return};
		if(_runTimer){clearTimeout(_runTimer)};
		if(typeof(code)=="function"){code()}
	};/*end of "Run"*/
}
