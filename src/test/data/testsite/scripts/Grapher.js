var f = document.getElementById("form");
//var x = document.getElementById("x");
var y = document.getElementById("y");
var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");

var xStart = 0;
var yStart = 0;

var xOffsetPoint = c.width/2;
var yOffsetPoint = c.height/2;

function draw() {

	xStart = 0;
	yStart = 0;

	var x;
	
	for(x = 1; x < 800; x++) {
	
		var newX = x;
		var newY = mathFunction(x,y.value);
	
		MakeLine(xStart,yStart,newX,newY);
		xStart = newX;
		yStart = newY;
	}
}

function doClear() {

	c.width = 800;
	c.height = 600;
	xStart = 0;
	yStart = 0;
	xOffsetPoint = c.width/2;
	yOffsetPoint = c.height/2;
}


function MakeLine(x1, y1, x2, y2) {
	
	
	ctx.moveTo(xOffsetPoint+x1,yOffsetPoint-y1);
	ctx.lineTo(xOffsetPoint+x2,yOffsetPoint-y2);
	ctx.stroke();
}

function mathFunction(x, functionString) {

	functionString.replace("x",x);
	/*
	var numbers = functionString.split("+");
	numbers.split("-");
	numbers.split("*");
	numbers.split("/");
	
	var operators = functionString.replace("0","");
	operators.replace("1","");
	operators.replace("2","");
	operators.replace("3","");
	operators.replace("4","");
	operators.replace("5","");
	operators.replace("6","");
	operators.replace("7","");
	operators.replace("8","");
	operators.replace("9","");
	
	var i;
	
	var calculation;
	
	// Multiplication and division before addition  and subtraction
	
	for(i = 0; i < operators.size; i++) {
	
		 
		if(operators.charAt(i) == '*') {
		
		} 
		if(operators.charAt(i) == '/') {
		
		}
	}
	
	
	for(i = 0; i < operators.size; i++) {
	
		 
		if(operators.charAt(i) == '+') {
		
		} 
		if(operators.charAt(i) == '-') {
		
		}
	}
	*/
	

	return parseInt(functionString);


}