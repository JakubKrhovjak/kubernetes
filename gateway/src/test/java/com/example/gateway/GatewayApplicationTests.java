package com.example.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SpringBootTest
class GatewayApplicationTests {

    public static int digital_root(int number) {
       var a = 88 % 9;
        var result = Arrays.stream(String.valueOf(number).split(""))
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElseThrow();

        var r=  result > 9 ? digital_root(result) : result;
//        var m =  --number % 9 + 1;
       return r;
    }



    @Test
    public void Test1() {
        assertEquals(  7, digital_root(88));
    }
    @Test
    public void Test2() {
        assertEquals( 6, digital_root(456));

    }


}
