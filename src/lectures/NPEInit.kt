package lectures

//1. In Java, Inherited class's constructors are running parent constructor first, then child class constructor
//https://medium.com/@mook2_y2/%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%9E%85%EB%AC%B8-%EC%8A%A4%ED%84%B0%EB%94%94-11-object-oriented-programming-8e2e8db4dff
open class A(open val value: String) {
    init {
        println("A class init")
        println(value.length) //Accessing non-final property value in constructor
    }
}

// 2. When use override value property, child class make additional field(same variable name, but different reference).
// Then child class do override related accessor for working to added field.
class B(override val value: String) : A(value)

fun main(args: Array<String>) {
    B("a")
}

//3. While making B class, B class's constructor will be called.
//First, B class's constructor will call A class's constructor. But A class's "this.value = value" will not be overridden. (Calling A class's value field)
//Then A class's constructor will call "this.getValue().length()". The getValue() is overridden by B class.
//So getValue() will access B class's value field, but B class's value field is not yet initiated.
//Finally, this class throw NullPointerException.


// Java version
/*
public class A {
    private final String value;
    public String getValue() { return this.value;}
    public A(String value) {
        this.value = value;
        System.out.println(this.getValue().length());
    }
}
public final class B extends A {
    private final String value;
    @Override
    public String getValue() { return this.value; }
    public B(String value) {
        super(value);
        this.value = value;
    }
}
 */