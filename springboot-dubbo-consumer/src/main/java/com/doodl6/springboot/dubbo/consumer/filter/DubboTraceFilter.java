package com.doodl6.springboot.dubbo.consumer.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

@Slf4j
@Activate(group = Constants.CONSUMER)
public class DubboTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            long start = System.currentTimeMillis();
            Result result = invoker.invoke(invocation);
            long took = System.currentTimeMillis() - start;

            recordRpcLog(took, invocation);
            return result;
        } catch (RpcException e) {
            log.error("dubbo消费者rpc异常:", e);
            throw e;
        }
    }

    private static void recordRpcLog(long took, Invocation invocation) {
        String serviceAndMethod = invocation.getAttachment("interface") + "." + invocation.getMethodName();
        log.info("dubbo调用 | {} | {}ms", serviceAndMethod, took);
    }

}
