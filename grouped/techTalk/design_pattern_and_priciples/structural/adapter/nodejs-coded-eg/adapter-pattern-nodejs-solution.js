// old interface
class Shipping {
    calCost(zipStart, zipEnd, weight) {
        // ...
        return "$49.75";
    }
}

// new interface
class AdvancedShipping {
    login(credentials) { /* ... */ };
    setStart(start) { /* ... */ };
    setDestination(destination) { /* ... */ };
    calculate(weight) { return "$39.50"; };
}

// adapter interface
class ShippingAdapter {
    constructor(credentials, advancedShipping) {
	this.shipping = advancedShipping;
	this.shipping.login(credentials);
    }

    calCost(zipStart, zipEnd, weight) {
        this.shipping.setStart(zipStart);
        this.shipping.setDestination(zipEnd);
        return this.shipping.calculate(weight);
    }
}

var shipping = new Shipping();
var credentials = {token: "30a8-6ee1"};
 
// original shipping object and interface 
var cost = shipping.calCost("78701", "10010", "2 lbs");
console.log("Old cost: " + cost);
 
// new shipping object with adapted interface
var adapter = new ShippingAdapter(credentials, new AdvancedShipping()); 
cost = adapter.calCost("78701", "10010", "2 lbs");
console.log("New cost: " + cost);
