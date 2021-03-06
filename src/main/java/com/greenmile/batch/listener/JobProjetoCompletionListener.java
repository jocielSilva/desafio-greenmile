package com.greenmile.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Juciel Almeida
 * 
 * @class JobProjetoCompletionListener
 * 
 *        Classe responsável pela finalização do processamento Batch.
 * 
 */
@Component
public class JobProjetoCompletionListener extends JobExecutionListenerSupport{
	
	private static final Logger log = LoggerFactory.getLogger(JobProjetoCompletionListener.class);

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");
		}
	}
}
