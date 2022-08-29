package com.yun.jt808.utils.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:54:40
 */
public class DatedFileAppender extends FileAppender{
	
	private final static Logger logger = LoggerFactory.getLogger(DatedFileAppender.class);  
	  
    private long nextCheck = 0L;  
  
    private String originFilename = null;  
  
    private String getDatedFilename()   
    {  
  
        String str = this.originFilename;  
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");  
        String time14 = format.format(new Date());  
        str = str.replaceAll("%yyyy", time14.substring(0, 4));  
        str = str.replaceAll("%yy", time14.substring(0, 4));  
        str = str.replaceAll("%mm", time14.substring(4, 6));  
        str = str.replaceAll("%dd", time14.substring(6, 8));  
        str = str.replaceAll("%hh", time14.substring(8, 10));  
  
        String curPath = str;  
        try   
        {  
            newFolder(str);  
        }  
        catch (Exception ex) {  
            logger.error("log4j文件路径创建失败：" + ex);  
        }  
  
        return curPath;  
    }  
  
    void switchFilename() throws IOException {  
        String datedFilename = getDatedFilename();  
  
        if (datedFilename.equals(this.fileName)) {  
            return;  
        }  
  
        try {  
            setFile(datedFilename, getAppend(), getBufferedIO(), getBufferSize());  
        }  
        catch (IOException ex) {  
  
            this.errorHandler.error("setFile(" + datedFilename + ", false) call failed.");  
        }  
        this.fileName = datedFilename;  
    }  
    @Override
    public void setFile(String file) {  
  
        this.originFilename = file;  
  
        super.setFile(getDatedFilename());  
    }  
    @Override
    protected void subAppend(LoggingEvent event) {  
        long n = System.currentTimeMillis();  
        if (n >= this.nextCheck) {  
            this.nextCheck = (n + 30000L);  
            try {  
                switchFilename();  
            }  
            catch (IOException ioe) {  
                LogLog.error("switchFilename() failed.", ioe);  
            }  
        }  
        super.subAppend(event);  
    }  
  
    private void newFolder(String folderPath) {  
        String filePath = folderPath.toString();  
        java.io.File myFilePath = new java.io.File(filePath);  
        try {  
            if (!myFilePath.isDirectory()) {  
                myFilePath.getParentFile().mkdirs();  
            }  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
}
