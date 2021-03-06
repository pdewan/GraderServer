package gradingTools.server;

import java.util.Arrays;

import framework.logging.recorder.ConglomerateRecorder;
import gradingTools.Driver;
import gradingTools.server.RemoteGraderServer;
/*
 * Implements the interface in the corresponding client project
 */
public class ARemoteGraderServer implements RemoteGraderServer {

	@Override
	public Exception drive(String[] args) {
		try {
			System.out.println (START_DRIVE );
			System.out.println ("Args:" + Arrays.toString(args));
		Driver.main(args);
//        ConglomerateRecorder.getInstance().finish();
//		System.out.println ("Drive ended and writing results");

		System.out.println (END_DRIVE);
		} catch (Exception e) {
			e.printStackTrace();
//	        ConglomerateRecorder.getInstance().finish();

			return e;
		}
		return null;
	}

}
