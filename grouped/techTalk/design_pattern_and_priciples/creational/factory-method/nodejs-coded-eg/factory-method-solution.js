class Ship {
	
	deliver() {
		console.log("ship.deliver");	
	}
}

class Truck {

	deliver() {
		console.log("truck.deliver");	
	}
}

class Logistics {
	createTransport() {
	}
	planDelivery () {
		let transport = this.createTransport();
		transport.deliver();
	}
}

class SeaLogistics extends Logistics {
	createTransport() {
		return new Ship();
	}
}

class RoadLogistics extends Logistics {
	createTransport() {
		return new Truck();
	}
}

class LogisticsApp3 {
	configure(shipmentType) {
		let logistics = null;
       		if ("Road"===shipmentType) {
    			logistics = new RoadLogistics();
		} else {
    			logistics = new SeaLogistics();
		}
		return logistics;
	 }

	assignDelivery(shipmentType) {
		this.configure(shipmentType).planDelivery();
	}
}	

new LogisticsApp3().assignDelivery("Road");
new LogisticsApp3().assignDelivery("Sea");
