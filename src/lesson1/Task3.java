package lesson1;


import java.util.Arrays;
import java.util.function.Function;

/**
 * Write a lambda expression that accepts seven (!) string arguments and returns a string in upper case concatenated from all of them (in the order of arguments).

 Note. The correct solution may not work in your local environment because you don't have suitable functional interface. The testing system has the interface and can test your solution.

 Solution format. Submit your lambda expression with seven arguments in any valid format with ; on the end.

 Examples (only with two args): (x, y)  -> x + y; (x, y) -> { return x + y; };

 Sample Input:
 The lambda has too many string arguments.
 Sample Output:
 THELAMBDAHASTOOMANYSTRINGARGUMENTS.
 */
public class Task3 {

    interface Invoker {
        String invoke(Object ... args);
    }

    static  Invoker   result =  (Object... x) -> { String s = ""; for (Object i : x){
                                                    s =s+ ((String) i).toUpperCase();
    }
        return s;
    };




    public static void main(String[] args) {

       // right desicion  (x, x1, x2, x3, x4, x5, x6) -> { String a = ""+x+x1+x2+x3+x4+x5+x6; return a.toUpperCase(); };
        System.out.println( result.invoke("a","d","c"));
    }


}
