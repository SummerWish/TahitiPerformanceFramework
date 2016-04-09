package otcoteam.tahiti.performance.example;

import otcoteam.tahiti.performance.PerformanceMonitor;
import otcoteam.tahiti.performance.recorder.CountingRecorder;
import otcoteam.tahiti.performance.reporter.LogReporter;
import otcoteam.tahiti.performance.reporter.RollingFileReporter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PerMiniuteCountingExample {

    public static void main(String args[]) {
        // ������Ҫһ������������, �˴����ǽ��� RollingFileReporter, ��
        // ���ɱ��浽һ���ļ���. ����ʱ�������ʽ�� yyyy-MM-dd_HH-mm, ��
        // �˽�ÿ��������һ�����ļ�.
        LogReporter reporter = new RollingFileReporter("bar-%d{yyyy-MM-dd_HH-mm}.log");

        // �������������ܼ��ʵ��
        final PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        // ����Ҫͳ�Ƶ�ָ��, ��Ҫ���г�ʼ��. �˴�ʹ�� CountingRecorder, ��
        // ������ָ��. ���ڼ�����ָ��, ����ʱ���ۼ����ʱ���ڵļ�¼����.
        monitor.addRecorder("request", new CountingRecorder("Request times"));
        monitor.addRecorder("login", new CountingRecorder("User login times"));

        // ��ʼ��ʱ����, �˴���ÿ 1 ����ͳ��һ��.
        monitor.start(1, TimeUnit.MINUTES);

        // ����ģ��ÿ 5 ����һ�� request
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                monitor.record("request");
            }
        }, 5, 5, TimeUnit.SECONDS);

        // ����ģ��ÿ 10 ����һ�� login
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                monitor.record("login");
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

}
