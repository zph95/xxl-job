package com.xxl.job.admin.core.thread;



import com.xxl.job.admin.SpringBootBaseTest;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JobScheduleHelperTest extends SpringBootBaseTest {
private static Logger logger = LoggerFactory.getLogger(JobScheduleHelperTest.class);


@Test
public void doExecute() throws InterruptedException {
Thread scheduleThreadA = new Thread(() -> testSelectForUpdate("A"));
Thread scheduleThreadB = new Thread(() -> {
testSelectForUpdate("B");
});
Thread scheduleThreadC = new Thread(() -> {
testSelectForUpdate("C");
});
scheduleThreadA.start();
scheduleThreadB.start();
scheduleThreadC.start();
Thread.sleep(1000000);
}

private void testSelectForUpdate(String threadNum){
boolean scheduleThreadToStop=false;
while (!scheduleThreadToStop) {

// Scan Job
long start = System.currentTimeMillis();

Connection conn = null;
Boolean connAutoCommit = null;
PreparedStatement preparedStatement = null;

boolean preReadSuc = true;
try {

conn = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection();
connAutoCommit = conn.getAutoCommit();
conn.setAutoCommit(false);
logger.info("try to get xxl_job_lock:{}",threadNum);
preparedStatement = conn.prepareStatement("select * from xxl_job_lock where lock_name = 'schedule_lock' for update");
preparedStatement.execute();
logger.info("get xxl_job_lock succeed:{}",threadNum);
Thread.sleep(61*1000L);
} catch (Exception e) {
if (!scheduleThreadToStop) {
logger.error(">>>>>>>>>>> xxl-job, JobScheduleHelper#scheduleThread error:{}",threadNum, e);
}
} finally {

// commit
if (conn != null) {
try {
conn.commit();
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
try {
conn.setAutoCommit(connAutoCommit);
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
try {
conn.close();
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
}

// close PreparedStatement
if (null != preparedStatement) {
try {
preparedStatement.close();
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
}
}
}
}

@Test
public void testExecute2() throws Exception{
//select sleep(5)
Thread scheduleThreadA = new Thread(() -> testSelectSleep("A"));
scheduleThreadA.start();
Thread.sleep(1000000);
}

private void testSelectSleep(String threadNum){
boolean scheduleThreadToStop=false;
while (!scheduleThreadToStop) {

// Scan Job
long start = System.currentTimeMillis();

Connection conn = null;
Boolean connAutoCommit = null;
PreparedStatement preparedStatement = null;

boolean preReadSuc = true;
try {

conn = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection();
connAutoCommit = conn.getAutoCommit();
conn.setAutoCommit(false);
logger.info("try to select sleep:{}",threadNum);
preparedStatement = conn.prepareStatement("select sleep(15)");
preparedStatement.execute();
logger.info("get select sleep:{}",threadNum);
} catch (Exception e) {
if (!scheduleThreadToStop) {
logger.error(">>>>>>>>>>> xxl-job, JobScheduleHelper#scheduleThread error:{}",threadNum, e);
}
} finally {

// commit
if (conn != null) {
try {
conn.commit();
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
try {
conn.setAutoCommit(connAutoCommit);
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
try {
conn.close();
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
}

// close PreparedStatement
if (null != preparedStatement) {
try {
preparedStatement.close();
} catch (SQLException e) {
if (!scheduleThreadToStop) {
logger.error(e.getMessage(), e);
}
}
}
}
}
}

}

