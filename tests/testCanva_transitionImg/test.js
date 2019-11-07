

var previous_image = null;
var current_image = null;
var next_image = null;

var current_image_index = -1;
var listImg = new Array();
var listImgNormal = new Array();
var canvas = null;
var ctx = null;

var width = 480;
var height = 270;

var intervalHandler = null;

$(document).ready(function()
{
	setTimeout(init, 1000);
});

function init()
{
	canvas = $(".div_canva")[0];
	ctx = canvas.getContext("2d");
	
	var listJImg = $(".imgSource");
	for(var i=0; i < listJImg.length; i++)
	{
		ctx.drawImage(listJImg[i], 0, 0, width, height, 0, 0, listJImg[i].width, listJImg[i].height);
		listImg.push( ctx.getImageData(0, 0, width, height) );
	}
	
	var listJImgNormal = $(".imgSourceNormal");
	for(var i=0; i < listJImgNormal.length; i++)
	{
		ctx.drawImage(listJImgNormal[i], 0, 0, width, height, 0, 0, listJImgNormal[i].width, listJImgNormal[i].height);
		listImgNormal.push( ctx.getImageData(0, 0, width, height) );
	}
	
	$(".cb_methodInterpolation").change(function()
	{
		if($(this).val()=="Linear_Kikoulol")
			$(".cb_normal").show();
		else
			$(".cb_normal").hide();
	});
	if($(".cb_methodInterpolation").val()=="Linear_Kikoulol")
		$(".cb_normal").show();
	else
		$(".cb_normal").hide();
	
	if(listImg.length!=0)
	{
		current_image_index = 0;
		next_image = listImg[current_image_index];
	}
	current_image = ctx.createImageData(width, height);
	previous_image = ctx.createImageData(width, height);
	current_image.data.set(new Uint8ClampedArray(next_image.data.buffer));
	previous_image.data.set(new Uint8ClampedArray(next_image.data.buffer));
	
	
	redraw();
	
	$(".bt_click_area").click(function()
	{
		if(listImg.length==0)
			return;
		
		previous_image.data.set(new Uint8ClampedArray(current_image.data.buffer));
		
		current_image_index++;
		if(current_image_index >= listImg.length)
			current_image_index = 0;
		
		next_image = listImg[current_image_index];
		
		update_currentImg();
	});
}

