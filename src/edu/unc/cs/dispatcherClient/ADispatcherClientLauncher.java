package edu.unc.cs.dispatcherClient;
/*
 * This is the entry point for the grader server jar, which happens to be 
 * a client of the dispathcing server 
 */

import edu.unc.cs.dispatcherServer.AGraderServerDescription;
import edu.unc.cs.dispatcherServer.DispatcherRegistry;
import edu.unc.cs.dispatcherServer.DispatcherServerLauncher;
import gradingTools.server.ARemoteGraderServer;
import gradingTools.server.GraderServerLauncher;
import inputport.ConnectionListener;
import inputport.InputPort;
import inputport.rpc.duplex.AnAbstractDuplexRPCClientPortLauncher;

public class ADispatcherClientLauncher extends AnAbstractDuplexRPCClientPortLauncher  implements DispatcherClientLauncher{
	SynchronizingConnectionListener connectionListener;
	public ADispatcherClientLauncher(String aServerHost, String aServerId) {
		super(DispatcherClientLauncher.CLIENT_NAME, aServerHost, aServerId, GraderServerLauncher.DRIVER_SERVER_NAME);
			
	}
	public ADispatcherClientLauncher(String aServerHost) {
		super(DispatcherClientLauncher.CLIENT_NAME, aServerHost, GraderServerLauncher.DRIVER_SERVER_ID, GraderServerLauncher.DRIVER_SERVER_NAME);
			
	}
	public ADispatcherClientLauncher(String myName, String aServerHost, String aServerId) {
		super(myName, aServerHost, aServerId, GraderServerLauncher.DRIVER_SERVER_NAME);
			
	}
	
//	protected PortAccessKind getPortAccessKind() {
//		return PortAccessKind.SIMPLEX;
//	}

	public ADispatcherClientLauncher(String aClientName, String aServerHost, String aServerId, String aServerName) {
		super(aClientName, aServerHost, aServerId, aServerName);
			
	}
	

	protected Class registeredServerClass() {
		return DispatcherServerLauncher.DISPATCHER_SERVER_CLASS;
	}

	

	
	protected DispatcherRegistry dispatcherRegistry;
	

	protected  ConnectionListener getConnectionListener (InputPort anInputPort) {
		if (connectionListener == null) {
			connectionListener = new ASynchronizingConnectionListener(anInputPort);			
		}
		return connectionListener;
	}
	

	
	protected  void createProxies() {

//		sessionServerProxy = (RMISimulationSessionServer) DirectedRPCProxyGenerator.generateRPCProxy((DuplexRPCClientInputPort) mainPort, registeredSessionServerClass());
		dispatcherRegistry = (DispatcherRegistry)  createProxy (registeredServerClass());
	}
	
//	public void drive (String[] args) {
//		if (driverServerProxy == null) {
//			System.err.println("Proxy for driver not bound");
//			return;
//		}
//	}


	@Override
	public DispatcherRegistry getDispatcherRegistry() {
		return dispatcherRegistry;
	}
	static DispatcherClientLauncher singleton;
	public static DispatcherClientLauncher getInstance() {
		if (singleton == null) {
			singleton = new ADispatcherClientLauncher("localhost");
			singleton.launch();
			
			try {
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return singleton;
			
	}
	@Override
	public InputPort getMainPort() {
		return mainPort;
	}
	public SynchronizingConnectionListener getSynchronizingConnectionListener() {
		// getPort will create another port, it should be called createPort
		return (SynchronizingConnectionListener) getConnectionListener(mainPort);
	}
	public static DispatcherClientLauncher createAndLaunch(String aClientName, String aServerHost, String aServerId) {
		ADispatcherClientLauncher aClient = new ADispatcherClientLauncher(aClientName, aServerHost, aServerId);
		aClient.launch();
		
			
			try {
//				Thread.sleep(1000);
				aClient.getSynchronizingConnectionListener().waitForConnectionStatus();
				if (!aClient.getMainPort().isConnected(GraderServerLauncher.DRIVER_SERVER_NAME) ){
					// should put in synchronous connect into gipc
					System.out.println ("Could not connect to grading dispatcher" );
					return null;
				} 
			 
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return aClient;
			
	}
	public static DispatcherClientLauncher createAndLaunchLocal(String aClientName, String aServerId) {
		return createAndLaunch(aClientName, "localhost", aServerId);
	}
	static int curId = 0;
	
	public static void main (String[] args) {
		
		String aClientName = DispatcherClientLauncher.CLIENT_NAME;
		if (args.length > 0) {
			aClientName = args[0];
		} else {
			aClientName = aClientName + curId;
			curId++;
		}
//		Driver.setHeadlessExitOnComplete(false);
		DispatcherClientLauncher aClient = createAndLaunchLocal (aClientName, DispatcherServerLauncher.DISPATCHER_SERVER_ID);
		if (aClient != null) {
			aClient.getDispatcherRegistry().registerDriverServer(new ARemoteGraderServer(), new AGraderServerDescription(aClientName));			
		}
	}
	
	
	
//	public  void  launchClient(String aMyName, HalloweenCommandProcessor aCommandProcessor, boolean aBroadcast) {
////		   RMISimulationInCoupler inCoupler = new AnRMISimulationInCoupler(aCommandProcessor);
//		   RMISimulationInCoupler inCoupler = createRMISimulationInCoupler();
//
//		try {
//			
//
//		   
//			sessionServerProxy.join(aMyName, inCoupler);
//		   
//			   if (aBroadcast) {
//				   new AnRMISimulationOutCoupler(aCommandProcessor, sessionServerProxy, aMyName);
//			   }
//				   
//		   } catch (Exception e) {
//			
//	      e.printStackTrace();
//	    }
//	  
//	  }
	
		

}
