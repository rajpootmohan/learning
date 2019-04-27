var burger = 'hamburger', // String
calories = 300, // Numeric
tasty = true; // Boolean
// The given type is boolean, the provided value is a string.
// var tasty: boolean = "I haven't tried it yet";
// Alternatively, you can omit the type declaration:
// var burger = 'hamburger';
// The function expects a string and an integer.
// It doesn't return anything so the type of the function itself is void.
function speak(food, energy) {
    console.log("Our " + food + " has " + energy + " calories.");
}
speak(burger, calories);
