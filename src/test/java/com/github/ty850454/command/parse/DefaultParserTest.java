package com.github.ty850454.command.parse;

/**
 * @author xy
 */
class DefaultParserTest {

    public static void main(String[] args) {
        System.out.println(TestOptionEnum.A.name());
        DefaultParser defaultParser = new DefaultParser(TestOptionEnum.class);

        System.out.println(defaultParser.parse("a b=12"));

    }
}