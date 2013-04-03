function abc(a,b,c){
	var x=a;
  	var y=b;
  	var z=c;
  	return x+y+z;
}

function fib(x){
	if(x==0||x==1)
		return 0;
	else
		return fib(x-1)+fib(x-2);
}	
