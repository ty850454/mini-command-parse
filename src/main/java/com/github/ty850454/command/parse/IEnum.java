package com.github.ty850454.command.parse;

/**
 * 枚举接口
 *
 * @author xy
 */
public interface IEnum {

    /**
     * 返回枚举定义的名称
     *
     * @return 枚举定义名
     */
    String name();

    /**
     * 返回枚举定义顺序
     *
     * @return 枚举定义顺序
     */
    int ordinal();
}
