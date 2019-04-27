class ShoppingCart {

    checkout(custType) {
	switch(custType) {
		case "guest" : return this.actualAmount;
		case "regular" : return this.actualAmount * 0.9;
		case "premium" : return this.actualAmount * 0.8;
	}
    }

    setActualAmount(actualAmount) {
        this.actualAmount = actualAmount;
    }	
}
export { ShoppingCart };

/* client usage
> cart = new ShoppingCart();
ShoppingCart {}
> cart.setActualAmount(100);
undefined
> cart.checkout("guest");
100
> cart.checkout("regular");
90
> cart.checkout("premium");
80
*/
