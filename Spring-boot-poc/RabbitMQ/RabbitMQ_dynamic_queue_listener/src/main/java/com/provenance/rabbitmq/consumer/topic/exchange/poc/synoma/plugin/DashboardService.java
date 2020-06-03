package com.provenance.rabbitmq.consumer.topic.exchange.poc.synoma.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

//    private final DashboardProperties dashboardProperties;
//    private List<String> listenedQueues = new ArrayList<>();
//    private List<String> listenedFanoutQueues = new ArrayList<>();
//
//
//    @Autowired
//    public DashboardService(DashboardProperties dashboardProperties) {
//        this.dashboardProperties = dashboardProperties;
//    }
//
//    public String getToolkitUrl() {
//        String prefix = dashboardProperties.getToolkitSecure() ? "https" : "http";
//        String host = dashboardProperties.getToolkitHost() + ":" + dashboardProperties.getToolkitPort();
//        return prefix + "://" + host + dashboardProperties.getToolkitContextPath();
//    }
//
//    public List<String> getAllQueues() {
//        return this.listenedQueues;
//    }
//
//    public List<String> getAllFanoutQueues() {
//        return this.listenedFanoutQueues;
//    }
//
//    public void addQueue(String queue) {
//        this.listenedQueues.add(queue);
//    }
//
//    public void addFanoutQueue(String topic) {
//        this.listenedFanoutQueues.add(topic);
//    }

}
