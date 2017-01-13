package com.duoec.cn.core.dto;

/**
 * Created by ycoe on 16/5/18.
 */
public class OrderedData<T> implements Comparable<OrderedData<T>> {

    private T data;

    private int order;

    public OrderedData(T data, Integer order){
        this.data = data;
        this.order = order;
    }

    @Override
    public int compareTo(OrderedData<T> o) { //NOSONAR
        if(o.order == this.order)
            return 0;
        return o.order > this.order ? -1 : 1;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
