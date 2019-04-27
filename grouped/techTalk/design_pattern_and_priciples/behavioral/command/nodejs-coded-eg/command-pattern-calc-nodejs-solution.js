// Abstract Command
class Command {
	constructor(value) {
		this.value = value;
	}
	execute(x, y) {
	}
	
	undo(x, y) {
	}
	toString() {
	}
}

// Concrete Command
class AddCommand extends Command {
	execute(x, y) { 
		return x + y; 
	}
	
	undo(x, y) {
		return x - y; 
	}
	toString() {
		return "Add";
	}
}
 
// Concrete Command
class SubCommand extends Command {
	execute(x, y) { 
		return x - y; 
	}
	
	undo(x, y) {
		return x + y; 
	}
	toString() {
		return "Sub";
	}
}
 
// Concrete Command
class MulCommand extends Command {
	execute(x, y) { 
		return x * y; 
	}
	
	undo(x, y) {
		return x / y; 
	}
	toString() {
		return "Mul";
	}
}
 
// Concrete Command
class DivCommand extends Command {
	execute(x, y) { 
		return x / y; 
	}
	
	undo(x, y) {
		return x * y; 
	}
	toString() {
		return "Div";
	}
} 

// Receiver
class Calculator {

	constructor() {
		this.current = 0;
		this.commands = [];
	
	}

	execute(command) {
		this.current = command.execute(this.current, command.value);
		this.commands.push(command);
		console.log("\n"+this.action(command) + ": " + command.value);
	}
 
	undo() {
		var command = this.commands.pop();
		this.current = command.undo(this.current, command.value);
		console.log("Undo " + this.action(command) + ": " + command.value);
	}
 
	action(command) {
		var name = command.toString()
		return name.charAt(0).toUpperCase() + name.slice(1);
	}

 
	getCurrentValue() {
		return this.current;
	}
}

// Client
class CalculatorClient {
	actions() {
		var calculator = new Calculator();	

		// issue commands		 
		calculator.execute(new AddCommand(100));
		console.log("Value: " + calculator.getCurrentValue());
		calculator.execute(new SubCommand(24));
		console.log("Value: " + calculator.getCurrentValue());
		calculator.execute(new MulCommand(6));
		console.log("Value: " + calculator.getCurrentValue());
		calculator.execute(new DivCommand(2));
		console.log("Value: " + calculator.getCurrentValue());
 
		// reverse last two commands
		console.log("\n");		 
		calculator.undo();
		calculator.undo();
		 
		console.log("\nValue after undo's: " + calculator.getCurrentValue());
	}	 
}

new CalculatorClient().actions()
