import java.lang.reflect.Method;

/**
 * Created by fanzhenmeng on 2017/2/24.
 */
public class Test {

    @org.junit.Test
    public void testMethod(){
        Method[] methods = Person.class.getMethods();

        System.out.print(methods[0].getDeclaringClass());

    }


}
