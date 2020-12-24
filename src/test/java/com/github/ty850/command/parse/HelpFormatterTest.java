package com.github.ty850.command.parse;


/**
 * @author xy
 */
class HelpFormatterTest {

    public static void main(String[] args) {
        System.out.println(new HelpFormatter(TestOptionEnum.class).getFormatHelp());
    }
}