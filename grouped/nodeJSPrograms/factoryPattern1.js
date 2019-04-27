class Vehicle {
  static create(type){
    if(type===1){
      return new TwoWheeler();
    }else if (type ===2) {
      return new FourWheeler();
    }
  }
}

class TwoWheeler extends Vehicle{
  printVehicle(){
    console.log("I m in 2 Wheeler");
  }
}

class FourWheeler extends Vehicle{
  printVehicle(){
    console.log("I am in 4 Wheeler");
  }
}

class Client {
  constructor(type) {
    return Vehicle.create(type);
  }
}

var v1 = new Client(2);
v1.printVehicle();
