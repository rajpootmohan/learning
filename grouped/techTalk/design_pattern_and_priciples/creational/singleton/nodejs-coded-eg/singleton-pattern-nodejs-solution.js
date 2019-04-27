class Singleton {
	
	constructor(val) {
	        if (typeof Singleton.instance === 'object') {
        	    return Singleton.instance;
        	}
	        Singleton.instance = this;
		this.val = val; 
        	return this;
    	}
}

var instance1 = new Singleton('foo');
var instance2 = new Singleton('bar');

console.log("Same instance? " + (instance1 === instance2));  
console.log(instance1.val+" "+instance2.val);  
