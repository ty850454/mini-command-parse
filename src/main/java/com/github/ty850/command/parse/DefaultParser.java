package com.github.ty850.command.parse;


import java.util.Optional;

/**
 * 默认命令参数解析器
 *
 * 逗号分隔，a,b=1,c=aa
 *
 * @author xy
 */
public class DefaultParser implements CommandLineParser {

    /**
     * 解析
     *
     * @param enumClass 命令定义枚举class
     * @param commandLine 参数文本
     * @param <T> class 类型
     * @return 命令行对象
     */
    public <T extends IOptionEnum> CommandLine parse(Class<T> enumClass, String commandLine) {
        if (!enumClass.isEnum()) {
            throw new RuntimeException("非枚举");
        }
        if (StringUtils.isBlank(commandLine)) {
            return CommandLine.empty();
        }
        String[] commands = commandLine.split(",");
        if (commands.length == 0) {
            return CommandLine.empty();
        }

        DefaultCommandLine cmd = new DefaultCommandLine(enumClass);
        for (String command : commands) {
            handleToken(enumClass, cmd, command);
        }

        return cmd;
    }

    private <T extends IOptionEnum> void handleToken(Class<T> enumClass, DefaultCommandLine cmd, String command) {
        if (StringUtils.isBlank(command)) {
            return;
        }

        int index = command.indexOf('=');
        if (index == -1) {
            Optional<T> t = EnumUtil.byOpt(enumClass, command);
            t.ifPresent(cmd::addOptionTrue);
            return;
        }
        String key = command.substring(0, index);
        Optional<T> t = EnumUtil.byOpt(enumClass, key);
        t.ifPresent(value -> cmd.addOption(value, command.substring(index + 1)));
    }


}
