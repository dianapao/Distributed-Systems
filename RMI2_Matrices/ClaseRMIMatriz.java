import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseRMIMatriz extends UnicastRemoteObject implements InterfaceRMIMatriz{
    
    public ClaseRMIMatriz() throws RemoteException{
        super();
    }
    
    public int[][] multiplica_matrices(int[][] A, int[][] B, int N){
        int[][] C = new int[N/2][N/2];
        for(int i = 0; i < N / 2; i++)
            for(int j = 0; j < N / 2; j++)
                for(int k = 0; k < N; k++)
                    C[i][j] += A[i][k] * B[j][k];
        return C;
    }
    
    public int[][] separa_matriz(int[][] A, int inicio, int N){
        int[][] M = new int[N/2][N];
        for(int i = 0; i < N / 2; i++)
            for(int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    }
    
    public void acomoda_matriz(int[][] C, int[][] A, int renglon, int columna, int N){
        for(int i = 0; i < N / 2; i++)
            for(int j = 0; j < N / 2; j++)
                C[i + renglon][j + columna] = A[i][j];
    }
}
