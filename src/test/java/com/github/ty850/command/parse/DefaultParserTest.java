package com.github.ty850.command.parse;

/**
 * @author xy
 */
class DefaultParserTest {

    public static void main(String[] args) {
        DefaultParser defaultParser = new DefaultParser();

        CommandLine commandLine = defaultParser.parse(TestOptionEnum.class, "b=12,d");
        System.out.println(commandLine.getBool(TestOptionEnum.A));
        System.out.println(commandLine.getValueOpt(TestOptionEnum.B));
        System.out.println(commandLine.hasOption(TestOptionEnum.C));
        System.out.println(commandLine.getValueOpt(TestOptionEnum.C));
    }
}