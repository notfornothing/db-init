package oldmoon.api.compiler;

import com.itranswarp.compiler.JavaStringCompiler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description java编译器工具类
 * @Author hupg
 * @Date 2021/8/25 16:38
 */
@Slf4j
public class CompilerUtilOm {

    /**
     * 动态编译
     *
     * @param className   类名
     * @param packageName 包名
     * @param classSource 类源码
     * @return java.lang.Class
     * @author hupg
     * @date 2021/8/26 10:08
     */
    public static Class compiler(String className, String packageName, String classSource) throws IOException, ClassNotFoundException {
        String fullName = packageName + "." + className;
        // 编译器
        JavaStringCompiler compiler = new JavaStringCompiler();
        // 编译数据
        Map<String, byte[]> javaFile = compiler.compile(className + ".java", classSource);
        // 加载内存中byte到Class<?>对象
        Class<?> clazz = compiler.loadClass(fullName, javaFile);
        return clazz;
    }

    /**
     * 反射获取类实例
     *
     * @param clazz 类
     * @return java.lang.Object
     * @author hupg
     * @date 2021/8/26 10:06
     */
    public static Object newInstance(Class clazz) throws InstantiationException, IllegalAccessException {
        // 类实例化
        Object object = clazz.newInstance();
        return object;
    }

    /**
     * 通过反射，获取想要的方法
     *
     * @param clazz      方法持有类
     * @param methodName 方法名
     * @param paramTypes ... 方法参数类（可以传入多个参数类）
     * @return java.lang.reflect.Method
     * @author hupg
     * @date 2021/8/26 10:05
     */
    public static Method getMethod(Class clazz, String methodName, Class... paramTypes) throws NoSuchMethodException {
        Method method = clazz.getMethod(methodName, paramTypes);
        return method;
    }

    /**
     * 反射方法执行
     *
     * @param method 方法实例
     * @param object 方法持有者实例
     * @param param  ... 方法参数（可以传入多个参数）
     * @return java.lang.Object
     * @author hupg
     * @date 2021/8/26 10:01
     */
    public static Object invokeMethod(Method method, Object object, Object... param) throws IllegalAccessException, InvocationTargetException {
        try {
            return method.invoke(object, param);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("++++++++++++++++++++{}方法执行出错++++++++++++++++++++", method.getName());
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        // 传入String类型的代码
        String source = "package oldmoon.api.compiler;" +
                "import java.util.Arrays;" +
                "public class Main {" +
                "public static String test(String[] args, String test) {" +
                "System.out.println(Arrays.toString(args));" +
                "System.out.println(test);" +
                "return \"testReturn\";" +
                "}" +
                "}";

        String[] param = new String[]{"1", "2", "3"};
        String test = new String("test");
        try {
            // 编译字节码对象
            Class mainClazz = CompilerUtilOm.compiler("Main", "oldmoon.api.compiler", source);
            // 实例化对象
            Object mainObject = CompilerUtilOm.newInstance(mainClazz);
            // 反射获取方法
            Method mainMethod = CompilerUtilOm.getMethod(mainClazz, "test", param.getClass(), test.getClass());
            // 执行方法，获取返回值
            Object result = CompilerUtilOm.invokeMethod(mainMethod, mainObject, param, test);
            // 输出返回值
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            log.error("++++++++++++++++++++动态编译出错++++++++++++++++++++");
            e.printStackTrace();
        } catch (InstantiationException e) {
            log.error("++++++++++++++++++++实例化出错++++++++++++++++++++");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            log.error("++++++++++++++++++++反射获取方法出错++++++++++++++++++++");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.error("++++++++++++++++++++反射实例化出错 || 反射执行方法出错++++++++++++++++++++");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            log.error("++++++++++++++++++++反射执行方法出错++++++++++++++++++++");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("++++++++++++++++++++未知错误++++++++++++++++++++");
            e.printStackTrace();
        }
    }
}
