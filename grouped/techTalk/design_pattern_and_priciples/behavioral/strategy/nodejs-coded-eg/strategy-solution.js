class ShoppingCart {

    constructor(discountStrategy) {
        this.discountStrategy = discountStrategy;
        this.actualAmount = 0;
    }

    checkout() {
        return this.discountStrategy(this.actualAmount);
    }

    setActualAmount(actualAmount) {
        this.actualAmount = actualAmount;
    }
	
    setDiscountStrategy(discountStrategy) {
	this.discountStrategy = discountStrategy;
    }
}

function guestDiscountStrategy(actualAmount) {
    return actualAmount;
}

function regularDiscountStrategy(actualAmount) {
    return actualAmount * 0.9;
}

function premiumDiscountStrategy(actualAmount) {
    return actualAmount * 0.8;
}

export { ShoppingCart, guestStrategy, regularStrategy, premiumStrategy };

/* client usage
> cart = new ShoppingCart(premiumDiscountStrategy)
ShoppingCart {
  discountStrategy: [Function: premiumDiscountStrategy],
  actualAmount: 0 }
> cart.setActualAmount(100);
undefined
> cart.checkout();
80
> cart.setDiscountStrategy(guestDiscountStrategy);
undefined
> cart.checkout();
100
*/

