import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRMIMatriz extends Remote{
    public int[][] multiplica_matrices(int[][] A, int[][] B, int N) throws RemoteException;
    public int[][] separa_matriz(int[][] A, int inicio, int N) throws RemoteException;
    public void acomoda_matriz(int[][] C, int[][] A, int renglon, int columna, int N) throws RemoteException;
}
