package com.osgi.poc1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

public class Activator implements BundleActivator, ServiceListener {

	private BundleContext ctx;

	public void start(BundleContext context) throws Exception {
		this.ctx = context;
		System.out.println("Starting the bundle");
		ctx.addServiceListener(this);
		System.out.println("Starting to listen for service events, Bundle Id = " + context.getBundle().getBundleId());

	}

	public void stop(BundleContext context) throws Exception {
		this.ctx = context;
		System.out.println("Stopped listening for service events. Bundle Id = " + context.getBundle().getBundleId());
		ctx.removeServiceListener(this);
		System.out.println("Stopping the bundle");

	}

	public void serviceChanged(ServiceEvent serviceEvent) {
		int type = serviceEvent.getType();
		switch (type) {
		case (ServiceEvent.REGISTERED):
			System.out.println("Notification of service registered.");
			break;
		case (ServiceEvent.UNREGISTERING):
			System.out.println("Notification of service unregistered.");
			break;
		default:
			break;
		}
	}

}
