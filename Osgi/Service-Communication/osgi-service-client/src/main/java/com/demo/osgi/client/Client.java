package com.demo.osgi.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.demo.osgi.service.definition.Greeter;

public class Client implements BundleActivator, ServiceListener {

	private BundleContext ctx;
	private ServiceReference serviceReference;

	@Override
	public void serviceChanged(ServiceEvent serviceEvent) {
		int type = serviceEvent.getType();
		switch (type) {
		case (ServiceEvent.REGISTERED):
			System.out.println("Notification of service registered.");
			serviceReference = serviceEvent.getServiceReference();
			Greeter service = (Greeter) (ctx.getService(serviceReference));
			System.out.println(service.sayHiTo("John"));
			break;
		case (ServiceEvent.UNREGISTERING):
			System.out.println("Notification of service unregistered.");
			ctx.ungetService(serviceEvent.getServiceReference());
			break;
		default:
			break;
		}

	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.ctx = context;
		try {
			ctx.addServiceListener(this, "(objectclass=" + Greeter.class.getName() + ")");
		} catch (InvalidSyntaxException ise) {
			ise.printStackTrace();
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (serviceReference != null) {
			ctx.ungetService(serviceReference);
		}
		this.ctx = null;

	}

}
