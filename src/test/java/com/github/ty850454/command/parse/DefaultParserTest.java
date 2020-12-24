package com.github.ty850454.command.parse;

/**
 * @author xy
 */
class DefaultParserTest {

    public static void main(String[] args) {
        DefaultParser defaultParser = new DefaultParser();

        CommandLine commandLine = defaultParser.parse(TestOptionEnum.class, "a,b=12,d");
        System.out.println(commandLine.getBoolean(TestOptionEnum.A));
        System.out.println(commandLine.getValue(TestOptionEnum.B));
        System.out.println(commandLine.hasOption(TestOptionEnum.C));
        System.out.println(commandLine.getValue(TestOptionEnum.C));
    }
}