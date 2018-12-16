package com.lazy.tcc.core;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.common.utils.SnowflakeIdWorkerUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Transaction interface definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class Transaction implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -994345465462L;

    public Transaction() {
        this.txId = SnowflakeIdWorkerUtils.getINSTANCE().nextId();
        this.txPhase = TransactionPhase.TRY;
        this.retryCount = 0;
        this.createTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
        this.lastUpdateTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * transaction id
     */
    private Long txId;
    /**
     * transaction phase
     * {@link TransactionPhase}
     */
    private TransactionPhase txPhase;
    /**
     * retry count
     */
    private int retryCount;
    /**
     * transaction create time
     */
    private String createTime;
    /**
     * transaction last update time
     */
    private String lastUpdateTime;
    /**
     * optimistic version
     */
    private long version = 1;
    /**
     * participant list
     */
    private List<Participant> participants = new ArrayList<>();

    public void updateVersion(){
        this.version++;
    }

    public void updateLastUpdateTime(){
        this.lastUpdateTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    public Long getTxId() {
        return txId;
    }

    public Transaction setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionPhase getTxPhase() {
        return txPhase;
    }

    public Transaction setTxPhase(TransactionPhase txPhase) {
        this.txPhase = txPhase;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Transaction setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Transaction setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Transaction setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public Transaction setVersion(long version) {
        this.version = version;
        return this;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public Transaction setParticipants(List<Participant> participants) {
        this.participants = participants;
        return this;
    }
}