function update_currentImg()
{
	var context = this;
	var duration = 1000;							//millisecondes
	var frameDuration = 40;
	var inc_duration = 0;
	var nbPixels = width * height;
	
	
	var methodInterpolation = $(".cb_methodInterpolation").val();
	
	
	if(intervalHandler!=null)
	{
		clearInterval(intervalHandler);
		intervalHandler = null;
	}
	
	if(methodInterpolation=="NoInterpolation")
	{
		current_image.data.set(new Uint8ClampedArray(next_image.data.buffer));
		redraw();
		
	}else if(methodInterpolation=="Linear_Fade"){
		
		intervalHandler = setInterval(function()
		{
			if(inc_duration > duration)
			{
				clearInterval(intervalHandler);
				intervalHandler = null;
				return;
			}
			
			var factor = inc_duration / duration;
			
			var pixel_prev, pixel_next, pixel_current;
			for(var i=0;i<nbPixels;i++)
			{
				for(var j=0;j<4;j++)			//RGBA
				{
					var pos =  i * 4 + j;
					
					pixel_prev = previous_image.data[pos];
					pixel_next = next_image.data[pos];
					pixel_current = Math.floor( (pixel_next - pixel_prev) * factor + pixel_prev);
					
					current_image.data[pos] = pixel_current;
				}
			}
			
			redraw();
			
			inc_duration += frameDuration;
		}, frameDuration);
		
		
	}else if(methodInterpolation=="Linear_Balayage"){
		
		intervalHandler = setInterval(function()
		{
			if(inc_duration > duration)
			{
				clearInterval(intervalHandler);
				intervalHandler = null;
				return;
			}
			
			var factor = inc_duration / duration;
			
			var pixel_prev, pixel_next, pixel_current;
			for(var i=0;i<width;i++)
			{
				for(var j=0;j<height;j++)
				{
					for(var k=0;k<4;k++)			//RGBA
					{
						var pos =  (i + previous_image.width * j) * 4 + k;
						
						pixel_prev = previous_image.data[pos];
						pixel_next = next_image.data[pos];
						
						pixel_current = (i < factor * width) ? pixel_next : pixel_prev;
						
						current_image.data[pos] = pixel_current;
					}
				}
			}
			
			redraw();
			
			inc_duration += frameDuration;
		}, frameDuration);
		
		
	}else if(methodInterpolation=="Linear_Balayage_Book_0"){
		
		var factor_offset = 0.2;
		var aspectRatio = height/width;
		//var aspectRatio = 1.0;
		var color_otherSide_multiplicator = 0.85;
		
		intervalHandler = setInterval(function()
		{
			if(inc_duration > duration)
			{
				clearInterval(intervalHandler);
				intervalHandler = null;
				return;
			}
			
			var factor = inc_duration / duration;
			
			var factor_offset_tmp = ((factor < 0.5) ? (factor / 0.5) : 1.0) * factor_offset;
			
			var pixel_prev, pixel_next, pixel_current;
			for(var i=0;i<width;i++)
			{
				for(var j=0;j<height;j++)
				{
					var i_offseted = Math.floor(i - 1.0 * j);
					i_offseted = Math.floor((1.0 - factor_offset_tmp) * i + factor_offset_tmp * i_offseted);
					
					for(var k=0;k<4;k++)			//RGBA
					{
						var pos =  (i + previous_image.width * j) * 4 + k;
						
						if(i_offseted < factor * width)
						{
							current_image.data[pos] = next_image.data[pos];
							
						}else if(i_offseted < (factor + factor_offset_tmp) * width){
							
							var x_rel = (i_offseted - factor * width);
							var angle = Math.acos( (factor_offset_tmp * width - x_rel) / (factor_offset_tmp * width));
							
							var x_offset = - Math.floor(angle * factor_offset_tmp * width + x_rel);		//perimetre = 2 * Pi * R
							var y_offset = Math.floor(Math.sin(angle) * factor_offset_tmp * width * aspectRatio);
							
							if(i_offseted + x_offset < 0)
								x_offset = -i_offseted;
							if(i_offseted + x_offset >= width)
								x_offset = 0;
								
							var pos_prev =  ((i_offseted + x_offset) + previous_image.width * (j + y_offset)) * 4 + k;
							
							if(j + y_offset < height)
								current_image.data[pos] = previous_image.data[pos_prev] * ((k!=3) ? color_otherSide_multiplicator : 1.0);		//color_otherSide_multiplicator for darkside, but alpha didn't change.
							else
								current_image.data[pos] = previous_image.data[pos];
							
						}else{
							current_image.data[pos] = previous_image.data[pos];
						}
						
						
						
						
					}
				}
			}
			
			redraw();
			
			inc_duration += frameDuration;
		}, frameDuration);
		
		
		
	}else if(methodInterpolation=="Linear_Kikoulol"){
		
		
		var offset_X = 100;
		
		intervalHandler = setInterval(function()
		{
			if(inc_duration > duration)
			{
				clearInterval(intervalHandler);
				intervalHandler = null;
				return;
			}
			
			var factor = inc_duration / duration;
			
			var factor_normal = (factor < 0.33) ? (factor / 0.33) : ((factor < 0.66) ? 1.0 : ((factor - 0.66) /0.33) );
			
			var offset_X_t = Math.floor((1.0 - factor) * offset_X);
			//var offset_X_t = Math.floor(factor * offset_X);
			
			var normalImgData = listImgNormal[ Number( $(".cb_normal").val() ) ];
			
			var pixel_prev, pixel_next, pixel_current, px_normal;
			for(var i=0;i<width;i++)
			{
				for(var j=0;j<height;j++)
				{
					px_normal = normalImgData.data[(i + previous_image.width * j) * 4 + 1];		//Red
					var offset_X_t_tmp = Math.floor((px_normal / 255.0) * offset_X_t);
					
					for(var k=0;k<4;k++)			//RGBA
					{
						var pos =  (i + previous_image.width * j) * 4 + k;
						
						//var pos_next =  (  ((i>=offset_X_t_tmp) ? (i - offset_X_t_tmp) : 0) + previous_image.width * j) * 4 + k;
						var pos_next =  (  ((i + offset_X_t_tmp < previous_image.width) ? (i + offset_X_t_tmp) : (previous_image.width-1) ) + previous_image.width * j) * 4 + k;
						
						
						pixel_prev = previous_image.data[pos];
						pixel_next = next_image.data[pos_next];
						pixel_current = Math.floor( (pixel_next - pixel_prev) * factor + pixel_prev);
						
						current_image.data[pos] = pixel_current;
					}
				}
			}
			
			redraw();
			
			inc_duration += frameDuration;
		}, frameDuration);
		
		
	}else if(methodInterpolation=="Boom"){
		
		var vMax = 1.5;
		
		var pixelsOld = new Array();
		
		var gridSize = 10;			//1,2,5 ou 10 px for a big pixel (else is too small to be beautiful)
		
		for(var i=0;i<width/gridSize;i++)
		{
			pixelsOld.push(new Array());
			
			for(var j=0;j<height/gridSize;j++)
				pixelsOld[i][j] = {pos: {x: (i * gridSize), y: (j * gridSize)}, vx: (Math.random() *  2 * vMax - vMax), vy: (Math.random() * 2 * vMax - vMax)};
		}
		
		intervalHandler = setInterval(function()
		{
			if(inc_duration > duration)
			{
				clearInterval(intervalHandler);
				intervalHandler = null;
				return;
			}
			
			var factor = inc_duration / duration;
			
			var factor_fade = (factor < 0.5) ? 0.0 : ((factor - 0.5) / 0.5);
			
			for(var i=0;i<width;i++)
			{
				for(var j=0;j<height;j++)
				{
					for(var k=0;k<4;k++)			//RGBA
					{
						var pos =  (i + previous_image.width * j) * 4 + k;
						current_image.data[pos] = next_image.data[pos];
					}
				}
			}
			
			
			for(var i=0;i<width;i+=gridSize)
			{
				for(var j=0;j<height;j+=gridSize)
				{
					for(var k=0;k<4;k++)			//RGBA
					{
						var px_info = pixelsOld[Math.floor(i/gridSize)][Math.floor(j/gridSize)];
						var pos_i = Math.floor(px_info.pos.x);
						var pos_j = Math.floor(px_info.pos.y);
						
						for(var m=0;m<gridSize;m++)
						{
							for(var n=0;n<gridSize;n++)
							{
								var pos = (pos_i + m + previous_image.width * (pos_j + n)) * 4 + k;
								var pos_prev =  (i + m + previous_image.width * (j + n)) * 4 + k;
								
								if((pos_i+ m >=0)&&(pos_i+ m <width)&&(pos_j+ n>=0)&&(pos_j+ n<height))
									current_image.data[pos] = Math.floor( (1.0 - factor_fade) * previous_image.data[pos_prev] + factor_fade * current_image.data[pos] );
							}
						}
						
						px_info.pos.x += px_info.vx;
						px_info.pos.y += px_info.vy;
					}
				}
			}
			
			redraw();
			
			inc_duration += frameDuration;
		}, frameDuration);
	}
	
	
	
	
}

function redraw()
{
	if(current_image!=null)
		ctx.putImageData(current_image, 0, 0);
}