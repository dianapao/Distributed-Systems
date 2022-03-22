import java.rmi.Naming;

public class ClienteRMIMatriz {
	public static void acomoda_matriz(int[][] C, int[][] A, int renglon, int columna, int N){
        for(int i = 0; i < N / 2; i++)
            for(int j = 0; j < N / 2; j++)
                C[i + renglon][j + columna] = A[i][j];
    }

    static int N = 8;
    static int[][] A = new int [N][N];
    static int[][] B = new int [N][N];
    static int[][] C = new int [N][N];

    public static void main(String[] args) throws Exception {

        //El objeto remoto se llama pruebaMatriz, se utiliza el puerto default 1099
        String url = "rmi://localhost/pruebaMatriz";

        //obtiene una referencia que punta al objeto remoto asociado a la URL
        InterfaceRMIMatriz inter_rmi = (InterfaceRMIMatriz)Naming.lookup(url);

        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++){
                A[i][j] = 2 * i - j;
                B[i][j] = i + 2 * j;
                C[i][j] = 0;
            }
	System.out.println("Matriz A: ");
	for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(A[i][j] + " ");
            }System.out.println("");
        }System.out.println("");

	System.out.println("Matriz B: ");
	for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(B[i][j] + " ");
            }System.out.println("");
        }System.out.println("");
        
        //transpone la matriz B, la matriz transpuesta queda en B
        for(int i = 0; i < N; i++)
            for(int j = 0; j < i; j++){
                int x = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = x;
            }

	System.out.println("Matriz B TRANSPUESTA: ");
	for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(B[i][j] + " ");
            }System.out.println("");
        }System.out.println("");
        
        int[][] A1 = inter_rmi.separa_matriz(A, 0, N);
        int[][] A2 = inter_rmi.separa_matriz(A, N/2, N);
        int[][] B1 = inter_rmi.separa_matriz(B, 0, N);
        int[][] B2 = inter_rmi.separa_matriz(B, N/2, N);
	
	System.out.println("CACHO A1");
	for(int i = 0; i < N / 2; i++){
            for(int j = 0; j < N; j++){
		System.out.print(A1[i][j] + " ");
		}
		System.out.println("");
	}
        
        int[][] C1 = inter_rmi.multiplica_matrices(A1, B1, N);
        int[][] C2 = inter_rmi.multiplica_matrices(A1, B2, N);
        int[][] C3 = inter_rmi.multiplica_matrices(A2, B1, N);
        int[][] C4 = inter_rmi.multiplica_matrices(A2, B2, N);
        
        int[][] C = new int[N][N];
        acomoda_matriz(C, C1, 0, 0, N);
        acomoda_matriz(C, C2, 0, N/2, N);
        acomoda_matriz(C, C3, N/2, 0, N);
        acomoda_matriz(C, C4, N/2, N/2, N);

	System.out.println("Matriz C: ");
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(C[i][j] + " ");
            }System.out.println("");
        }
    }
}
