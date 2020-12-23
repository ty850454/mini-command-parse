package com.github.ty850454.command.parse;


/**
 * @author xy
 */
public class DefaultParser<T extends Enum<?> & IOptionEnum> implements CommandLineParser {

    private final Class<T> enumClass;

    public DefaultParser(Class<T> enumClass) {
        if (!enumClass.isEnum()) {
            throw new RuntimeException("非枚举");
        }
        this.enumClass = enumClass;
    }

    public CommandLine parse(String commandLine) {
        if (StringUtils.isBlank(commandLine)) {
            return CommandLine.empty();
        }
        String[] commands = commandLine.split(" ");
        if (commands.length == 0) {
            return CommandLine.empty();
        }

        CommandLine cmd = new CommandLine(enumClass);
        for (String command : commands) {
            handleToken(cmd, command);
        }

        checkRequiredArgs(cmd);

        return cmd;
    }

    private void handleToken(CommandLine cmd, String command) {
        if (StringUtils.isBlank(command)) {
            return;
        }

        int index = command.indexOf('=');
        if (index == -1) {
            cmd.addOptionTrue(EnumUtil.byOpt(enumClass, command).orElseThrow(() -> new RuntimeException("选项" + command + "不存在")));
            return;
        }
        String key = command.substring(0, index);
        cmd.addOption(EnumUtil.byOpt(enumClass, key).orElseThrow(() -> new RuntimeException("选项" + key + "不存在")), command.substring(index + 1));
    }

    private void checkRequiredArgs(CommandLine cmd) {


    }

}
