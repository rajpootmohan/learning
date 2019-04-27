class CustomerPrototype {

	constructor(first, last, status) {
		this.first = first;
		this.last = last;
		this.status = status;	 
	}
	
	clone() {
	}			
}
 
class Customer extends CustomerPrototype {
 	
	constructor(first, last, status) {
		super(first, last, status);
	}

	clone() {
		return new Customer(this.first, this.last, this.status); 
	}

    	say() {
        	console.log("name: " + this.first + " " + this.last +
              ", status: " + this.status);
    	}
}

function run() {
 
    var customer = new Customer("sujeet", "jaiswal", "pending");
    customer.say();
    var customerCopy = customer.clone();
    customerCopy.say();
}

run();
