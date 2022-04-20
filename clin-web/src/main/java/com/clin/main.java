package com.clin;

import com.clin.core.utils.SequenceGenerator;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-07
 **/
public class main {

    private static SequenceGenerator sequenceGenerator = new SequenceGenerator();
    public static void main(String[] args) {


        System.out.println(sequenceGenerator.nextId());
        System.out.println(sequenceGenerator.nextId());
        System.out.println(sequenceGenerator.nextId());
    }



}
