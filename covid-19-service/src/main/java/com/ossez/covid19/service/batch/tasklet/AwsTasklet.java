package com.ossez.covid19.service.batch.tasklet;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 *
 */
public class AwsTasklet implements Tasklet {

    private final Logger logger = LoggerFactory.getLogger(AwsTasklet.class);
    private Resource directory;

    public AwsTasklet(Resource directory) {
        this.directory = directory;
    }


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("Download Listing Photo from AWS Bucket: com.ossez.real.estate");

        AWSCredentials awsCreds = new BasicAWSCredentials("AKIAIJDKPNI3PL7E6QXQ", "Jfaq2mGcogodLKJa7wae9dgd+M3bQ6g5XjkOt1ZV");

        final AmazonS3 s3 =
                AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_1).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals("com.ossez.real.estate")) {
                named_bucket = b;
            }


        }

        try {

            ListObjectsV2Result result = s3.listObjectsV2("com.ossez.real.estate", "verani-listing-img");
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            for (S3ObjectSummary os : objects) {
                String fileName = StringUtils.substringAfterLast(os.getKey(), "/");
                System.out.println("* " + fileName);
                String folderName = "" + (NumberUtils.toLong(StringUtils.substringBefore(fileName, "-")) / 10000);

                if (!(StringUtils.equalsIgnoreCase("RESI-ALL.xml", fileName) || StringUtils.equalsIgnoreCase("COMM-ALL.xml", fileName))) {
                    S3Object o = s3.getObject("com.ossez.real.estate", os.getKey());
                    S3ObjectInputStream s3is = o.getObjectContent();
                    FileOutputStream fos =
                            FileUtils.openOutputStream(new File("/home/vhosts/ossez.com/repo.ossez.com/httpdocs/real-estate/listing-photo/" + folderName + "/" + StringUtils.substringBefore(fileName,
                                    "-") + "/" + fileName));

                    byte[] read_buf = new byte[1024];
                    int read_len = 0;
                    while ((read_len = s3is.read(read_buf)) > 0) {
                        fos.write(read_buf, 0, read_len);
                    }

                    s3is.close();
                    fos.close();
                }

                s3.deleteObject("com.ossez.real.estate", os.getKey());
            }


        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }


        return RepeatStatus.FINISHED;
    }

}
