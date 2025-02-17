package com.mzt.logapi.starter.support.parse;

import com.mzt.logapi.service.IFunctionService;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author muzhantong
 * create on 2022/1/5 8:37 下午
 */
@AllArgsConstructor
public class LogFunctionParser {

    private IFunctionService functionService;


    public String getFunctionReturnValue(Map<String, String> beforeFunctionNameAndReturnMap, Object value, String functionName) {
        if (StringUtils.isEmpty(functionName)) {
            return value.toString();
        }
        String functionReturnValue = "";
        if (beforeFunctionNameAndReturnMap != null) {
            functionReturnValue = beforeFunctionNameAndReturnMap.get(getFunctionCallInstanceKey(functionName, value));
        }
        if (StringUtils.isEmpty(functionReturnValue)) {
            functionReturnValue = functionService.apply(functionName, value);
        }
        return functionReturnValue;
    }

    /**
     * 方法执行之前换成函数的结果，此时函数调用的唯一标志：函数名+参数
     */
    public String getFunctionCallInstanceKey(String functionName, Object value) {
        return functionName + value.toString();
    }


    public void setFunctionService(IFunctionService functionService) {
        this.functionService = functionService;
    }


    public boolean beforeFunction(String functionName) {
        return functionService.beforeFunction(functionName);
    }
}
