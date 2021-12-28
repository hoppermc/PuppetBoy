# Reversing
These are some notes I took while reverse engineering the minecraft sourcecode as practice,
for more complicated classes, I used the mojang remappings.

## PathfinderGoal
### Methods
- **a(): Boolean = Precondition** | Should the goal start?
- **b(): Boolean = Stay Active** | Should the goal stay active? (defaults to a())
- **D_(): Boolean = Interruptable** | Can this goal be interrupted?
- **c(): Void = Initializer** | What should the goal do after starting?
- **d(): Void = Finalizer** | What should the goal do after it finished? (b() is false)
- **e(): Void = Ticker** | What should the goal do while being active?
- **h(): Boolean = Fixed Tick** | Must this goal be updated every tick?

### Flags
a: MOVE, b: LOOK, c: JUMP, d: TARGET

## Common Schemas
### DataTypes
#### Int
Most commonly used for: counter, amounts, probability calculations.

#### Float
Most commonly used for: distances, probabilities.

#### Double
Most commonly used for: positions, vectors, speed.

### Patterns
#### First parameter
The first parameter is always the mob to which the goal should be applied.

#### Movement based goals
The first parameters in any movement based goals are generally speed values as double,
as well as float values for the goal selection radius or distance. All goals which involve
another entity, commonly include the class of the entity or its supertype as the second parameter,
as well as a target selector or a predicate.

#### Entity selection
Entity selections most commonly look something like this
```java
this.b = this.a.t.a(this.a.t.a(this.f, this.a.cw().c((double)this.c, 3.0D, (double)this.c), (var0x) -> {
   return true;
}), this.k, this.a, this.a.dc(), this.a.de(), this.a.di());
```
Here the selected entity is assigned to the field **b**. **c** is the radius in x and z,
y is hardcoded to 3. Is very easy to spot by the `this.a.t.a(this.a.t.a` and the 
`(double)this.c, 3.0D, (double)this.c` (3 doubles are almost always either vectors or locations)

### Distances
Distances are calucated mostly through an `f(Entity)` or `f(Location)` function on the primary entity.

### Constructors
Constructors are almost always organized in ascending order,
the highest on being the primary constructor, called by the ones.

### Name Obfuscation
Most names are obfuscated as one or two char replacement, using an upper and 
lowercase letters only charset, with some exceptions. [a, D, dF, ..]

#### Parameters
Parameter names consist of their index append to 'var'. [var0, var1, ..]

#### Local Variables
Local variables often seem unchanged or follow normal minimized variable naming. [i, k, entity, ..]

#### Lambdas
Parameters in lambdas are similar to parameters, with an 'x' appended at the end. [var0x, var1x, ..]
