package com.systemcalls.systemcalls.service;

import com.systemcalls.systemcalls.domain.constants.Constants;
import com.systemcalls.systemcalls.domain.response.ErrorResponse;
import com.systemcalls.systemcalls.service.iface.iDiskService;
import com.systemcalls.systemcalls.util.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.systemcalls.systemcalls.util.LoggerUtil.logger;

@Service
public class DiskService implements iDiskService {

    private final FileSystem fileSystem;

    public DiskService(FileSystem fileSystem){
        this.fileSystem=fileSystem;
    }

    @Override
    public long getDiskSpaceUsedInMb() {
        try{
//            file = new File("/");File
            logger.info("Getting disk space usage...");
            long totalSpace = fileSystem.getTotalSpace();
            long freeSpace = fileSystem.getFreeSpace();
            long usedSpace = totalSpace-freeSpace;
            long usedSpaceInMb = usedSpace/ Constants.MB_CONVERSION_FACTOR;
            logger.info("Disk Space Used In MB:{}", usedSpaceInMb);
            return usedSpaceInMb;
        }
        catch(Exception e){
            logger.error("Error occurred while getting Disk Space Usage",e);
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.DISK_SPACE_USAGE_ERROR,
                    e.getMessage());
            String errorMessage = ObjectUtils.serialize(errorResponse);
            throw new RuntimeException(errorMessage);
        }

    }

    @Override
    public BigDecimal getDiskSpacePercentageUsed() {
        try{
//            file = new File("/");
            logger.info("Getting disk space percentage usage...");
            double totalSpace = fileSystem.getTotalSpace();
            double freeSpace = fileSystem.getFreeSpace();
            double usedSpace = totalSpace-freeSpace;
            double spacePercentageUsage = (usedSpace/totalSpace)*100;
            logger.info("Disk Space Used In MB:{}", spacePercentageUsage);
            return BigDecimal.valueOf(spacePercentageUsage).setScale(2, RoundingMode.HALF_UP);
        }
        catch(Exception e){
            logger.error("Error occurred while getting Disk Space Usage",e);
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.DISK_SPACE_PERCENTAGE_USAGE_ERROR,
                    e.getMessage());
            String errorMessage = ObjectUtils.serialize(errorResponse);
            throw new RuntimeException(errorMessage);
        }

    }
}
