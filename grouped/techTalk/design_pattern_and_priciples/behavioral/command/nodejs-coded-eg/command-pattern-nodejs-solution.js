// Invoker
class Cockpit {
    constructor(command) {
        this.command = command;
    }
    execute() {
        this.command.execute();
    }
    setCommand(command) {
        this.command = command;
    }
}

// Receiver
class Turbine {
    constructor() {
        this.state = false;
    }
    on() {
        this.state = true;
    }
    off() {
        this.state = false;
    }
    getState() {
	return this.state;
    }
}

// ConcreteCommand
class OnCommand {
    constructor(turbine) {
        this.turbine = turbine;
    }
    execute() {
        this.turbine.on();
    }
}

// ConcreteCommand
class OffCommand {
    constructor(turbine) {
        this.turbine = turbine;
    }
    execute() {
        this.turbine.off();
    }
}

/**
> turbine = new Turbine();
Turbine { state: false }
> on = new OnCommand(turbine);
OnCommand { turbine: Turbine { state: false } }
> cockpit = new Cockpit(on);
Cockpit { command: OnCommand { turbine: Turbine { state: false } } }
> cockpit.execute();
undefined
> turbine.getState();
true
> off = new OffCommand(turbine);
OffCommand { turbine: Turbine { state: true } }
> cockpit.setCommand(off);
undefined
> cockpit.execute();
undefined
> turbine.getState();
false
*/
