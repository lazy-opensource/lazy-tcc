package com.lazy.tcc.core.scheduler.job;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.repository.jdbc.MysqlTransactionRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * <p>
 * CompensableTransactionJob Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public class CompensableTransactionJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompensableTransactionJob.class);
    private static final MysqlTransactionRepository TRANSACTION_REPOSITORY = (MysqlTransactionRepository) TransactionRepositoryFactory.create();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        long beginTime = System.currentTimeMillis();
        doExecute();
        long endTime = System.currentTimeMillis();

        long consumingTime = endTime - beginTime;
        LOGGER.info("CompensableTransactionJob Finished: Consuming Time: " + consumingTime / 1000 + "s");
    }

    private void doExecute() {

        List<TransactionEntity> failures = TRANSACTION_REPOSITORY.findAllFailure();

        if (failures.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Not Failures TCC Transaction");
            }
            return;
        }

        for (TransactionEntity entity : failures) {

            try {

                if (entity.getParticipants().isEmpty()) {

                    TRANSACTION_REPOSITORY.delete(entity.getTxId());
                    continue;
                }

                final TransactionContext context = new TransactionContext()
                        .setTxId(entity.getTxId())
                        .setTxPhase(entity.getTxPhase());

                if (entity.getTxPhase().equals(TransactionPhase.TRY)) {

                    entity.getParticipants().forEach(participant -> participant.getCancelMethodInvoker().invoker(context));
                } else if (entity.getTxPhase().equals(TransactionPhase.CONFIRM)) {

                    entity.getParticipants().forEach(participant -> participant.getConfirmMethodInvoker().invoker(context));
                } else if (entity.getTxPhase().equals(TransactionPhase.CANCEL)) {

                    entity.getParticipants().forEach(participant -> participant.getCancelMethodInvoker().invoker(context));
                }

                TRANSACTION_REPOSITORY.delete(entity.getTxId());
            } catch (Exception ex) {

                LOGGER.error("CompensableTransactionJob Execute Exception: ", ex);
            }

        }

    }
}
