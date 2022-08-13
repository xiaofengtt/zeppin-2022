/**
 * Created by 沈智 on 2015/7/7.
 */
function circle(){
    this.radius=100;
    this.lineWidth=5;
    this.strokeStyle='green';
    this.lineCap='round';
}
circle.prototype.draw=function(ctx){
    ctx.beginPath();
    ctx.arc(250,250,this.radius,0,Math.PI*2,true);
    ctx.lineWidth=this.lineWidth;
    ctx.strokeStyle=this.strokeStyle;
    ctx.stroke();
}
function ring(startAngle,percernt){
 circle.call(this);
    this.startAngle = startAngle||3*Math.PI/2;
    this.percent = percernt;
}
ring.prototype=Object.create(circle.prototype);
ring.prototype.drawRing=function (ctx){
    ctx.beginPath();
    var anglePerSec = 2*Math.PI / (100 / this.percent);
    ctx.arc(250, 250, this.radius, this.startAngle, this.startAngle  , false); //这里的圆心坐标要和cirle的保持一致
    ctx.strokeStyle = that.fillStyle;
    ctx.lineCap = that.lineCap;
    ctx.stroke();
    ctx.closePath();
}
