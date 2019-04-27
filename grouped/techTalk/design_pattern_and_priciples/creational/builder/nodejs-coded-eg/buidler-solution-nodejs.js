class Car {
	addBodyParts() {
		this.doors = 4;
		this.windows = 4;
		this.staring = 1;
	}
	addTyres() {
		this.tyres = 4;
	}
	say() {
		console.log("I am a car - " + JSON.stringify(this));
	}
}

class Truck {
	addBodyParts() {
		this.doors = 2;
		this.windows = 2;
		this.staring = 1;
	}
	addTyres() {
		this.tyres = 10;
	}
	say() {
		console.log("I am a truck - " + JSON.stringify(this));
	}
}

class CarBuilder {

    reset() {
        this.car = new Car();
    };
 
    assembleBodyParts() {
        this.car.addBodyParts();
    };

    assembleTyres() {
        this.car.addTyres();
    };
 
    getResult() {
        return this.car;
    };
}

class TruckBuilder {
 
    reset() {
        this.truck = new Truck();
    };
 
    assembleBodyParts() {
        this.truck.addBodyParts();
    };

    assembleTyres() {
        this.truck.addTyres();
    };
 
    getResult() {
        return this.truck;
    };
}

class Shop {
	construct(builder) {
		builder.reset();
	        builder.assembleBodyParts();
	        builder.assembleTyres();
	        return builder.getResult();
	}
}

// new Shop().construct(new TruckBuilder()).say()
// I am a truck - {"doors":2,"windows":2,"staring":1,"tyres":10}
// new Shop().construct(new CarBuilder()).say()
// I am a car - {"doors":4,"windows":4,"staring":1,"tyres":4}
