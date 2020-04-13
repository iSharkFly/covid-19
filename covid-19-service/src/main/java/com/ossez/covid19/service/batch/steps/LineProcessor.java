package com.ossez.covid19.service.batch.steps;

import com.ossez.covid19.common.models.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

public final class LineProcessor implements ItemProcessor<Listing, Listing>, StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(LineProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Line Processor initialized.");
    }

    @Override
    public Listing process(Listing listing) throws Exception {
//        long age = ChronoUnit.YEARS.between(line.getDob(), LocalDate.now());
//        logger.debug("Calculated age " + age + " for line " + line.toString());
//        line.setAge(age);
        logger.debug("MLSNumber : [{}]", listing.getMlsNumber());
        return listing;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Line Processor ended.");
        return ExitStatus.COMPLETED;
    }
}
