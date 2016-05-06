package gradingTools.server;

import gradingTools.Driver;
import gradingTools.server.GraderServerLauncher;
import gradingTools.server.RemoteGraderServer;
import inputport.ConnectionListener;
import inputport.InputPort;
import inputport.rpc.duplex.AnAbstractDuplexRPCServerPortLauncher;
import inputport.rpc.duplex.DuplexRPCServerInputPort;
import port.ATracingConnectionListener;
import port.PortAccessKind;




public class AGraderServerLauncher extends AnAbstractDuplexRPCServerPortLauncher implements GraderServerLauncher   {
	public AGraderServerLauncher(String aServerName,
			String aServerPort) {
		super (aServerName, aServerPort);
	}
	public AGraderServerLauncher() {
	}

	protected RemoteGraderServer getDriverObject() {
		return new ARemoteGraderServer();
	}
	
//	protected  ConnectionListener getConnectionListener (InputPort anInputPort) {
//		return new ATracingConnectionListener(anInputPort);
//	}
//	protected PortAccessKind getPortAccessKind() {
//		return PortAccessKind.DUPLEX;
//	}

	protected void registerRemoteObjects() {
		DuplexRPCServerInputPort anRPCServerInputPort = (DuplexRPCServerInputPort) mainPort;
		RemoteGraderServer aDriverObject = getDriverObject();
		anRPCServerInputPort.register(aDriverObject);
	}
	
//	public static String computeServerId(int aServerNumber) {
//		int aBaseNumber = Integer.parseInt(DRIVER_SERVER_ID);
//
//		return "" + (aBaseNumber + aServerNumber);
//	}
	
	public static void main (String[] args) {
		Driver.setHeadlessExitOnComplete(false);
		String aServerId = DRIVER_SERVER_ID;
		int aServerNumber = 0;
		if (args.length != 0) {
			try {
				aServerNumber = Integer.parseInt(args[0]);
				aServerId = GraderServerLauncher.computeServerId(aServerNumber);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println (DRIVER_SERVER_START_MESSAGE); 


		(new AGraderServerLauncher(DRIVER_SERVER_NAME, aServerId)).launch();
		System.out.println (DRIVER_SERVER_START_MESSAGE + " after launch");
	}
}
