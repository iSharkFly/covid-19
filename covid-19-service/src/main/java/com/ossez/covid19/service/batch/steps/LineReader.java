package com.ossez.covid19.service.batch.steps;

import com.ossez.covid19.common.models.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;

public class LineReader implements ItemReader<Listing>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LineReader.class);
    private int nextStudentIndex;
    private List<Listing> listingList = new ArrayList<Listing>();

    @Override
    public void beforeStep(StepExecution stepExecution) {
//        fu = new FileUtils("taskletsvschunks/input/tasklets-vs-chunks.csv");
        Listing listing = new Listing();
        listing.setMlsNumber("0");
        listingList.add(listing);
        logger.debug("Line Reader initialized.");
    }

    @Override
    public Listing read() throws Exception {
//        Line line = fu.readLine();
//        if (line != null) logger.debug("Read line: " + line.toString());

        Listing nextListing = null;

        if (nextStudentIndex <1) {
            nextListing = listingList.get(nextStudentIndex);
            nextStudentIndex++;
        }

        return nextListing;


    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
//        fu.closeReader();
        logger.debug("Line Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
