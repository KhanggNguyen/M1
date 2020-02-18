//Participants : TRAN Thi Tra My - NGUYEN Huu Khang


package share;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVeterinaire extends Remote{
	public void alert(String msg);
}