package com.peri.fashion;

import java.util.function.Consumer;

/**
 * 函数式接口Consumer的使用例子
 */
public class ConsumerTest {

    public static void main(String[] args) {
        Integer result = method("neoooo", (name) -> {
            //对传递的字符串进行消费
            //消费方式:把字符串进行反转输出
            String reverse = new StringBuffer(name).reverse().toString();
            System.out.println(reverse); //3.cba 5.abd
        });
        System.out.println(result); //8.123
    }

    public static Integer method(String name, Consumer<String> consumer) {
        System.out.println(name); //1.neoooo
        name = "abc";
        System.out.println(name); //2.abc
        // 1.andThen() -> 先执行accept方法 再执行andThen里面的内容
        consumer.andThen(
                (names) -> {
                    names = "def";
                    System.out.println(names); //4.def 但是这个def是单独执行的 并没有带出去
                }
        ).accept(name);
        // 此时name为cba 再次进行字符串翻转 name变为abc
        // 2.andThen() -> 先执行accept方法 再执行andThen里面的内容
        consumer.andThen(
                (names) -> {
                    Integer result = method1(names);
                    System.out.println(result); //7.456
                }
        ).accept(name);
        return 123;
    }

    public static Integer method1(String name) {
        System.out.println(name); // 6.abc
        return 456;
    }

}
