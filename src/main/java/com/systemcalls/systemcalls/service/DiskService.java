package com.systemcalls.systemcalls.service;


import com.systemcalls.systemcalls.domain.constants.Constants;
import com.systemcalls.systemcalls.domain.response.ErrorResponse;
import com.systemcalls.systemcalls.service.iface.iDiskService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.systemcalls.systemcalls.util.LoggerUtil.logger;

@Service
public class DiskService implements iDiskService {
    private File file;

    @PostConstruct
    public void init(){
        try{
            file = new File("/");
        }catch(Exception e){
            logger.error("Error occurred while creating file",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.FILE_CREATION_ERROR,e.getMessage())));
        }
    }
    @Override
    public long getDiskSpaceUsedInMb() {
        try{
            logger.info("Getting disk space usage...");
            long totalSpace = file.getTotalSpace();
            long freeSpace = file.getFreeSpace();
            long usedSpace = totalSpace-freeSpace;
            long usedSpaceInMb = usedSpace/ Constants.MB_CONVERSION_FACTOR;
            logger.info("Disk Space Used In MB:{}", usedSpaceInMb);
            return usedSpaceInMb;
        }catch(Exception e){
            logger.error("Error occurred while getting Disk Space Usage",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.DISK_SPACE_USAGE_ERROR,
                    e.getMessage())));
        }

    }

    @Override
    public BigDecimal getDiskSpacePercentageUsed() {
        try{
            logger.info("Getting disk space percentage usage...");
            double totalSpace = file.getTotalSpace();
            double freeSpace = file.getFreeSpace();
            double usedSpace = totalSpace-freeSpace;
            double spacePercentageUsage = (usedSpace/totalSpace)*100;
            logger.info("Disk Space Used In MB:{}", spacePercentageUsage);
            return BigDecimal.valueOf(spacePercentageUsage).setScale(2, RoundingMode.HALF_UP);
        }catch(Exception e){
            logger.error("Error occurred while getting Disk Space Usage",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.DISK_SPACE_PERCENTAGE_USAGE_ERROR,
                    e.getMessage())));
        }

    }
}
