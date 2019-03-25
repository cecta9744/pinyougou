package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Table(name="tb_pay_log")
public class PayLog implements Serializable{
   
	private static final long serialVersionUID = 362140345448460733L;
	@Id @Column(name="out_trade_no")
	private String outTradeNo;
	@Column(name="create_time")
    private Date createTime;
	@Column(name="pay_time")
    private Date payTime;
	@Column(name="total_fee")
    private Long totalFee;
	@Column(name="user_id")
    private String userId;
	@Column(name="transaction_id")
    private String transactionId;
	@Column(name="trade_state")
    private String tradeState;
	@Column(name="order_list")
    private String orderList;
	@Column(name="pay_type")
    private String payType;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState == null ? null : tradeState.trim();
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList == null ? null : orderList.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }
}